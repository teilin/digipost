package net.teilin.hackathon24;

import net.teilin.hacathon24.R;
import net.teilin.hackathon24.lib.TextToSpeechWrapper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DigipostPostboks extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v("Activity","Postboks");
		super.onCreate(savedInstanceState);
		System.gc();
		TextToSpeechWrapper t2s = new TextToSpeechWrapper(this, "Postboks");
        setContentView(R.layout.main);
	}
}
