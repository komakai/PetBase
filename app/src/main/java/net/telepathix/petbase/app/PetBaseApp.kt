package net.telepathix.petbase.app

import android.support.multidex.MultiDexApplication
import com.squareup.picasso.OkHttp3Downloader
import net.telepathix.petbase.network.ApiClientManager
import net.telepathix.petbase.network.ApiService
import com.squareup.picasso.Picasso

const val petbaseTag = "PETBASE"

const val maxCacheSize = 30*1024*1024

open class PetBaseApp: MultiDexApplication() {

    lateinit var apiClient: ApiService

    override fun onCreate() {
        super.onCreate()
        initApiClient()
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this, maxCacheSize.toLong()))
        val built = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }

    open fun initApiClient() {
        apiClient = ApiClientManager.apiClient
    }
}
