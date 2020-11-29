package com.jijith.alexa.hmi.registration

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.jijith.alexa.vo.ServiceData
import com.jijith.alexa.vo.User


class RegistrationRepository(private val context: Context?) {

    var loading = MutableLiveData<Int>()
    var success = MutableLiveData<Boolean>()
    var errrorMessage = MutableLiveData<String>()
    var user = MutableLiveData<User>()
    var isSignedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun isSignedIn() {
        isSignedIn.value = ServiceData.isSignedIn.value ?: false
    }
}