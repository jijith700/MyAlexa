package com.jijith.alexa.hmi

import android.content.Context
import android.content.ServiceConnection
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jijith.alexa.hmi.common.CommonRepository
import com.jijith.alexa.lib.IMyAlexaServiceInterface
import com.jijith.alexa.vo.User
import com.jijith.alexa.vo.WikiDisplayCard
import org.json.JSONObject
import timber.log.Timber


class MainRepository(var context: Context) {

    private lateinit var iMyAlexaServiceInterface: IMyAlexaServiceInterface
    lateinit var serviceConnection: ServiceConnection

    var loading = MutableLiveData<Int>()
    var success = MutableLiveData<Boolean>()
    var errrorMessage = MutableLiveData<String>()
    var user = MutableLiveData<User>()
    var url = MutableLiveData<String>()
    var code = MutableLiveData<String>()
    var wikiDisplayCard = MutableLiveData<WikiDisplayCard>()

    /**
     * Variable hold the object of ApiService and the it will initialized here.
     */
    init {
        CommonRepository.context = context
        CommonRepository.mainRepository = this
        CommonRepository.startService()
        CommonRepository.connectService(IMyAlexaServiceInterface::class.java.name)
    }

    fun startCBL() {
        CommonRepository.startCBL()
    }

    fun onReceiveCBL(url: String?, code: String?) {
        Timber.d("URL: %s Code: %s", url, code)
        this.url.postValue(url!!)
        this.code.postValue(code!!)
    }

    fun onRenderTemplate(payload: String?) {
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

    fun stopCBL() {
        CommonRepository.stopCBL()
    }

    public fun stopBinding() {
        CommonRepository.stopBinding()
    }
}