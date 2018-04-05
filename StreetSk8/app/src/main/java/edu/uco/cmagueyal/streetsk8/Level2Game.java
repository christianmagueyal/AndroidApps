package edu.uco.cmagueyal.streetsk8;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

/**
 * Created by DogTownDog on 11/27/2016.
 */

public class Level2Game  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new Level2Panel(this));
    }
  //  @Override
  //  protected void onDestroy(){
     //   Intent resultIntent = new Intent();
     //   resultIntent.putExtra("result",false);
     //   setResult(Activity.RESULT_OK,resultIntent);
   // }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}