package com.jijith.alexa.hmi.splash

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jijith.alexa.R
import com.jijith.alexa.lib.IMyAlexaServiceInterface
import com.jijith.alexa.vo.ServiceData
import com.jijith.alexa.vo.User
import timber.log.Timber


class SplashRepository(var context: Context?) {

    var loading = MutableLiveData<Int>()
    var isSignedIn = MutableLiveData<Boolean>()
    var errrorMessage = MutableLiveData<String>()
    var user = MutableLiveData<User>()

    fun isSignedIn() {
        isSignedIn.value = ServiceData.isSignedIn.value ?: false
    }
}