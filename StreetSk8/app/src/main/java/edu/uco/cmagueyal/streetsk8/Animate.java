package edu.uco.cmagueyal.streetsk8;

import android.graphics.Bitmap;

/**
 * Created by DogTownDog on 11/6/2016.
 */

public interface Animate {
    void    update();
    Bitmap  getImage();
    void    setFrames(Bitmap[] frames);
    void    setDelay(long d);
    void    setFrame(int i);
    int     getFrame();
    boolean getDone();
    void    setDone(boolean b);
}
