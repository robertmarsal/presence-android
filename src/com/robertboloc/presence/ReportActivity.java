package com.robertboloc.presence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

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

			// fetch the user data
			final DatePicker startDate = (DatePicker) findViewById(R.id.startdate);
			final DatePicker endDate = (DatePicker) findViewById(R.id.enddate);

			String start = startDate.getDayOfMonth() + "-"
					+ startDate.getMonth() + "-" + startDate.getYear();
			String end = endDate.getDayOfMonth() + "-" + endDate.getMonth()
					+ "-" + endDate.getYear();

			// display the report
			Intent i = new Intent(v.getContext(),ReportDisplayActivity.class);
			i.putExtra("start", start);
			i.putExtra("end", end);
			startActivity(i);
		}
	};
}
