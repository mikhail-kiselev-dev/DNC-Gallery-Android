package com.dnc.androidgallery.base

import android.net.Uri
import androidx.annotation.MainThread
import androidx.lifecycle.*
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.dnc.androidgallery.base.navigation.NavigationCommand
import com.dnc.androidgallery.core.network.NetworkState
import com.dnc.androidgallery.core.utils.BaseResult
import com.dnc.androidgallery.core.utils.ExceptionParser
import com.dnc.androidgallery.core.utils.SingleLiveEvent
import com.dnc.androidgallery.core.utils.loge
import com.dnc.androidgallery.di.scope.EMPTY_SCOPE_NAME
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.KoinScopeComponent
import org.koin.core.scope.Scope
import org.koin.core.scope.getScopeId
import kotlin.coroutines.CoroutineContext

private const val TAG = "BaseViewModel"

@Suppress("unused")
abstract class BaseViewModel : ViewModel(), KoinScopeComponent {

    private var _scope: Scope? = null
    override val scope: Scope
        get() = _scope
            ?: throw IllegalStateException("Attempting to call scope after ViewModel's clear")

    private val exceptionParser: ExceptionParser by inject()

    protected val mainContext: CoroutineContext = Dispatchers.Main
    protected val ioContext: CoroutineContext = Dispatchers.IO

    protected val loaderScope: CoroutineScope = viewModelScope + ioContext

    val navigationCommand: LiveData<NavigationCommand> = SingleLiveEvent<NavigationCommand>()

    /**
     * Used by [BaseHostFragment] if this fragment hosts a [androidx.navigation.fragment.NavHostFragment]
     */
    val currentDestination = MutableStateFlow<NavDestination?>(null)

    val defaultErrorLiveData: LiveData<String?> = MutableLiveData(null)

    val loadingFlow = MutableStateFlow(false)

    init {
        @Suppress("LeakingThis")
        _scope = getKoin().createScope(scopeId = getScopeId(), qualifier = EMPTY_SCOPE_NAME)
    }

    override fun onCleared() {
        super.onCleared()

        _scope?.close()
    }

    open fun bindFragmentScope(fragmentScope: Scope) {
        _scope!!.linkTo(fragmentScope)
    }

    fun unbindFragmentScope(fragmentScope: Scope) {
        _scope?.unlink(fragmentScope)
    }

    protected val defaultErrorHandler = CoroutineExceptionHandler { _, throwable ->
        sendError(throwable)
        loge(TAG, throwable)
    }

    protected fun <T> collect(flow: Flow<T>, block: suspend (T) -> Unit): Job {
        return viewModelScope.launch(mainContext + defaultErrorHandler) {
            loadingFlow.value = true
            flow.collect {
                loadingFlow.value = false
                block(it)
            }
        }
    }

    protected fun <T> collectCatching(
        onFailure: ((Throwable) -> Unit)? = null,
        flow: Flow<T>,
        block: (T) -> Unit
    ): Job {
        return launchCatching(onFailure = onFailure) {
            flow.flowOn(mainContext).collect {
                block(it)
            }
        }
    }

    protected fun launch(
        loadingLiveData: MutableStateFlow<Boolean>? = loadingFlow,
        dispatcher: CoroutineContext = mainContext,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(dispatcher + defaultErrorHandler) {
            try {
                withContext(mainContext) {
                    loadingLiveData?.value = true
                }
                this.block()
            } finally {
                withContext(mainContext) {
                    loadingLiveData?.value = false
                }
            }
        }
    }

