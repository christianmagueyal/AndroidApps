package edu.uco.cmagueyal.streetsk8;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by DogTownDog on 10/28/2016.
 */

public class InstructionsActivity extends Activity {

    TextView instructionsTitle;
    TextView instructionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_instructions);
        Typeface instFont = Typeface.createFromAsset(getAssets(), "fonts/adventure.ttf");
        instructionsTitle = (TextView) findViewById(R.id.instructions);
        instructionsList = (TextView) findViewById(R.id.instructions_list);
      //  instructionsTitle.setTypeface(instFont);
        String instructions = (
                "1. The object of the game is to score enough points to advance to the " +
                        "next level. \n" +
                "2. In order to keep playing, do not hit any obstacles. Doing so will mean" +
                        " you have to start from the beginning and lose all points. \n" +
                "3. Score as many points as you can by doing ollies over obstacles. Doing tricks" +
                        " over obstacles results in extra points! \n" +
                "4. To do an ollie you must first crouch down and prepare to pop the board into " +
                        "the air. PRESS AND HOLD on the screen to crouch.\n" +
                "5. While crouching down, the marker will appear. This marks how high you will "  +
                        "ollie. RELEASE PRESS to perform the ollie.\n" +
                "6. Make sure to charge up the ollie before the obstacle to get enough height to" +
                        "fully clear the obstacle.\n" +
                "7. Tricks are performed by swiping in mid air in one of four directions: UP, "   +
                        "DOWN, LEFT, RIGHT.\n" +
                "8. Watch out for the bananas! You will fall but no points are earned when you"   +
                        " Ollie them!\n"+
                "9. Cones can be destroyed by doing a powerslide. Scroll down and hold to do a"   +
                        " powerslide!\n"
        );
        instructionsList.setText(instructions);
    }// end onCreate . . .
}// end InstructionsActivity( . . .
