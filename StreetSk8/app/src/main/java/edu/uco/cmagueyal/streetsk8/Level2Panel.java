package edu.uco.cmagueyal.streetsk8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by DogTownDog on 11/6/2016.
 */

public class Level2Panel extends SurfaceView implements SurfaceHolder.Callback{
    public static final int TARGETSCORE = 3000; // 30,000
    public static final int WIDTH = 856; // used to be 856
    public static final int HEIGHT = 480;
    public static int moveSpeed = MainActivity.MOVESPEED;
    public static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private Level2Thread thread;
    private Background bg;
    private Player player;
    public static int jumpCounter;
    private final Context context = this.getContext();
    private long coneStartTime;
    private long bananaStartTime;
    private ArrayList<Bricks> bricks;
    private ArrayList<Banana> bananas;
    private int trick;
    public boolean levelCompleted;
    private boolean newGameCreated;
    public Paint myPaint;
    private long startReset;
    private long levelReset;
    private boolean reset;
    private boolean started;
    private int best = 0;
    private GestureDetector gDetector;

    public Level2Panel(Context context){
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        Level2Panel.MyGestureDetector myGestureDetector = new Level2Panel.MyGestureDetector();
        gDetector = new GestureDetector(this.getContext(), myGestureDetector);
        gDetector.setIsLongpressEnabled(false);


    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter < 1000){
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        ////// for collision box painter testing bro delete delete delete
        myPaint = new Paint();
        myPaint.setColor(Color.rgb(0, 0, 0));
        //////
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background),
                moveSpeed, MainActivity.BACKGROUNDWIDTH);
        player  = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.bigskater), 208,206, 43);
        bricks   = new ArrayList<>();
        bananas = new ArrayList<>();
        bananaStartTime = System.nanoTime();
        coneStartTime   = System.nanoTime();
        thread = new Level2Thread (getHolder(),  this);
        thread.setRunning(true);
        thread.start();
    }

    // Main part of Game loop.
    public void update(){
        if(player.getPlaying()) {
            // update player animation.
            player.update();
            // update background animation.
            bg.update();
            // checks if skater has finished falling animation to end the game.
            if (player.animate instanceof  FallAnimate && player.animate.getDone()) {
                player.setPlaying(false);
                player.setFall(false);
                System.out.println("End " + player.getPlaying());
            }
            // checks if player has reached required points.
            if(player.getScore() > TARGETSCORE){
                player.setPlaying(false);
                levelCompleted = true;
                levelReset = System.nanoTime();
            }
            // increment jumpcounter while crouched.
            if (player.animate instanceof CrouchingAnimate && (jumpCounter <= 25)) {
                jumpCounter++;
            }
            // Creating Bananas:
            long bananaElapsed = (System.nanoTime() - bananaStartTime) / 1000000;
            if(bananaElapsed > 15000 && MainActivity.difficulty != 0){
                bananas.add(new Banana(BitmapFactory.decodeResource(getResources(),
                        R.drawable.bigbanana), WIDTH + 10, (int) (HEIGHT * 0.85), 40, 40, 1));
                bananaStartTime = System.nanoTime();
            }
            //collision detection:
            for (int i = 0; i < bananas.size(); i++) {
                bananas.get(i).update();
                if (collision(bananas.get(i), player)) {
                    bananas.remove(i);
                    player.setFall(true);
                    player.setPlaying(false);
                    break;
                }
                // removing bananas when off screen
                if (bananas.get(i).getX() < -100) {
                    bananas.remove(i);
                    break;
                }
            }
            // Creating Cones:
            long coneElapsed = (System.nanoTime() - coneStartTime) / 1000000;
            if (coneElapsed > 3000) {
                bricks.add(new Bricks(BitmapFactory.decodeResource(getResources(),
                        R.drawable.bigbricks), WIDTH + 10, (int) (HEIGHT * 0.68), 100, 114, 1));
                coneStartTime = System.nanoTime();
            }
            // update and check collisions.
            for (int i = 0; i < bricks.size(); i++) {

                bricks.get(i).update();
                if (collision(bricks.get(i), player) && MainActivity.difficulty == 0) {
                    bricks.remove(i);
                    player.forceSetScore(-500);
                    break;
                }

                if (collision(bricks.get(i), player) && MainActivity.difficulty != 0) {
                    bricks.remove(i);
                    player.setFall(true);
                    break;
                }
                // removing cones when off screen
                if (bricks.get(i).getX() < -100) {
                    bricks.remove(i);
                    break;
                }

                if((bricks.get(i).getX() < player.getX() -15) ){
                    bricks.remove(i);
                    player.setPendingPoints(500);
                    break;
                }
            }
        }
        else if(player.getPlaying() == false && levelCompleted){
            long resetElapsed = (System.nanoTime()-levelReset)/1000000;
            if(resetElapsed > 4000) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",true);
                ((Activity)context).setResult(Activity.RESULT_OK,resultIntent);
                ((Activity)context).finish();
            }
        }
        else if(player.getPlaying() == false && !levelCompleted){
            if(!reset){
                newGameCreated = false;
                startReset = System.nanoTime();
                reset = true;
            }

            long resetElapsed = (System.nanoTime()-startReset)/1000000;

            if(resetElapsed > 2500 && !newGameCreated){
                newGame();
            }
            else if(resetElapsed < 2500 && started){
                player.update();
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gDetector.onTouchEvent(event);
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            if (player.getOnGround() && player.getTrick() == 0) {
                player.setCrouch(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP && player.animate instanceof PowerslideAnimate) {
            // change back to rolling here
            player.resetAnimate();
            return true;
        }
        return super.onTouchEvent(event);
    }
    public boolean collision(GameObject a, GameObject b){
        if(Rect.intersects(a.getRectangle(), b.getRectangle())){
            return true;
        }
        return false;
    }
    public void newGame(){
        trick = 0;
        bricks.clear();
        bananas.clear();
        player.resetScore();
        player.setY(HEIGHT/2);
//        if(player.getScore() > best){
//            best = player.getScore();
//        }
        newGameCreated = true;
    }
    // Inner Class MyGestureDetector detects swipes and taps and scrolls.
    class MyGestureDetector implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            if (!player.getPlaying() && newGameCreated && reset) {
                player.setPlaying(true);
                player.resetAnimate();
            }
            if (player.getPlaying()) {
                if (!started) {
                    started = true;
                }
                reset = false;
            }
            return false;
        }// end onDown.
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //("onSingleTapUp   " + player.getY());
            player.setUp(false);
            player.setCrouch(false);
            if(player.getOnGround()) {
                player.setOllieHeight(jumpCounter);
                player.setOllie(true);
            }
            jumpCounter = 0;
            return false;
        }// end Tap Up.
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float velocityY) {
            if ((e2.getY() - e1.getY()) > 0  && Math.abs(velocityY)>0 ) {
                if (player.getOnGround()) {
                    player.setTrick(5);
                    player.setCrouch(false);
                }
            }
            return false;
        }// end onScroll
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(player.animate instanceof OllieUpAnimate) {
                try {
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        player.setTrick(4);
                        trick = 4;
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        player.setTrick(3);
                        trick = 3;
                    }
                    if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        player.setTrick(2);
                        trick = 2;
                    } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        player.setTrick(1);
                        trick = 1;
                    }
                } catch (Exception e) {}
            }
            return false;
        }
        @Override
        public void onShowPress(MotionEvent e) {}
        // LongPress is deactivated in the gesture detector.
        @Override
        public void onLongPress(MotionEvent e) {}
    } // end of GestureListener declaration.
    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if(canvas!=null){
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            //below was to draw collis boxes
            //    canvas.drawRect(new Rect(10, 10, 200,300 - 15*jumpCounter), myPaint);
            //        if(!bananas.isEmpty())
            //            canvas.drawRect(bananas.get(0).getRectangle(), myPaint );
            player.draw(canvas);
            for(Bricks c: bricks){
                c.draw(canvas);
            }
            for(Banana b: bananas){
                b.draw(canvas);
            }
            drawText(canvas);
            canvas.restoreToCount(savedState);
        }
    }
    public void drawText(Canvas canvas){
        Paint paint = new Paint();
        String trickName = "";

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        if(trick==1){
            trickName = "HEELFLIP";
        }
        else if(trick==2){
            trickName = "KICKFLIP";
        }
        else if(trick==3){
            trickName = "360 SHOVE IT";
        }
        else if(trick==4){
            trickName = "OLLIE IMPOSSIBLE";
        }
        canvas.drawText("Score: " + player.getScore() + "  Trick: "+ trickName, 10, HEIGHT -10 , paint);
        if(!player.getPlaying() && newGameCreated && reset){
            Paint paint1 = new Paint();
            paint1.setTextSize(40);
            paint1.setColor(Color.WHITE);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2 - 50, HEIGHT - 10, paint1);

        }
        if(player.getPlaying() == false && levelCompleted){
            Paint paint1 = new Paint();
            paint1.setTextSize(50);
            paint1.setColor(Color.BLACK);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Level  2 Completed", WIDTH/2 - 55, HEIGHT - 250, paint1);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}
}

