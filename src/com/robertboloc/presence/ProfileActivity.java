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
import android.widget.TextView;
import android.widget.Toast;

import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.User;

public class ProfileActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		final Button updateProfileButton = (Button) findViewById(R.id.updateProfile);
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
			
			final TextView editFirstName = (TextView) findViewById(R.id.editFirstName);		
			final TextView editLastName = (TextView) findViewById(R.id.editLastName);
			final TextView editIdentifier = (TextView) findViewById(R.id.editIdentifier);
			
			//get the params
			String firstname = editFirstName.getText().toString();
			String lastname = editLastName.getText().toString();
			String identifier = editIdentifier.getText().toString();
			
			//check that we have all the params
			if(!firstname.isEmpty() && !lastname.isEmpty() && !identifier.isEmpty()){
			
				List<NameValuePair> params = new LinkedList<NameValuePair>();
				params.add(new BasicNameValuePair("firstname", firstname));
				params.add(new BasicNameValuePair("lastname", lastname));
				params.add(new BasicNameValuePair("identifier", identifier));
				
				//instance of the api client
				PresenceApiClient client = new PresenceApiClient(
						getApplicationContext());
				
				//update the user data
				client.updateUser(params);
				
				Toast.makeText(getApplicationContext(), R.string.profile_updated, Toast.LENGTH_LONG)
				.show();
			}
		}
	};

}
