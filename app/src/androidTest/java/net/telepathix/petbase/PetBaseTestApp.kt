package net.telepathix.petbase

import io.reactivex.Single
import net.telepathix.petbase.app.PetBaseApp
import net.telepathix.petbase.model.Config
import net.telepathix.petbase.model.Pet
import net.telepathix.petbase.network.ApiService

class PetBaseTestApp: PetBaseApp() {
    var config = Config(true, true, "M-F 9:00-18:00")

    override fun initApiClient() {
        apiClient = object: ApiService {
            override fun getConfig(): Single<Config> = Single.just(config)

            override fun getPets(): Single<List<Pet>> = Single.just(
                listOf(
                    Pet("file:///android_asset/cat.png", "Cat", "file:///android_asset/cat.html", "2018-02-21"),
                    Pet("file:///android_asset/rabbit.png", "Rabbit", "file:///android_asset/rabbit.html", "2018-02-21")
                )
            )
        }
    }
}