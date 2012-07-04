package com.robertboloc.presence;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.User;

public class ProfileActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		// get the user data
		PresenceApiClient client = new PresenceApiClient(this);
		User user = client.getUserData();

		// populate the view with the data
		final TextView userName = (TextView) findViewById(R.id.profileName);
		userName.setText(user.getFirstname() + " " + user.getLastname());
		
		final TextView userPosition = (TextView) findViewById(R.id.profilePosition);
		userPosition.setText(user.getPosition());
		
	}

}
