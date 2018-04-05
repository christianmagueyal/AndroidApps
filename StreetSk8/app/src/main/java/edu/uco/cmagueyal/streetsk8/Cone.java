package edu.uco.cmagueyal.streetsk8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by DogTownDog on 11/6/2016.
 */

public class Cone  extends GameObject {
    private int speed;
    private Animate animate = new StillAnimate();
    private Bitmap spritesheet;
    //constructor:
    public Cone(Bitmap res, int x, int y, int w, int h, int numFrames){
        super.x=x;
        super.y=y;
        width=w;
        height=h;
        speed = -MainActivity.MOVESPEED;
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        for(int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(spritesheet, 0, i * height, width, height);
        }
        animate.setFrames(image);
        animate.setDelay(100);
    }
    public void update(){
        setSpeed(-MainActivity.MOVESPEED);
        x-=speed;
    }
    public void draw(Canvas canvas){
        try{
            canvas.drawBitmap(animate.getImage(),x,y,null);

        }catch(Exception e){}
    }
    @Override
    public int getWidth(){
        // offset slightly for more realistic collision detection.
        return width - 10;
    }
    public void setSpeed(int s){
        speed = s;
    }
    @Override
    public Rect getRectangle(){return new Rect(x+12, y, x+(int)(width * 0.65f) , y+height);}
}
