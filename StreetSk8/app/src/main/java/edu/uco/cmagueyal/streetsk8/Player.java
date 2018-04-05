package edu.uco.cmagueyal.streetsk8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by DogTownDog on 11/6/2016.
 */

public class Player  extends GameObject {
    public Animate animate = new RollingAnimate();
    private int score;
    private int trick;
    private int multiplier;

    private int pendingPoints;
    private boolean up;
    private boolean fall;
    private boolean ollie;
    private boolean crouching;
    public int ollieMax;
    private boolean onGround;
    private boolean playing;
    private long startTime;
    private Bitmap playerSprite;
    private Bitmap[] animateFrames;

    public Player(Bitmap res, int w, int h, int numFrames) {
        x = 100;
        y = PracticePanel.HEIGHT / 2;
        dy = 0;
        score = 0;
        multiplier = 0;
        height = h;
        width = w;
        ollie = false;
        Bitmap[] imagePlayer = new Bitmap[numFrames];
        playerSprite = res;
        for (int i = 0; i < imagePlayer.length; i++){
            imagePlayer[i] = Bitmap.createBitmap(playerSprite, i*width, 0, width, height);
        }
        animate.setFrames(imagePlayer);
        animate.setDelay(100);
        animateFrames = imagePlayer;
    }
    public void setUp(boolean b){up = b;}
    public boolean getUp(){return up;}
    public void setFall(boolean b){
        fall = b;
    }
    public void update(){
        animate.update();

        updateAnimate();
        if(!(animate instanceof PowerslideAnimate)) {
            if (ollie) {
                dy = (int) -20.1;
                if (y <= ollieMax) {
                    ollie = false;
                    ollieMax = 0;
                }
            }
            if (!ollie) {
                dy = (int) 13.1;
            }
            if (y > PracticePanel.HEIGHT * 0.53f && !ollie) {
                dy = 0;
                onGround = true;
           //
                //
                //
                //
                //  trick = 0;
            } else {
                onGround = false;
            }
            y += dy;
        }
    }
    public void updateAnimate(){
        if(crouching && animate instanceof RollingAnimate){
            animate = new CrouchingAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
        }
        else if(ollie && animate instanceof CrouchingAnimate) {
            animate = new OllieUpAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
        }
        else if( animate instanceof OllieUpAnimate && trick == 4 ){
            animate = new ImpossibleAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
            this.setPendingPoints(1000);
        }
        else if( animate instanceof OllieUpAnimate && trick == 2 ){
            animate = new KickflipAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
            this.setPendingPoints(1000);
        }else if( animate instanceof OllieUpAnimate && trick == 1 ){
            animate = new HeelflipAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
            this.setPendingPoints(1000);
        }else if( animate instanceof OllieUpAnimate && trick == 3 ){
            animate = new ShoveIt360Animate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
            this.setPendingPoints(1000);
        }
        else if(animate.getDone() && !(animate instanceof FallAnimate)){
            animate = new OllieDownAnimate();
            trick = 0;
            animate.setFrames(animateFrames);
            animate.setDelay(100);
            // animate.setDone(false);
        }

        else if(onGround && !animate.getDone() && (trick == 1 || trick ==2 ||
                                      trick == 3 || trick ==4) && MainActivity.difficulty ==2){
            System.out.println(" Helloooo oOOijwoiejf");
            fall = true ;
            playing = false;
        }
        else if(!ollie && !onGround   && trick == 0 ){
            animate = new OllieDownAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
            // animate.setDone(false);
        }
        else if(onGround && (animate instanceof OllieDownAnimate)){
            animate = new RollingAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
            trick =0;
            setScore();
        } else if (trick == 5) {
            animate = new PowerslideAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);

        }
        else if (fall) {
            animate = new FallAnimate();
            animate.setFrames(animateFrames);
            animate.setDelay(100);
            fall = false;
        }

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(animate.getImage(),x,y,null);
    }
    public void setOllie(boolean b){ollie = b;}
    public void setOllieHeight(int count){
        ollieMax =  y - 120 - 8 * count;
    }
    public void setCrouch(boolean b){crouching = b;}
    public boolean getCrouch(){return crouching;}
    public boolean getOllie(){return ollie;}
    public void setOnGround(boolean b){onGround = b;}
    public boolean getOnGround(){return onGround;}
    public int getScore(){return score;}
    public int getTrick(){return trick;}
    public void setTrick(int t){ trick = t;}
    public void forceSetScore(int p){
        score += p;
    }
    public void setScore(){
        score += pendingPoints * multiplier ;
        multiplier = 0;
        pendingPoints = 0;
    }
    public void setPendingPoints(int pp){
        multiplier++;
        pendingPoints += pp;
    }
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetScore(){score = 0;}
    public void resetAnimate( ){
        animate = new RollingAnimate();
        animate.setFrames(animateFrames);
        animate.setDelay(100);
        trick =0;
    }
    @Override
    public void setY(int y){this.y = y;}
    @Override
    public Rect getRectangle (){
        return new Rect(x+80, y+50, x+(int)(width*0.55 ) , y+(int) (height*0.8f));
    }
}
