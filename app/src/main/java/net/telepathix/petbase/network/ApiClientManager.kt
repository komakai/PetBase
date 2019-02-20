package net.telepathix.petbase.network

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import net.telepathix.petbase.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

open class ApiClientManager {
    companion object {
        val apiClient: ApiService
            get() = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                .build()
                .create(ApiService::class.java)
    }
}
