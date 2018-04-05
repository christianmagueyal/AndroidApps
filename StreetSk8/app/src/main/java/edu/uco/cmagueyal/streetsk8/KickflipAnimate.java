package edu.uco.cmagueyal.streetsk8;

import android.graphics.Bitmap;

/**
 * Created by DogTownDog on 11/13/2016.
 */

public class KickflipAnimate implements Animate {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    @Override
    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrame = 25;
        startTime = System.nanoTime();
        playedOnce = false;
    }
    @Override
    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame= i;}
    @Override
    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>delay && currentFrame < 29){
            currentFrame++;
            startTime = System.nanoTime();
        }
        else if (currentFrame == 29)
            playedOnce = true;
    }
    @Override
    public Bitmap getImage(){
        return frames[currentFrame];
    }
    @Override
    public int getFrame(){return currentFrame;}
    @Override
    public boolean getDone() {
        return playedOnce;
    }
    @Override
    public void setDone(boolean b) {}
    //public boolean playedOnce(){return playedOnce;}
}
