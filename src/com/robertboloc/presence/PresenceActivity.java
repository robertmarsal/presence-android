package com.robertboloc.presence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                //TODO: show incindence screen
                return true;
            case R.id.settings:
            	startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}