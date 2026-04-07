package min2d.core;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	//threads surface holder
    private SurfaceHolder surfaceHolder;
	//game view instance
    private GameView gameView;
	//running instance
    private boolean running;
	//fps for game
    public static final int FPS = 60;

    public GameThread(SurfaceHolder holder, GameView view) {
        surfaceHolder = holder;
        gameView = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long targetTime = 1000 / FPS;

        while (running) {
            startTime = System.nanoTime();
            Canvas canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if (canvas != null)
                        gameView.onDraw(canvas);
                }
            } finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0)
                    sleep(waitTime);
            } catch (Exception e) { }
        }
    }
}
