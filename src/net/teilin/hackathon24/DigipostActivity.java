package net.teilin.hackathon24;

import net.teilin.hacathon24.R;
import net.teilin.hackathon24.lib.DigipostUser;
import net.teilin.hackathon24.lib.WebClient;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DigipostActivity extends Activity {
	/** Called when the activity is first created. */
	Button loginbutton;
	public static final String LOG_TAG = "Digipost";
	Thread t;
	private SharedPreferences mPreferences;
	ProgressDialog dialog;
	public static WebClient client;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
		if (!checkLoginInfo()) {
			loginbutton = (Button) findViewById(R.id.loginButton);
			loginbutton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					showDialog(0);
					t = new Thread() {
						public void run() {
							tryLogin();
						}
					};
					t.start();
				}
			});
		} else {
			// IF ALREADY LOGGED IN!
		}
	}

	private final boolean checkLoginInfo() {
		boolean username_set = mPreferences.contains("UserName");
		boolean password_set = mPreferences.contains("PassWord");
		if (username_set || password_set) {
			return true;
		}
		return false;

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			dialog = new ProgressDialog(this);
			dialog.setMessage("Vennligst vent mens tilkobing");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		return null;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String loginmsg = (String) msg.obj;
			if (loginmsg.equals("SUCCESS")) {
				removeDialog(0);
				Log.v("AUTH", "SUCCESS");
				Intent intent = new Intent(getApplicationContext(),
						DigipostMainMenu.class);
				Log.v("AUTH", "New Intent");
				try {
					startActivity(intent);
				} catch (Exception e) {

					Log.d(LOG_TAG, e.toString());
				}
				finish();
				Log.v("AUTH", "End Handler");
			}
		}
	};

	public void tryLogin() {
		EditText user = (EditText) findViewById(R.id.username);
		EditText pass = (EditText) findViewById(R.id.password);
		String username = user.getText().toString();
		String password = pass.getText().toString();
		DigipostUser digiUser = new DigipostUser(username, password);
		this.client = new WebClient(digiUser);
		boolean success = this.client.authenticate();
		if (success) {
			Message myMessage = new Message();
			myMessage.obj = "SUCCESS";
			handler.sendMessage(myMessage);
		} else {
			Log.v(LOG_TAG, "login failed");
		}

	}
}