package info.thewordnerd.spiel.scripts

import android.view.accessibility.AccessibilityEvent

import info.thewordnerd.spiel.presenters._
import info.thewordnerd.spiel.utils.TTS

class Script(val pkg:String) {

  protected val tts = TTS

  protected def on(cls:String) = {
    val str = if(cls.startsWith(".")) pkg+cls else cls
    new Handler(pkg, str)
  }
}

class Handler(val pkg:String, val cls:String) {
  def viewFocused(f:((AccessibilityEvent) => Boolean)) = { ViewFocused.handlers(pkg -> cls) = f
  android.util.Log.d(this.toString, "Registered focus handler for "+cls+" on "+pkg)
  }
}
