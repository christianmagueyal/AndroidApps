package edu.uco.cmagueyal.streetsk8;

import android.graphics.Bitmap;

/**
 * Created by DogTownDog on 11/6/2016.
 */

public class RollingAnimate implements Animate {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    @Override
    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }
    @Override
    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame= i;}
    @Override
    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;

        if(elapsed>delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame >= 8){
            currentFrame = 0;
            playedOnce = true;
        }
    }
    @Override
    public Bitmap getImage(){
        return frames[currentFrame];
    }
    @Override
    public int getFrame(){return currentFrame;}
    @Override
    public boolean getDone() {
        return false;
    }
    @Override
    public void setDone(boolean b) {}
    public boolean playedOnce(){return playedOnce;}
}
