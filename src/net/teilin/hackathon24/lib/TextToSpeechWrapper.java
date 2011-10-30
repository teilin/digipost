package net.teilin.hackathon24.lib;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public class TextToSpeechWrapper implements OnInitListener {

    private TextToSpeech tts;
    static final int TTS_CHECK_CODE = 0;
    private int ready = 0;
    private ReentrantLock waitForInitLock = new ReentrantLock();
    public String text;
    
    public TextToSpeechWrapper(Activity screen, String text) {
        ready = 0;
        tts = new TextToSpeech(screen, this);
        waitForInitLock.lock();
        this.text = text;
     }

	//@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			ready = 1;
			speak(this.text);
		}
		tts.setLanguage(Locale.US);
		waitForInitLock.unlock();
	}
    
    public int speak(String text) {
    	if(waitForInitLock.isLocked()) {
    		try {
    			waitForInitLock.tryLock(180, TimeUnit.SECONDS);
    		} catch(InterruptedException e) {
    			Log.e("TTSAPI", "interruped");
    		}
    		waitForInitLock.unlock();
    	}
    	if(ready==1) {
    		tts.speak(text, TextToSpeech.QUEUE_ADD, null);
    		return 1;
    	} else {
    		return 0;
    	}
    }

}