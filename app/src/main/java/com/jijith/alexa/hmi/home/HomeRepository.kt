package com.jijith.alexa.hmi.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.jijith.alexa.vo.ServiceData
import com.jijith.alexa.vo.User


class HomeRepository(var context: Context?) {

    var loading = MutableLiveData<Int>()
    var isSignedIn = MutableLiveData<Boolean>()
    var errrorMessage = MutableLiveData<String>()
    var user = MutableLiveData<User>()

    fun isSignedIn() {
        isSignedIn.value = ServiceData.isSignedIn.value ?: false
    }
}