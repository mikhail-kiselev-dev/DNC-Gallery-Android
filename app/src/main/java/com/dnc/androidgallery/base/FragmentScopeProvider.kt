package com.dnc.androidgallery.base

import androidx.fragment.app.Fragment
import com.dnc.androidgallery.di.scope.emptyScope
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.scope.getScopeId
import org.koin.core.component.KoinComponent
import org.koin.core.scope.Scope

/**
 * Expected state: [onAttach], [bindViewModel], [unbindViewModel], [onDetach]
 */
class FragmentScopeProvider : KoinComponent {
    private var realScope: Scope? = null

    val scope: Scope
        get() = realScope
            ?: throw IllegalStateException("Attempting to call scope while fragment is already detached / has not been attached yet")

    fun onAttach(scopeDefinition: ScopeDefinition) {
        createScopeFromDefinition(scopeDefinition)
        realScope = scopeDefinition.scope
    }

    fun bindViewModel(viewModel: BaseViewModel) {
        viewModel.bindFragmentScope(realScope!!)
    }

    fun unbindViewModel(viewModel: BaseViewModel) {
        viewModel.unbindFragmentScope(realScope!!)
    }

    fun onDetach(scopeDefinition: ScopeDefinition) {
        if (scopeDefinition.closeOnDestroy) {
            realScope?.also {
                it.close()
                getKoin().deleteScope(it.id)
            }
        }
        realScope = null
    }
}

private fun createScopeFromDefinition(definition: ScopeDefinition) {
    val scopesList = mutableListOf<Scope>()
    var currentScopeDefinition = definition.parentScopeDefinition.invoke()
    while (currentScopeDefinition != null) {
        scopesList.add(currentScopeDefinition.scope)
        currentScopeDefinition = currentScopeDefinition.parentScopeDefinition.invoke()
    }
    definition.scope.linkTo(*scopesList.toTypedArray())
}

@Suppress("unused")
private fun Fragment.getScopeFromDefinition(scopeDefinition: ScopeDefinition) =
    when (scopeDefinition) {
        is ScopeDefinition.Fragment -> fragmentScope()
        is ScopeDefinition.CustomScope -> scopeDefinition.scope
        is ScopeDefinition.Empty -> emptyScope(getScopeId())
    }
