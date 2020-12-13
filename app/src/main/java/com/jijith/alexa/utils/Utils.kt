package com.jijith.alexa.utils

import com.amazon.aace.alexa.AlexaClient
import com.amazon.autovoicechrome.util.AutoVoiceChromeState

class Utils {
    companion object {
        fun getAutoVoiceChromeState (dialogState: AlexaClient.DialogState) :AutoVoiceChromeState  {
            var vcState = AutoVoiceChromeState.UNKNOWN
            when(dialogState) {
                AlexaClient.DialogState.LISTENING->{
                    vcState = AutoVoiceChromeState.LISTENING
                }
                AlexaClient.DialogState.THINKING->{
                    vcState = AutoVoiceChromeState.THINKING
                }
                AlexaClient.DialogState.SPEAKING->{
                    vcState = AutoVoiceChromeState.SPEAKING
                }
                AlexaClient.DialogState.IDLE->{
                    vcState = AutoVoiceChromeState.IDLE
                }
            }
            return vcState
        }
    }
}