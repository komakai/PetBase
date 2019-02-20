package net.telepathix.petbase.network

import io.reactivex.Single
import net.telepathix.petbase.BuildConfig
import net.telepathix.petbase.model.Config
import net.telepathix.petbase.model.Pet
import retrofit2.http.GET

interface ApiService {
    @GET(BuildConfig.CONFIG_ENDPOINT)
    fun getConfig(): Single<Config>

    @GET(BuildConfig.PET_LIST_ENDPOINT)
    fun getPets(): Single<List<Pet>>
}
