package com.robertboloc.presence;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.Status;
import com.robertboloc.presence.pojo.User;

public class PresenceActivity extends Activity {

	private static PresenceApiClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.onInstall();
		setContentView(R.layout.main);

		this.refresh();

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
			client.incidence();
			this.refresh();
			return true;
		case R.id.refresh:
			this.refresh();
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
	 * OnClickListener for the main button
	 */
	public OnClickListener mainButtonListener = new View.OnClickListener() {
		public void onClick(View v) {
			Status st = client.getUserStatus();
			if (st.getStatus().equalsIgnoreCase(PresenceConstants.CHECKIN)) {
				st = client.checkout();
			} else if (st.getStatus().equalsIgnoreCase(
					PresenceConstants.CHECKOUT)) {
				st = client.checkin();
			}
			updateMainButtonStatus(st.getStatus());
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

			// show (only ONCE) the UUID on screen so the user can be created
			AlertDialog uuidAlert = new AlertDialog.Builder(this).create();
			uuidAlert.setTitle(R.string.popup_uuid_title);
			uuidAlert.setMessage(uuid.toString());
			uuidAlert.show();

			return true;
		}
		return false;
	}

	private void updateMainButtonStatus(String status) {
		final Button button = (Button) findViewById(R.id.mainButton);

		if (status.equalsIgnoreCase(PresenceConstants.CHECKOUT)
				|| status.equalsIgnoreCase(PresenceConstants.UNDEFINED)) {
			// set text to checkin
			button.setText(R.string.checkin);
			// set button color to green
			button.getBackground().setColorFilter(0xFF00FF00,
					PorterDuff.Mode.MULTIPLY);
		} else if (status.equalsIgnoreCase(PresenceConstants.CHECKIN)) {
			// set text to checkout
			button.setText(R.string.checkout);
			// set button color to red
			button.getBackground().setColorFilter(0xFFFF0000,
					PorterDuff.Mode.MULTIPLY);
		} else { // bad things happened, hide the button
			button.setVisibility(View.INVISIBLE);
		}
	}

	private void refresh() {

		// get the views that form the status bar
		final TextView userName = (TextView) findViewById(R.id.profileName);
		final TextView userPosition = (TextView) findViewById(R.id.profilePosition);
		final Button mainButton = (Button) findViewById(R.id.mainButton);

		// create an instance of the api client
		client = new PresenceApiClient(this);

		// get the user data
		User user = client.getUserData();

		// if we can not get the data we are offline
		if (user != null) {

			final String[] OPTIONS = new String[] {
					getString(R.string.activity), getString(R.string.report),
					getString(R.string.profile) };

			ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(this,
					R.layout.list_item, OPTIONS);

			// configure the main menu
			final ListView mainMenu = (ListView) findViewById(R.id.mainMenu);
			mainMenu.setAdapter(menuAdapter);
			mainMenu.setOnItemClickListener(mainMenuListener);
			mainMenu.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT,
					new int[] { 0, 0xFF808080, 0 }));
			mainMenu.setDividerHeight(1);

			// populate the view with the data
			userName.setText(user.getFirstname() + " " + user.getLastname());
			userPosition.setText(user.getPosition());

			// get the user status
			Status status = client.getUserStatus();

			// configure the main button based on user status
			mainButton.setOnClickListener(mainButtonListener);
			updateMainButtonStatus(status.getStatus());

		} else { // display offline status
			userName.setText(R.string.offline);
			userPosition.setText(R.string.error_nostatus);
			updateMainButtonStatus(PresenceConstants.NULL_STRING);
		}
	}
}