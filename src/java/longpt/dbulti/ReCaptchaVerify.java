/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.dbulti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Long Le
 */
public class ReCaptchaVerify {
    public static final String SITE_KEY = "6Lcvtv0ZAAAAAGWiju75FrjEg0vyC9dSEoecPvpA";
    public static final String SECRET_KEY = "6Lcvtv0ZAAAAAKBXM2qaQ_bjI92H02SEOyf18i2i";
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    
    public static boolean verify(String gRecaptchaResponse) 
	    throws MalformedURLException, IOException{
	if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0){
	    return false;
	}
	URL verifyUrl = new URL(SITE_VERIFY_URL);

	//open connection to verifyUrl
	HttpsURLConnection cnn = (HttpsURLConnection) verifyUrl.openConnection();
	
	//add Header infomation into Request to send to server
	cnn.setRequestMethod("POST");
	cnn.setRequestProperty("User-Agent", "Mozilla/5.0");
	cnn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	
	// Data to send to Server.
	String postParams = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;

	// Send Request
	cnn.setDoOutput(true);
	
	// Get Output Stream from Server.
	// Write data Output Stream (send data Server).
	OutputStream outStream = cnn.getOutputStream();
	outStream.write(postParams.getBytes());

	outStream.flush();
	outStream.close();

	// get Input Stream of Connection
	// read data Server.
	InputStream inputStream = cnn.getInputStream();

	JsonReader jsonReader = Json.createReader(inputStream);
	JsonObject jsonObject = jsonReader.readObject();
	jsonReader.close();

	//in json return by server: {"success": true}
	boolean success = jsonObject.getBoolean("success");
	
	return success;    
    }
}
