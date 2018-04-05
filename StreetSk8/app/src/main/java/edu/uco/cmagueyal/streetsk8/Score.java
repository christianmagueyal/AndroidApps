package edu.uco.cmagueyal.streetsk8;

/**
 * Created by DogTownDog on 10/29/2016.
 */

public class Score implements Comparable<Score> {
    private String playerName = null;
    private int playerScore = 0;
    public Score(String name, int score){
        this.playerName = name;
        this.playerScore = score;
    }

    @Override
    public int compareTo(Score other) {
        if(this.getScore() > other.getScore())
            return 1;
        else if(this.getScore() < other.getScore())
            return -1;
        else
            return 0;
    }
    public String getName(){
        return playerName;
    }
    public int getScore(){
        return playerScore;
    }
}
