package com.jijith.alexa.hmi.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jijith.alexa.hmi.common.CommonRepository
import com.jijith.alexa.hmi.interfaces.Repository
import com.jijith.alexa.vo.ServiceData
import com.jijith.alexa.vo.User
import com.jijith.alexa.vo.WikiDisplayCard
import org.json.JSONObject
import timber.log.Timber


class HomeRepository(var context: Context?) : Repository {

    var loading = MutableLiveData<Int>()
    var isSignedIn = MutableLiveData<Boolean>()
    var errrorMessage = MutableLiveData<String>()
    var user = MutableLiveData<User>()
    var wikiDisplayCard = MutableLiveData<WikiDisplayCard>()

    init {
        CommonRepository.registerRepositoryObserver(this)
    }

    fun isSignedIn() {
        isSignedIn.value = ServiceData.isSignedIn.value ?: false
    }

    override fun onRenderTemplate(payload: String?) {
        Timber.d("payload: %s", payload)
        val template = JSONObject(payload)
        val type = template.getString("type")
        val gson = Gson()
        when (type) {
            "BodyTemplate1",
            "BodyTemplate2" -> {
                val wikiCard =
                    gson.fromJson<WikiDisplayCard>(payload, WikiDisplayCard::class.java)
                wikiDisplayCard.postValue(wikiCard)
            }
            /* "ListTemplate1" ->

             "WeatherTemplate" ->

             "LocalSearchListTemplate1" ->

             "LocalSearchListTemplate2" ->

             "TrafficDetailsTemplate" ->

             "LocalSearchDetailTemplate1" ->*/

            else -> Timber.e("Unknown Template sent")
        }
    }
}