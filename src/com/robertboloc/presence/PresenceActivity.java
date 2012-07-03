package com.robertboloc.presence;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.User;

public class PresenceActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.onInstall();
		setContentView(R.layout.main);

		final String[] OPTIONS = new String[] { getString(R.string.activity),
				getString(R.string.report), getString(R.string.profile) };

		ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(this,
				R.layout.list_item, OPTIONS);

		// configure the main menu
		final ListView mainMenu = (ListView) findViewById(R.id.mainMenu);
		mainMenu.setAdapter(menuAdapter);
		mainMenu.setOnItemClickListener(mainMenuListener);
		mainMenu.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT,
				new int[] { 0, 0xFF808080, 0 }));
		mainMenu.setDividerHeight(1);

		// configure the main button
		final Button mainButton = (Button) findViewById(R.id.mainButton);
		mainButton.getBackground().setColorFilter(0xFF00FF00,
				PorterDuff.Mode.MULTIPLY);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.incidence:
			// TODO: show incindence screen
			return true;
		case R.id.settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * OnItemClickListener for the ListView that contains the main menu
	 */
	public OnItemClickListener mainMenuListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			switch (position) {
			case 0: // Activity
				startActivity(new Intent(view.getContext(),
						ActivityActivity.class));
				break;
			case 1: // Report
				startActivity(new Intent(view.getContext(),
						ReportActivity.class));
				break;
			case 2: // Profile
				startActivity(new Intent(view.getContext(),
						ProfileActivity.class));
				break;
			}
		}
	};

	/**
	 * Checks if this is the first run after the install, if it is it creates a
	 * UUID variable that uniquely identifies the device
	 * 
	 * @return boolean
	 */
	public boolean onInstall() {
		SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean firstRun = p.getBoolean(PresenceConstants.FIRST_RUN, true);
		// if is the first time we run the app, generate and store an UUID
		if (firstRun) {
			UUID uuid = UUID.randomUUID();
			p.edit().putString(PresenceConstants.UUID, uuid.toString())
					.commit();
			p.edit().putBoolean(PresenceConstants.FIRST_RUN, false).commit();
			return true;
		}
		return false;
	}
}