package com.itunesapp.repository.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.itunesapp.repository.R
import okhttp3.Interceptor
import okhttp3.Request
import java.io.IOException

class NetworkConnectionInterceptor(private val context: Context): Interceptor{

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        if (!isOnline())
            throw NoConnectivityException(context)

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    fun isOnline(): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    class NoConnectivityException(private val context: Context) : IOException() {
        override val message: String get() = context.getString(R.string.check_connection)
    }
}