    protected fun launchThrowable(
        loadingLiveData: MutableLiveData<Boolean>? = null,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(mainContext) {
        loadingLiveData?.value = true
        this.block()
        loadingLiveData?.value = false
    }

    protected fun launchCatching(
        onFailure: ((Throwable) -> Unit)? = null,
        loadingLiveData: MutableStateFlow<Boolean>? = loadingFlow,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(mainContext) {
            try {
                loadingLiveData?.value = true
                block()
            } catch (e: Exception) {
                onFailure?.invoke(e)
                loge(TAG, e)
            } finally {
                loadingLiveData?.value = false
            }
        }
    }

    protected fun launchCatchingWithHandler(
        exceptionHandler: CoroutineExceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(mainContext + exceptionHandler) {
            block()
        }
    }

    protected fun sendError(throwable: Throwable) {
        defaultErrorLiveData.postNewValue(exceptionParser.parseError(throwable))
    }

    open fun navigate(
        direction: NavDirections,
        navigatorExtras: Navigator.Extras? = null,
        hideKeyboard: Boolean = true,
        navOptions: NavOptions? = null
    ) {
        navigationCommand.setNewValue(
            NavigationCommand.To(
                direction,
                navigatorExtras,
                hideKeyboard,
                navOptions
            )
        )
    }

    fun navigateToUri(
        uri: Uri,
        hideKeyboard: Boolean = true
    ) {
        navigationCommand.setNewValue(NavigationCommand.ToUri(uri, hideKeyboard))
    }

    fun navigateBack(hideKeyboard: Boolean = true) {
        navigationCommand.setNewValue(NavigationCommand.Back(hideKeyboard))
    }

    fun navigateBackToStart(hideKeyboard: Boolean = true) {
        navigationCommand.setNewValue(NavigationCommand.BackToStart(hideKeyboard))
    }

    fun navigateChild(
        direction: NavDirections,
        navigatorExtras: Navigator.Extras? = null,
        hideKeyboard: Boolean = true
    ) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                NavigationCommand.To(
                    direction,
                    navigatorExtras,
                    hideKeyboard
                )
            )
        )
    }

    fun navigateChild(
        command: NavigationCommand
    ) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                command
            )
        )
    }

    fun navigateChildToUri(
        uri: Uri,
        hideKeyboard: Boolean = true
    ) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                NavigationCommand.ToUri(
                    uri, hideKeyboard
                )
            )
        )
    }

    fun navigateChildBack(hideKeyboard: Boolean = true) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                NavigationCommand.Back(
                    hideKeyboard
                )
            )
        )
    }

    fun navigateChildBackToStart(hideKeyboard: Boolean = true) {
        navigationCommand.setNewValue(
            NavigationCommand.HostNavigationCommand(
                NavigationCommand.BackToStart(
                    hideKeyboard
                )
            )
        )
    }

    protected inline fun <T : Any> BaseResult<T>.dispatch(
        onSuccessValue: ((T) -> Unit) = { },
        onErrorValue: (Throwable) -> T,
        notifyError: Boolean = true
    ): T =
        when (this) {
            is BaseResult.Success -> {
                data.also {
                    onSuccessValue.invoke(data)
                }
            }
            is BaseResult.Error -> {
                if (notifyError) {
                    sendError(this.exception)
                }
                onErrorValue(this.exception)
            }
        }

    /**
     * Calls [sendError] on each [NetworkState.Error]
     * U still should be responsible for handling [NetworkState.InitialError]
     */
    protected fun Flow<NetworkState>.launchSendError() =
        this.onEach {
            if (it is NetworkState.Error) {
                sendError(it.t)
            }
        }.launchIn(viewModelScope)

    protected fun NetworkState.tryRetry() =
        launch(loadingLiveData = null) {
            when (this@tryRetry) {
                is NetworkState.InitialError -> {
                    this@tryRetry.retry.invoke()
                }
                is NetworkState.Error -> {
                    this@tryRetry.retry.invoke()
                }
                else -> {
                    // ignore
                }
            }
        }

    /**
     * Use this in order to eliminate backing _fields for Livedata
     */
    @MainThread
    protected fun <T : Any?> LiveData<T>.setNewValue(newValue: T) {
        when (this) {
            is MutableLiveData -> this.value = newValue
            else -> throw Exception("Not using createMutableLiveData() or createSingleLiveData() to create live data")
        }
    }

    /**
     * Use this in order to eliminate backing _fields for Livedata
     */
    @MainThread
    protected fun <T : Any?> LiveData<T>.postNewValue(newValue: T) {
        when (this) {
            is MutableLiveData -> this.postValue(newValue)
            else -> throw Exception("Not using createMutableLiveData() or createSingleLiveData() to create live data")
        }
    }

    /**
     * Use this in order to eliminate backing _fields for Flow
     */
    @MainThread
    protected fun <T : Any?> Flow<T?>.setNewValue(newValue: T) {
        when (this) {
            is MutableStateFlow -> {
                this.value = newValue
                this.value = null
            }
            is MutableSharedFlow -> {
                this.tryEmit(newValue)
                this.tryEmit(null)
            }
            else -> throw Exception("Not using createMutableLiveData() or createSingleLiveData() to create live data")
        }
    }

    protected inline fun <reified T : Any> injectWithLoaderScope() =
        inject<T> { parametersOf(loaderScope) }

    protected fun parseError(throwable: Throwable): String {
        return exceptionParser.parseError(throwable)
    }
}
