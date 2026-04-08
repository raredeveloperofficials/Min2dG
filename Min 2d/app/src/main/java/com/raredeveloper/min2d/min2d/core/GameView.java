package min2d.core;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private GameThread gameThread;
	private Scene currentScene;
	public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }
	public void setScene(Scene s){
        s.myView(this);
		currentScene = s;
	}
	public Scene getScene(){
		return currentScene;
	}
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) { }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(currentScene.backgroundColor);
        
        int saved1 =canvas.save();

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        canvas.translate(centerX, centerY);

        canvas.scale(currentScene.zoom, -currentScene.zoom);
        canvas.translate(-currentScene.cameraPos.x, -currentScene.cameraPos.y);
        
        //loop logic
        canvas.restoreToCount(saved1);
    }
}

