package edu.uco.cmagueyal.streetsk8;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by DogTownDog on 11/6/2016.
 */

public class MainThread extends Thread{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private PracticePanel practicePanel;
//    private Level1Panel level1Panel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, PracticePanel practicePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.practicePanel = practicePanel;
    }
   // public MainThread(SurfaceHolder surfaceHolder, Level1Panel level1Panel){
   //     super();
   //     this.surfaceHolder = surfaceHolder;
  //      this.level1Panel = level1Panel;
   // }
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.practicePanel.update();
                    this.practicePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }




            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                //////////////////////////         System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running=b;
    }
}
