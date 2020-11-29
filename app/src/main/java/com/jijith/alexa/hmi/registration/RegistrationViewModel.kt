package com.jijith.alexa.hmi.registration

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jijith.alexa.hmi.MainViewModel
import com.jijith.alexa.vo.User

class RegistrationViewModel(
    private val context: Context?,
    private val registrationRepository: RegistrationRepository,
    sharedMainViewModel: MainViewModel
) : ViewModel() {


    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()
    var isSignedIn = MutableLiveData<Boolean>()

    var user = MutableLiveData<User>()

    init {
        user = registrationRepository.user
        loadingVisibility = registrationRepository.loading
        errorMessage = registrationRepository.errrorMessage
        isSignedIn = registrationRepository.isSignedIn
    }

    fun isSigned() {
        registrationRepository.isSignedIn()
    }
}