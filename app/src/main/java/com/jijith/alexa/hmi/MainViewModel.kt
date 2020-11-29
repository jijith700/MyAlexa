package com.jijith.alexa.hmi

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jijith.alexa.vo.User

class MainViewModel() : ViewModel() {

    private lateinit var context: Context
    private lateinit var mainRepository: MainRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()
    var user = MutableLiveData<User>()
    var loadRegistration: MutableLiveData<Boolean> = MutableLiveData()
    var loadHome: MutableLiveData<Boolean> = MutableLiveData()
    var url = MutableLiveData<String>()
    var code = MutableLiveData<String>()

    constructor(context: Context, mainRepository: MainRepository) : this() {
        this.context = context
        this.mainRepository = mainRepository
        user = mainRepository.user
        loadingVisibility = mainRepository.loading
        errorMessage = mainRepository.errrorMessage
        success = mainRepository.success
        url = mainRepository.url
        code = mainRepository.code
    }

    fun stopBinding() {
        mainRepository.stopBinding()
    }

    fun startCBL() {
        mainRepository.startCBL()
    }

    fun loadRegistration() {
        loadRegistration.value = true
    }

    fun loadHome() {
        loadHome.value = true
    }

    fun stopCBL(){
        mainRepository.stopCBL()
    }
}