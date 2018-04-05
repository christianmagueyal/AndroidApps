package edu.uco.cmagueyal.streetsk8;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by DogTownDog on 11/6/2016.
 */

public class Background {
    private Bitmap image;
    private int x, y, dx;
    //constructor.
    public Background(Bitmap res, int moveSpeed, int w){
        image = res;
        dx = moveSpeed;
    }
    public void update(){
        setSpeed(dx);
        x+=dx;
        // 1200 is the width of my background image
        if(x<-1200){
            x=0;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y,null);
        if(x<0){
            canvas.drawBitmap(image, x+1200, y, null); //:.
        }
    }
    public void setSpeed( int speed){
        dx = speed;
    }
}
