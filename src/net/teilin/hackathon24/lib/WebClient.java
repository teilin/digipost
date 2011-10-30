package net.teilin.hackathon24.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;

public class WebClient {
	public static final String LOG_TAG = "WebClient";
	private DigipostUser user;
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpContext localContext = new BasicHttpContext();
	CookieStore cookieStore = httpClient.getCookieStore();
	// private boolean abort;
	// private String ret;

	HttpResponse response = null;
	HttpPost httpPost = null;

	public WebClient(DigipostUser user) {

		this.user = user;
	}

	// @SuppressWarnings("null")
	public boolean authenticate() {
		// ret = null;
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.RFC_2109);
		HttpPost post = new HttpPost(
				"https://www.digipost.no/post/passordautentisering");
		response = null;
		List<NameValuePair> keyValue = new ArrayList<NameValuePair>();
		BasicHttpContext myCookies = new BasicHttpContext();
		myCookies.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		try {

			//keyValue.add(new BasicNameValuePair("foedselsnummer", user.getPID()));
			//keyValue.add(new BasicNameValuePair("passord", user.getPassword()));
			keyValue.add(new BasicNameValuePair("foedselsnummer", "30088847993"));
			keyValue.add(new BasicNameValuePair("passord", "oipt737OI!"));
			post.setEntity(new UrlEncodedFormEntity(keyValue));
			HttpResponse response = httpClient.execute(post, myCookies);
			// Log.d(LOG_TAG, "Error of the error");
			//
			// Log.d(LOG_TAG, "Error of the error");
			// String line = "";

			int status_code = response.getStatusLine().getStatusCode();

			if (status_code == 200) {
				Log.d(LOG_TAG, "Login SUCCESS!!!");
				return true;
			} else {
				Log.d(LOG_TAG, "Login Failed!!!");
				return false;
			}

		} catch (Exception e) {

			Log.d(LOG_TAG, e.toString());
		}
		return false;
	}
	
	public void Write2File(InputStream is) throws Exception {
		File root = Environment.getExternalStorageDirectory();
		FileOutputStream f = new FileOutputStream(new File(root,"test.pdf"));
		byte[] buffer = new byte[1024];
		int len1 = 0;
		while((len1 = is.read(buffer))>0) {
			f.write(buffer,0,len1);
		}
		f.close();
	}
	
	public void speakFile() throws IOException {
		File root = Environment.getExternalStorageDirectory();
		try {
		Pdf2Text p2t = new Pdf2Text(root.toString()+"/test.pdf");
		String text = p2t.getText();
		}catch(Exception e){
			Log.e(LOG_TAG,root.toString()+"/test.pdf");
		}
		Log.v("TEXT",root.toString()+"/test.pdf");
	}
	
	public List<String> getKitchenContent() { 
		BufferedReader in = null;
		List<String> stringList = new ArrayList<String>();
		try{
			Log.v(LOG_TAG, "Getting account");
			HttpGet get = new HttpGet(
					"https://www.digipost.no/post/privat/konto");
			HttpResponse response = httpClient.execute(get);
			Log.v(LOG_TAG, response.getStatusLine().toString());
			
			in = new BufferedReader
		            (new InputStreamReader(response.getEntity().getContent()));
			
		    StringBuffer sb = new StringBuffer("");
		    String line = "";
		    String NL = System.getProperty("line.separator");
		    while ((line = in.readLine()) != null) {
		    	sb.append(line + NL);
		    }
		    
		    in.close();
		    String jString = sb.toString();
		    
		    JSONObject jObject = new JSONObject(jString);
		    
		    String kitchenURI = jObject.getString("kjokkenbenkUri");
		    Log.v("kjokkenbenkUri",kitchenURI);
		    get = new HttpGet(kitchenURI);
		    
		    response = httpClient.execute(get);
		    Log.v(LOG_TAG, response.getStatusLine().toString());
			
			in = new BufferedReader
		            (new InputStreamReader(response.getEntity().getContent()));
			
		    sb = new StringBuffer("");
		    line = "";
		    
		    while ((line = in.readLine()) != null) {
		    	sb.append(line + NL);
		    }
		    
		    in.close();
		    jString = sb.toString();
		    
		    JSONArray jArray = new JSONArray(jString);
		    JSONObject firstLetter = jArray.getJSONObject(0);
		    String letterURI = firstLetter.getString("brevUri");
		    get = new HttpGet(letterURI);
		    response = httpClient.execute(get);
		    
		    InputStream inS = response.getEntity().getContent();
		    Write2File(inS);
		    Log.v("Stream","Stream");
		    speakFile();
			
		    
	    } catch (Exception e) {
			Log.e(LOG_TAG,e.getMessage());
	    	if (in != null) {
	    		try {
	    			in.close();
	    		} catch (IOException ef) {
	    			Log.e(LOG_TAG,ef.getMessage());
	    		}
	    	}
		
		
		
	    }
		return stringList;
	}
//		return stringList;
}
