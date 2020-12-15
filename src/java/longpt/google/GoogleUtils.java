/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.google;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author phamt
 */
public class GoogleUtils {
    private static final String GOOGLE_CLIENT_ID = "1017604108080-4vqusr3v1c7ecjd7at2095okof7q9vkh.apps.googleusercontent.com";
    private static final String GOOGLE_CLIENT_SECRET = "x866eciIQebb8Uo3cXdSBavr";
    private static final String GOOGLE_REDIRECT_URI = "http://localhost:8084/J3.L.P0003_BookHotelOnline/LoginGoogle";
    private static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    private static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    private static final String GOOGLE_GRANT_TYPE = "authorization_code";
    
     public static String getToken(String code) throws IOException{
	//prepare data to send to google
	List<NameValuePair> getTokenInfoPairs = Form.form().add("client_id", GOOGLE_CLIENT_ID)
						.add("client_secret", GOOGLE_CLIENT_SECRET)
						.add("redirect_uri", GOOGLE_REDIRECT_URI)
						.add("code", code)
						.add("grant_type", GOOGLE_GRANT_TYPE)
						.build();
	
	//send request to google to get json
	String responseJson = Request.Post(GOOGLE_LINK_GET_TOKEN).bodyForm(getTokenInfoPairs).execute().returnContent().asString();
	
	//process return json
	Gson gson = new Gson();
	JsonObject jsonObject = gson.fromJson(responseJson, JsonObject.class);
	String accessToken = jsonObject.get("access_token").toString().replace("\"", "");
	
	return accessToken;
    }
    
    public static GooglePojo getUserInfo (String accessToken) throws IOException{
	//prepare link to get user info
	String getUserInfoLink = GOOGLE_LINK_GET_USER_INFO + accessToken;
	
	//send request to google to get json
	String responseJson = Request.Get(getUserInfoLink).execute().returnContent().asString();
	
	//process return json
	Gson gson = new Gson();
	GooglePojo googlePojo = gson.fromJson(responseJson, GooglePojo.class);
	
	return googlePojo;
    }
}
