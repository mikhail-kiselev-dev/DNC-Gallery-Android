package com.dnc.androidgallery.di.scope

import androidx.fragment.app.Fragment
import org.koin.androidx.scope.getScopeId
import org.koin.core.component.KoinComponent
import org.koin.core.qualifier.named

val AUTHORIZED_SCOPE_NAME = named("Authorized")

private var counter = 0

@Suppress("unused")
fun authorizedScope(hostFragment: Fragment) = (object : KoinComponent {}).getKoin()
    .getOrCreateScope("${hostFragment.getScopeId()}${counter++}", AUTHORIZED_SCOPE_NAME)
