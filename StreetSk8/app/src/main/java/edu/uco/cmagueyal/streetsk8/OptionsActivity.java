package edu.uco.cmagueyal.streetsk8;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by DogTownDog on 10/30/2016.
 */

public class OptionsActivity extends Activity {
    TextView GameDiff;
    RadioGroup onlyGroup;
    RadioButton easy;
    RadioButton normal;
    RadioButton hard;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        onlyGroup = (RadioGroup) findViewById(R.id.radiogroup);
        GameDiff = (TextView) findViewById(R.id.label_difficulty);
        easy = (RadioButton) findViewById(R.id.radio_easy);
        normal = (RadioButton) findViewById(R.id.radio_normal);
        hard = (RadioButton) findViewById(R.id.radio_hard);
        RadioButtonListener radioButtonListener = new RadioButtonListener();
        easy.setOnClickListener(radioButtonListener);
        normal.setOnClickListener(radioButtonListener);
        hard.setOnClickListener(radioButtonListener);
    }
    class RadioButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // find which button was clicked
            switch (view.getId()) {
                case R.id.radio_easy:
                    MainActivity.difficulty = 0;
                    break;
                case R.id.radio_normal:
                    MainActivity.difficulty = 1;
                    break;
                case R.id.radio_hard:
                    MainActivity.difficulty = 2;
                    break;
                default:
                    break;
            }
        }
    }
}
