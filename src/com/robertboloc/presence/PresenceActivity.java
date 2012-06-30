package com.robertboloc.presence;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PresenceActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final String[] OPTIONS =
        		new String[]{getString(R.string.activity),
        					 getString(R.string.report),
        					 getString(R.string.profile)};
        	 
        ArrayAdapter<String> menuAdapter =
        		new ArrayAdapter<String>(this,
        	        R.layout.list_item, OPTIONS);
        	 
        ListView mainMenu = (ListView)findViewById(R.id.mainMenu);
        	 
        mainMenu.setAdapter(menuAdapter);
    }
}