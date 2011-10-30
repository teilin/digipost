package net.teilin.hackathon24;

import net.teilin.hacathon24.R;
import net.teilin.hackathon24.lib.TextToSpeechWrapper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DigipostMainMenu extends Activity {
	public static final String LOG_TAG = "DigipostMainMenu";
	Button archiveButton;
	Button mailboxButton;
	Button kitchenButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.gc();
        setContentView(R.layout.main_menu);
        
        
        archiveButton = (Button) findViewById(R.id.buttonArchive);
        mailboxButton = (Button) findViewById(R.id.buttonMailBox);
        kitchenButton = (Button) findViewById(R.id.buttonKitchen);
        
        archiveButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
			}
		});
        
        mailboxButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						DigipostPostboks.class);
				Log.v("AUTH", "New Intent");
				try {
					startActivity(intent);
				} catch (Exception e) {

					Log.d(LOG_TAG, e.toString());
				}
				finish();
			}
		});
        
        kitchenButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(),
						DigipostKitchen.class);
				Log.v("AUTH", "New Intent");
				try {
					startActivity(intent);
				} catch (Exception e) {

					Log.d(LOG_TAG, e.toString());
				}
				finish();
			}
		});
	}
}
