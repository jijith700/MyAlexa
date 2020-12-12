package com.jijith.alexa.service.handlers

import com.amazon.aace.alexa.TemplateRuntime
import com.jijith.alexa.service.interfaces.managers.AlexaEngineManager
import timber.log.Timber

class TemplateRuntimeHandler(
    private var alexaEngineManager: AlexaEngineManager,
    private var playbackController: PlaybackControllerHandler
) : TemplateRuntime() {

    private var mCurrentAudioItemId: String? = null

    override fun renderTemplate(payload: String?) {
        // Log payload
        Timber.i(payload)
        alexaEngineManager.onRenderTemplate(payload)
    }

    override fun renderPlayerInfo(payload: String?) {
        Timber.d(payload)
        /* try {
             val playerInfo = JSONObject(payload)
             val audioItemId = playerInfo.getString("audioItemId")
             val content = playerInfo.getJSONObject("content")
             val audioProvider = content.getJSONObject("provider")
             val providerName = audioProvider.getString("name")

             // Update playback controller buttons and player info labels
             if (mPlaybackController != null) {
                 //reset visual state
                 mPlaybackController.hidePlayerInfoControls()
                 if (playerInfo.has("controls")) {
                     val controls = playerInfo.getJSONArray("controls")
                     for (j in 0 until controls.length()) {
                         val control = controls.getJSONObject(j)
                         if (control.getString("type") == "BUTTON") {
                             val enabled = control.getBoolean("enabled")
                             val name = control.getString("name")
                             mPlaybackController.updateControlButton(name, enabled)
                         } else if (control.getString("type") == "TOGGLE") {
                             val selected = control.getBoolean("selected")
                             val enabled = control.getBoolean("enabled")
                             val name = control.getString("name")
                             mPlaybackController.updateControlToggle(name, enabled, selected)
                         }
                     }
                 }
                 val title =
                     if (content.has("title")) content.getString("title") else ""
                 val artist =
                     if (content.has("titleSubtext1")) content.getString("titleSubtext1") else ""
                 val provider = content.getJSONObject("provider")
                 val name =
                     if (provider.has("name")) provider.getString("name") else ""
                 mPlaybackController.setPlayerInfo(title, artist, name)
             }

             // Log only if audio item has changed
             if (audioItemId != mCurrentAudioItemId) {
                 mCurrentAudioItemId = audioItemId

                 // Log payload
                 mLogger!!.postJSONTemplate(TemplateRuntimeHandler.sTag, playerInfo.toString(4))

                 // Log card
                 mLogger!!.postDisplayCard(content, LogRecyclerViewAdapter.RENDER_PLAYER_INFO)
             } else {
                 mLogger!!.postJSONTemplate(TemplateRuntimeHandler.sTag, playerInfo.toString(4))
             }
         } catch (e: JSONException) {
             mLogger!!.postError(TemplateRuntimeHandler.sTag, e.message)
         }*/
    }

    override fun clearTemplate() {
        // Handle dismissing display card here
        Timber.d("handle clearTemplate()")
    }

    override fun clearPlayerInfo() {
        Timber.d("handle clearPlayerInfo()")
        /* if (mPlaybackController != null && !mPlaybackController.getProvider()
                 .equals(MACCPlayer.SPOTIFY_PROVIDER_NAME)
         ) {
             mPlaybackController.setPlayerInfo("", "", "")
             mPlaybackController.hidePlayerInfoControls()
         }*/
    }
}