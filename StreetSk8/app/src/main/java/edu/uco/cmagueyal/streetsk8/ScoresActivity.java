package edu.uco.cmagueyal.streetsk8;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by DogTownDog on 10/29/2016.
 */

public class ScoresActivity extends Activity {

    public static final String WIN_LOG = "edu.uco.cmagueyal.p3ChristianM.win_log";
    private StringBuilder winsBuilder = new StringBuilder("");
    public ArrayList<Score> loggedScores = new ArrayList<Score>();
    TextView name;
    TextView scores;
    TextView infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_scores);

        loggedScores.add(new Score("name1", 100));
        loggedScores.add(new Score("name4", 400));
        loggedScores.add(new Score("name2", 200));
        loggedScores.add(new Score("name3", 300));
        loggedScores.add(new Score("name6", 600));
        loggedScores.add(new Score("name5", 500));

        Collections.sort( loggedScores);
        Collections.reverse(loggedScores);

        Typeface mainFont = Typeface.createFromAsset(getAssets(), "fonts/adventure.ttf");

        name = (TextView) findViewById(R.id.logName);
        scores = (TextView) findViewById(R.id.logScore);
        infoList= (TextView) findViewById(R.id.logWins);

        for(Score s : loggedScores){
            winsBuilder.append("                    " + s.getName() + "                                   " + s.getScore() + "  \n");
        }
        infoList.setText(winsBuilder.toString());
    }//onCreate
}
