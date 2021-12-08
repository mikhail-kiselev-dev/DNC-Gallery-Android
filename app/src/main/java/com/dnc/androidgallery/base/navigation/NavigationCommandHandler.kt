package com.dnc.androidgallery.base.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.dnc.androidgallery.core.extensions.hideKeyboard
import com.dnc.androidgallery.core.utils.logd

typealias NavControllerDefinition = () -> NavController

interface NavigationCommandHandlerInterface {
    fun handle(
        activity: Activity,
        navigationCommand: NavigationCommand
    )
}

open class NavigationCommandHandler(
    private val navControllerDefinition: NavControllerDefinition,
    private val childNavControllerDefinition: NavControllerDefinition? = null
) : NavigationCommandHandlerInterface {

    override fun handle(
        activity: Activity,
        navigationCommand: NavigationCommand
    ) {
        if (navigationCommand.hideKeyboard) {
            activity.hideKeyboard()
        }
        try {
            val navController = navControllerDefinition.invoke()
            handle(activity, navController, navigationCommand)
        } catch (e: IllegalArgumentException) {
            logd("Error happened while trying to dispatch navigation command, $e")
        }
    }

    private fun handle(
        activity: Activity,
        navController: NavController,
        navigationCommand: NavigationCommand
    ) {
        when (navigationCommand) {
            is NavigationCommand.ToUri -> {
                navController.navigate(
                    navigationCommand.uri
                )
            }
            is NavigationCommand.To -> {
                navController.navigate(
                    navigationCommand.direction.actionId,
                    navigationCommand.direction.arguments,
                    navigationCommand.navOptions,
                    navigationCommand.navigationExtras
                )
            }
            is NavigationCommand.Back -> {
                navController.navigateUp()
            }
            is NavigationCommand.BackToStart -> {
                navController.popBackStack(navController.graph.startDestination, false)
            }
            is NavigationCommand.CompoundNavigationCommand -> {
                for (command in navigationCommand.navigationCommandList) {
                    handle(activity, navController, command)
                }
            }
            is NavigationCommand.HostNavigationCommand -> {
                childNavControllerDefinition?.invoke()?.also { navigationController ->
                    handle(activity, navigationController, navigationCommand.navigationCommand)
                }
                    ?: throw IllegalStateException("Declare childNavControllerDefinition to handle HostNavigationCommand")
            }
        }
    }
}
