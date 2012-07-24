package com.robertboloc.presence;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.User;

public class ProfileActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		final Button updateProfileButton = (Button) findViewById(R.id.generateReport);
		updateProfileButton.setOnClickListener(updateProfileButtonListener);
		
		// get the user data
		PresenceApiClient client = new PresenceApiClient(this);
		User user = client.getUserData();

		// populate the view with the data
		final TextView editFirstName = (TextView) findViewById(R.id.editFirstName);
		editFirstName.setText(user.getFirstname());
		
		final TextView editLastName = (TextView) findViewById(R.id.editLastName);
		editLastName.setText(user.getLastname());
		
		final TextView editIdentifier = (TextView) findViewById(R.id.editIdentifier);
		editIdentifier.setText(user.getIdentifier());
		
	}
	
	/**
	 * OnClickListener for the generate button
	 */
	public OnClickListener updateProfileButtonListener = new View.OnClickListener() {
		public void onClick(View v) {

		}
	};

}
