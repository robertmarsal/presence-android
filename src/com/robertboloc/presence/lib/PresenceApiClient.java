package com.robertboloc.presence.lib;

import org.apache.http.client.HttpClient;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertboloc.presence.PresenceConstants;
import com.robertboloc.presence.R;
import com.robertboloc.presence.pojo.Token;

public class PresenceApiClient {

	private Context ctx;
	private Token token;
	private HttpClient httpClient;
	private ObjectMapper mapper;

	public PresenceApiClient(Context ctx) {
		this.ctx = ctx;
		this.httpClient = PresenceHttpClient.getNewHttpClient();
		this.mapper = new ObjectMapper();
		this.token = authenticate();
	}

	private Token authenticate() {

		// first check if we already have a valid token
		//TODO:
		
		// obtain the user UUID
		final String UUID = PreferenceManager.getDefaultSharedPreferences(ctx)
				.getString(PresenceConstants.UUID, "");
		// check if the UUID is valid
		if (UUID.isEmpty()) {
			Toast.makeText(ctx, R.string.brand, Toast.LENGTH_LONG)
					.show();
			return null;
		}
		// obtain the user MAC
		final WifiManager wifiMan = (WifiManager) ctx
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		final String MAC = wifiInf.getMacAddress();
		// check if the MAC address if valid
		//TODO:
		
		return null;
	}

}
