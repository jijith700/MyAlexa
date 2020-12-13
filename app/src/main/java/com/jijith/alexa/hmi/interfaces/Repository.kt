package com.jijith.alexa.hmi.interfaces

import com.amazon.aace.alexa.AlexaClient

interface Repository {

    fun onRenderTemplate(payload: String?) {
    }

    fun onDialogStateChange(dialogState: AlexaClient.DialogState) {

    }

}