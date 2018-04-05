package edu.uco.cmagueyal.streetsk8;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by DogTownDog on 11/27/2016.
 */

public class Level2Thread extends Thread {
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder sHolder;
    private Level2Panel panel;
    //    private Level1Panel level1Panel;
    private boolean running;
    public static Canvas canvas;

    public Level2Thread(SurfaceHolder surfaceHolder, Level2Panel panel) {
        super();
        this.sHolder = surfaceHolder;
        this.panel = panel;
    }

    // public MainThread(SurfaceHolder surfaceHolder, Level1Panel level1Panel){
    //     super();
    //     this.surfaceHolder = surfaceHolder;
    //      this.level1Panel = level1Panel;
    // }
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.sHolder.lockCanvas();
                synchronized (sHolder) {
                    this.panel.update();
                    this.panel.draw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        sHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                this.sleep(waitTime);
            } catch (Exception e) {
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }
}