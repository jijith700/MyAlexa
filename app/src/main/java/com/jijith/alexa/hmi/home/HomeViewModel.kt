package com.jijith.alexa.hmi.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jijith.alexa.vo.User

class HomeViewModel(
    private val context: Context?,
    private val splashRepository: HomeRepository
) :
    ViewModel() {

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var isSignedIn: MutableLiveData<Boolean> = MutableLiveData()

    var user = MutableLiveData<User>()

    init {
        user = splashRepository.user
        loadingVisibility = splashRepository.loading
        errorMessage = splashRepository.errrorMessage
        isSignedIn = splashRepository.isSignedIn
    }

    fun isSigned() {
        splashRepository.isSignedIn()
    }
}