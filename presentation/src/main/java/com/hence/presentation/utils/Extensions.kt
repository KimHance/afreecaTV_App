package com.hence.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CancellationException

fun showErrorMessage(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager: ConnectivityManager =
        getSystemService(ConnectivityManager::class.java)
    val network: Network = connectivityManager.activeNetwork ?: return false
    val actNetwork: NetworkCapabilities =
        connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        else -> false
    }
}
