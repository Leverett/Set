package com.set.activities;

import com.solo.set.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void startSinglePlayer(View singleplayer){
    	Intent intent = new Intent(this, SinglePlayerScreen.class);
    	startActivity(intent);
    }
    
    public void createMultiplayer(View createMultiplayer){
    	Context context = getApplicationContext();
    	Toast toast = Toast.makeText(context, "Feature not implemented yet", Toast.LENGTH_SHORT);
		toast.show();
    }
    
    public void joinMultiplayer(View joinMultiplayer){
    	Context context = getApplicationContext();
    	Toast toast = Toast.makeText(context, "Feature not implemented yet", Toast.LENGTH_SHORT);
		toast.show();
    }
}
