package edu.uco.cmagueyal.streetsk8;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends Activity {
                            //-12 move speed for real devices
    public static final int MOVESPEED = -20;
    public static final int BACKGROUNDWIDTH = 1200;

    Button playNow;
    Button practice;
    Button howToPlay;
  //  Button highScore;
    ImageButton options;
    public static int difficulty = 0;
    public static boolean levelUnlocked[] = {true, false, false};



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Make this activity, full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        options = (ImageButton) findViewById(R.id.options);
        playNow = (Button) findViewById(R.id.new_game);
        practice = (Button) findViewById(R.id.practice);
        howToPlay = (Button) findViewById(R.id.instructions);
     //   highScore = (Button) findViewById(R.id.scores);



        Typeface mainFont = Typeface.createFromAsset(getAssets(), "fonts/adventure.ttf");
        playNow.setTypeface(mainFont);
        practice.setTypeface(mainFont);
        howToPlay.setTypeface(mainFont);
  //      highScore.setTypeface(mainFont);
        options.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(i);
            }
        });
        playNow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, StageActivity.class);
                startActivity(i);
            }
        });
        practice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, PracticeGame.class);
                startActivity(i);
            }
        });
        howToPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, InstructionsActivity.class);
                startActivity(i);
            }
        });
   //     highScore.setOnClickListener(new View.OnClickListener(){
   //         @Override
   //         public void onClick(View v){
   //             Intent i = new Intent(MainActivity.this, ScoresActivity.class);
   //             startActivity(i);
   //         }
   //     }
   //     );
    }//OnCreate
}
