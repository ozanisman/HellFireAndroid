package com.wizardstudios.hellfire;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static int MAX_FPS = 25;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private HellFireGame hellFireGame;
    private boolean running;
    private HellFireMain main;
    public static Canvas canvas;



    public MainThread(SurfaceHolder surfaceHolder, HellFireGame hellFireGame, HellFireMain main) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.hellFireGame = hellFireGame;
        this.main = main;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;
            Paint paint = new Paint();
            try {
                canvas = this.surfaceHolder.lockCanvas(null);
                synchronized(surfaceHolder) {
                    this.hellFireGame.tick();
                    this.hellFireGame.draw(canvas);
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if(waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MAX_FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    public int getFPS() {
        return (int)MAX_FPS;
    }

}
