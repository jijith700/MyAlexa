// IMyAlexaCallbackInterface.aidl
package com.jijith.alexa.lib;

// Declare any non-default types here with import statements

interface IMyAlexaCallbackInterface {

    void onReceiveCBL(String url, String code);

    void onRenderTemplate(String payload);

}
