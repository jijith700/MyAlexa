package com.jijith.alexa.service.interfaces.managers

import com.amazon.aace.alexa.AlexaClient

interface AlexaEngineManager {

    fun startCBL()

    fun onReceiveCBLCode(url: String?, code: String?)

    fun stopCBL()

    fun onRenderTemplate(payload:  String?)

    fun onDialogStateChange(dialogState: AlexaClient.DialogState?)

}