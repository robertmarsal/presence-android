package com.robertboloc.presence;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.Report;

public class ReportActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);

		final Button generateReportButton = (Button) findViewById(R.id.generateReport);
		generateReportButton.setOnClickListener(generateReportButtonListener);
	}

	/**
	 * OnClickListener for the generate button
	 */
	public OnClickListener generateReportButtonListener = new View.OnClickListener() {
		public void onClick(View v) {

			final DatePicker startDate = (DatePicker) findViewById(R.id.startdate);
			final DatePicker endDate = (DatePicker) findViewById(R.id.enddate);

			String start = startDate.getDayOfMonth() + "-"
					+ startDate.getMonth() + "-" + startDate.getYear();
			String end = endDate.getDayOfMonth() + "-" + endDate.getMonth()
					+ "-" + endDate.getYear();

			// instance of the api client
			PresenceApiClient client = new PresenceApiClient(
					getApplicationContext());

			// add the user params
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("start", start));
			params.add(new BasicNameValuePair("end", end));

			// get the user report
			Report report = client.getUserReport(params);
			report.getCheckins();
		}
	};
}
