package net.teilin.hackathon24;

import java.util.List;

import net.teilin.hacathon24.R;
import net.teilin.hackathon24.lib.TextToSpeechWrapper;
import net.teilin.hackathon24.lib.WebClient;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DigipostKitchen extends Activity {
	public static final String LOG_TAG = "Kitchen";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.gc();
		Log.v(LOG_TAG,"Entered kitchen");
		WebClient wc = DigipostActivity.client;
		Log.v(LOG_TAG,"Entered kitchen");
		TextToSpeechWrapper t2s = new TextToSpeechWrapper(this, "Kjøkkenbenk");
		List<String> list = wc.getKitchenContent();
        setContentView(R.layout.kitchen);
	}
}
