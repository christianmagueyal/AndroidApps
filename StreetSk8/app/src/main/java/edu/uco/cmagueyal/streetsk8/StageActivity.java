package edu.uco.cmagueyal.streetsk8;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by DogTownDog on 11/27/2016.
 */

public class StageActivity extends Activity{
    public static final String TAG = "edu.uco.cmagueyal.p3ChristianM.stages";



    Button level1, level2, level3;
    boolean unlocked[] = MainActivity.levelUnlocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_stages);
        Typeface stagesFont = Typeface.createFromAsset(getAssets(), "fonts/adventure.ttf");

        level1 = (Button) findViewById(R.id.button1);
        level2 = (Button) findViewById(R.id.button2);
        level3 = (Button) findViewById(R.id.button3);
        // will probs not use the font but keep for now 11/27
        level1.setTypeface(stagesFont);
        level2.setTypeface(stagesFont);
        level3.setTypeface(stagesFont);
        if(!unlocked[0]){
            level1.setClickable(false);
        }

        if(!unlocked[1]){
            level2.setClickable(false);
        }

        if(!unlocked[2]){
            level3.setClickable(false);
        }

        level1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(StageActivity.this, Level1Game.class);
                startActivityForResult(i,1);

            }
        });

        level2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(StageActivity.this, Level2Game.class);
                startActivityForResult(i,2);
            }
        });
        level3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(StageActivity.this, Level3Game.class);
                   startActivityForResult(i,3);
            }
        });
        if(!unlocked[1]){
            level2.setClickable(false);
        }
        if(!unlocked[2]){
            level3.setClickable(false);
        }
    }//end: OnCreate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode ==1){
            if(resultCode == Activity.RESULT_OK){
                MainActivity.levelUnlocked[1] = data.getBooleanExtra("result",false);
                unlocked = MainActivity.levelUnlocked;
                if(unlocked[1]){
                    level2.setClickable(true);
                }

            }
        }
        if(requestCode ==2){
            if(resultCode == Activity.RESULT_OK){
                MainActivity.levelUnlocked[2] = data.getBooleanExtra("result",false);
                unlocked = MainActivity.levelUnlocked;
                if(unlocked[2]){
                    level3.setClickable(true);
                }

            }
        }
    }
}
