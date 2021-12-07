package com.dnc.androidgallery.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.dnc.androidgallery.core.utils.loge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ConnectionStatusListener {
    val connectionStatusChannel: StateFlow<ConnectionStatusState>

    fun registerListener()
    fun unregisterListener()
    fun setDismissedStatus()

    enum class ConnectionStatusState {
        Default, HasConnection, NoConnection, Dismissed
    }
}

@Suppress("unused")
class ConnectionStatusListenerImpl(
    private val context: Context
) : ConnectionStatusListener {

    override val connectionStatusChannel =
        MutableStateFlow(ConnectionStatusListener.ConnectionStatusState.Default)

    private val connectivityManager by lazy {
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    }

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                if (connectionStatusChannel.value != ConnectionStatusListener.ConnectionStatusState.Dismissed) {
                    connectionStatusChannel.tryEmit(
                        ConnectionStatusListener.ConnectionStatusState.NoConnection
                    )
                }
            }

            override fun onAvailable(network: Network) {
                connectionStatusChannel.tryEmit(
                    ConnectionStatusListener.ConnectionStatusState.HasConnection
                )
            }
        }
    }

    override fun registerListener() {
        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        updateConnectionStatus()
    }

    override fun unregisterListener() {
        try {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        } catch (ignore: IllegalArgumentException) {
            loge("ConnectionStatusListener unregisterListener exception: $ignore")
        }

        connectionStatusChannel.tryEmit(
            ConnectionStatusListener.ConnectionStatusState.Default
        )
    }

    override fun setDismissedStatus() {
        connectionStatusChannel.tryEmit(
            ConnectionStatusListener.ConnectionStatusState.Dismissed
        )
    }

    private fun updateConnectionStatus() {
        connectivityManager?.also {
            connectionStatusChannel.tryEmit(
                if (it.activeNetwork == null) {
                    ConnectionStatusListener.ConnectionStatusState.NoConnection
                } else {
                    ConnectionStatusListener.ConnectionStatusState.HasConnection
                }
            )
        }
    }
}
