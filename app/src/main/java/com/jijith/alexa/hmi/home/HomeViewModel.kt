package com.jijith.alexa.hmi.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazon.aace.alexa.AlexaClient
import com.jijith.alexa.hmi.MainRepository
import com.jijith.alexa.vo.User
import com.jijith.alexa.vo.WikiDisplayCard

class HomeViewModel(
    private val context: Context?,
    private val mainRepository: MainRepository,
    private val homerepository: HomeRepository
) :
    ViewModel() {

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var isSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    var wikiDisplayCard = MutableLiveData<WikiDisplayCard>()
    var dialogState = MutableLiveData<AlexaClient.DialogState>()

    var user = MutableLiveData<User>()

    init {
        user = homerepository.user
        loadingVisibility = homerepository.loading
        errorMessage = homerepository.errrorMessage
        isSignedIn = homerepository.isSignedIn
        wikiDisplayCard = homerepository.wikiDisplayCard
        dialogState = homerepository.dialogState
    }

    fun isSigned() {
        homerepository.isSignedIn()
    }
}