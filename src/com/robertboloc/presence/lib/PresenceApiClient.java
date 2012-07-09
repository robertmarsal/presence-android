package com.robertboloc.presence.lib;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertboloc.presence.PresenceConstants;
import com.robertboloc.presence.R;
import com.robertboloc.presence.SettingsActivity;
import com.robertboloc.presence.pojo.Status;
import com.robertboloc.presence.pojo.Token;
import com.robertboloc.presence.pojo.User;

public class PresenceApiClient {

	private Context ctx;
	private Token token;
	private HttpClient httpClient;
	private ObjectMapper mapper;
	private String provider;

	public PresenceApiClient(Context ctx) {
		this.ctx = ctx;
		this.provider = PreferenceManager.getDefaultSharedPreferences(ctx)
				.getString(PresenceConstants.PROVIDER, "");
		// to continue we must have a provider set
		if (this.provider.isEmpty()) {
			Toast.makeText(ctx, R.string.error_noprovider, Toast.LENGTH_LONG)
					.show();
			ctx.startActivity(new Intent(ctx, SettingsActivity.class));
		} else { // continue
			this.httpClient = PresenceHttpClient.getNewHttpClient();
			this.mapper = new ObjectMapper();
			this.token = authenticate();
		}
	}

	private Token authenticate() {

		// first check if we already have a valid token
		Date date = new Date();
		if (this.token != null
				&& this.token.getTimeexpires().after(
						new Timestamp(date.getTime()))) {
			// the token is still valid
			return this.token;
		}

		// obtain the user UUID
		final String UUID = PreferenceManager.getDefaultSharedPreferences(ctx)
				.getString(PresenceConstants.UUID, "");
		// check if the UUID is valid
		if (UUID.isEmpty()) {
			Toast.makeText(ctx, R.string.error_nouuid, Toast.LENGTH_LONG)
					.show();
			return null;
		}

		// obtain the user MAC
		final WifiManager wifiMan = (WifiManager) ctx
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		final String MAC = wifiInf.getMacAddress();
		// check if the MAC address if valid
		if (MAC == null) {
			Toast.makeText(ctx, R.string.error_nowifi, Toast.LENGTH_LONG)
					.show();
			return null;
		}

		// authenticate
		List<NameValuePair> authParams = new LinkedList<NameValuePair>();
		authParams.add(new BasicNameValuePair("UUID", String.valueOf(UUID)));
		authParams.add(new BasicNameValuePair("MAC", String.valueOf(MAC)));
		String authResponse = this.get("user/authenticate", authParams);

		// save the token
		try {
			return this.mapper.readValue(authResponse, Token.class);
		} catch (Exception e) {
			return null;
		}
	}

	private String get(String petition, List<NameValuePair> params) {

		// check if params list is empty and create it if so
		if (params == null) {
			params = new LinkedList<NameValuePair>();
		}

		// check for token
		if (this.token != null) {
			// add the token to the params
			params.add(new BasicNameValuePair("token", String
					.valueOf(this.token.getToken())));
		}
		// build the url
		String paramsString = URLEncodedUtils.format(params, "utf-8");
		String url = this.provider + "/" + petition + "?" + paramsString;

		// make the request
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = this.httpClient.execute(request);
		} catch (Exception e) {
			Toast.makeText(ctx, R.string.error_norequest, Toast.LENGTH_LONG)
					.show();
			return null;
		}

		// treat the response
		String responseBody;
		try {
			responseBody = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			responseBody = null;
		}

		return responseBody;
	}

	public User getUserData() {

		String userData = this.get("user/data", null);
		try {
			return this.mapper.readValue(userData, User.class);
		} catch (Exception e) {
			return null;
		}
	}

	public Status getUserStatus() {

		String userStatus = this.get("user/status", null);
		try {
			return this.mapper.readValue(userStatus, Status.class);
		} catch (Exception e) {
			return new Status();
		}
	}

}
