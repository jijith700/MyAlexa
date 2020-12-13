package com.jijith.alexa.service.interfaces

import com.amazon.aace.alexa.AlexaClient

interface AlexaServiceCallbackListener {

    fun onReceiveCBL(url: String?, code: String?)

    fun onRenderTemplate(payload:  String?)

    fun onDialoStateChange(dialogState: AlexaClient.DialogState?)
}