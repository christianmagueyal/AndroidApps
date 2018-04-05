package edu.uco.cmagueyal.streetsk8;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

/**
 * Created by DogTownDog on 11/6/2016.
 */

public class PracticeGame extends Activity{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        //set to full screen
             getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PracticePanel pP = new PracticePanel(this);
        setContentView(pP);
    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
