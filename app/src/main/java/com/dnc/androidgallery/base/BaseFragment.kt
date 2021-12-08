package com.dnc.androidgallery.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.dnc.androidgallery.MainActivity
import com.dnc.androidgallery.MainViewModel
import com.dnc.androidgallery.base.navigation.NavigationCommand
import com.dnc.androidgallery.base.navigation.NavigationCommandHandler
import com.dnc.androidgallery.core.extensions.*
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.scope.KoinScopeComponent
import org.koin.core.scope.Scope
import org.koin.core.scope.getScopeId
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(
    private val layoutId: Int,
    factory: (View) -> VB
) : Fragment(layoutId), KoinScopeComponent, ScopeDefinitionProvider {

    /**
     * Usually u should not override this value, override [scopeDefinition] instead
     */
    override val scope: Scope
        get() = fragmentScopeProvider.scope

    override val scopeDefinition: ScopeDefinition = ScopeDefinition.Empty(getScopeId()) {
        (requestNotHostParentFragment() as? ScopeDefinitionProvider)?.scopeDefinition
    }

    protected lateinit var baseActivity: MainActivity

    protected open val viewModel: VM by lazy {
        scope.getViewModel(
            owner = getVMOwnerDefinition(),
            clazz = getViewModelKClass(),
            parameters = getParameters()
        )
    }

    protected val mainViewModel: MainViewModel by sharedViewModel()

    private val fragmentScopeProvider = FragmentScopeProvider()

    protected open val navigationCommandHandler =
        NavigationCommandHandler(navControllerDefinition = { findNavController() })

    open fun getViewModelScope(): ViewModelScope = ViewModelScope.Self

    val binding by viewBinding<VB>(factory)

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as MainActivity
        fragmentScopeProvider.onAttach(scopeDefinition)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentScopeProvider.bindViewModel(viewModel)
    }

    private fun getVMOwnerDefinition(): () -> ViewModelOwner {
        return when (val type = getViewModelScope()) {
            is ViewModelScope.Activity -> requireActivity().asOwnerDefinition()
            is ViewModelScope.Parent -> type.parentClass?.let {
                getParentFragmentByClass(it).asOwnerDefinition()
            } ?: getParentFragment(true)?.asOwnerDefinition() ?: this.asOwnerDefinition()
            else -> this.asOwnerDefinition()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(context).inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    override fun onDetach() {
        super.onDetach()
        fragmentScopeProvider.onDetach(scopeDefinition = scopeDefinition)
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentScopeProvider.unbindViewModel(viewModel)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getViewModelKClass(): KClass<VM> {
        val actualClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        return actualClass.kotlin
    }

    open fun getParameters(): ParametersDefinition = {
        emptyParametersHolder()
    }

    @CallSuper
    open fun observeLiveData() {
        subscribeNullable(viewModel.defaultErrorLiveData, ::onError)
        subscribe(viewModel.loadingFlow, ::onLoading)
        subscribe(viewModel.navigationCommand, ::navigate)
    }

    protected open fun onLoading(isLoading: Boolean) {}

    protected open fun onError(errorMessage: String?) {
        errorMessage ?: return
        showToast(errorMessage)
    }

    protected open fun navigate(navCommand: NavigationCommand) {
        navigationCommandHandler.handle(requireActivity(), navCommand)
    }

    sealed class ViewModelScope {
        object Self : ViewModelScope()
        data class Parent(val parentClass: Class<out Fragment>? = null) : ViewModelScope()
        object Activity : ViewModelScope()
    }
}
