package com.robertboloc.presence;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.Report;

public class ReportDisplayActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_display);
		
		// obtain the user params
		Intent i = this.getIntent();
		String start = i.getStringExtra("start");
		String end = i.getStringExtra("end");
		
		// instance of the api client
		PresenceApiClient client = new PresenceApiClient(
				getApplicationContext());
		
		// append the user params
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("start", start));
		params.add(new BasicNameValuePair("end", end));

		// get the user report
		Report report = client.getUserReport(params);
		
		//display the report
	}

}
