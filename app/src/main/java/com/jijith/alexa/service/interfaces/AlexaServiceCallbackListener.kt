package com.jijith.alexa.service.interfaces

interface AlexaServiceCallbackListener {

    fun onReceiveCBL(url: String?, code: String?)

    fun onRenderTemplate(payload:  String?)
}