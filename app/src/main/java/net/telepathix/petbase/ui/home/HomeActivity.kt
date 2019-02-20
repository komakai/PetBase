package net.telepathix.petbase.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.telepathix.petbase.app.PetBaseApp
import net.telepathix.petbase.app.petbaseTag
import kotlinx.android.synthetic.main.activity_home.*
import net.telepathix.petbase.R
import net.telepathix.petbase.helper.OpeningHours
import net.telepathix.petbase.helper.setVisibility
import net.telepathix.petbase.helper.showSimpleAlert

private fun <T> execApi(single: Single<T>, resultHandler: (T) -> Unit ) : Disposable {
    return single
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(
            { t -> resultHandler(t) },
            { e -> Log.e(petbaseTag, e.toString()) }
        )
}

class HomeActivity : AppCompatActivity() {

    val petAdapter = PetAdapter(this)
    private var officeHours: OpeningHours? = null
    val compositeDisposable = CompositeDisposable()

    fun getMessageRes() = if (officeHours?.isOpenNow() == true) R.string.message_in_hours else R.string.message_out_of_hours

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        petList.layoutManager = LinearLayoutManager(this)
        petList.adapter = petAdapter
        swipeRefresh.setOnRefreshListener {
            refreshPetList()
        }
        callButton.setOnClickListener {
            showSimpleAlert(this, getMessageRes())
        }
        chatButton.setOnClickListener {
            showSimpleAlert(this, getMessageRes())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        petList.adapter = null
        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(execApi((application as PetBaseApp).apiClient.getConfig()) { config ->
            // buttons are disabled until we get the config. Button hiding/unhiding logic is below
            chatButton.isEnabled = true
            callButton.isEnabled = true
            buttonArea.setVisibility(config.isCallEnabled || config.isChatEnabled)
            buttonSpacer.setVisibility(config.isCallEnabled && config.isChatEnabled)
            chatButton.setVisibility(config.isChatEnabled)
            callButton.setVisibility(config.isCallEnabled)
            officeHoursText.text = getString(R.string.office_hours_label, config.workHours)
            officeHours = OpeningHours.parseWorkHours(config.workHours)
        })
        refreshPetList()
    }

    private fun refreshPetList() {
        val apiCall = (application as PetBaseApp).apiClient
            .getPets()
            .doOnSubscribe { runOnUiThread { swipeRefresh.isRefreshing = true } }
            .doFinally { runOnUiThread { swipeRefresh.isRefreshing = false } }
        compositeDisposable.add(execApi(apiCall) { petResults -> petAdapter.items = petResults })
    }
}
