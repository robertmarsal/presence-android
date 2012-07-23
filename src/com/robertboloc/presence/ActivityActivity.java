package com.robertboloc.presence;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.robertboloc.presence.lib.PresenceApiClient;
import com.robertboloc.presence.pojo.Interval;

public class ActivityActivity extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        
		// get the user data
		PresenceApiClient client = new PresenceApiClient(this);
		ArrayList<Interval> intervals = client.getUserActivity();
		
		ArrayAdapter<Interval> activityAdapter = new ActivityListAdapter(this, intervals);
		
		final ListView activityView = (ListView) findViewById(R.id.activityView);
		activityView.setAdapter(activityAdapter);
		activityView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT,
				new int[] { 0, 0xFF808080, 0 }));
		activityView.setDividerHeight(1);
    }

}
