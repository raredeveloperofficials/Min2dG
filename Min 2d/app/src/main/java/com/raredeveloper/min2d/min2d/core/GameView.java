package min2d.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.HashMap;
import min2d.core.Components.Renderer;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private Scene currentScene;
    private Paint p;
    private HashMap<String, ArrayList<GameObject>> layered = new HashMap<String, ArrayList<GameObject>>() {{
            put("object", new ArrayList<>());
            put("ui", new ArrayList<>());
        }};

    private static final ArrayList<String> layers = new ArrayList<String>() {{
            add("object");
            add("ui");
        }};

    public float delta = 0.12f;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Vector2 touchWorld = Vector2.positionInWorld(
            new Vector2(event.getX(), event.getY()),
            new Vector2(getWidth(), getHeight()),
            getCurrentScene().cameraPos,
            getCurrentScene().zoom
        );

        for (GameObject obj : getCurrentScene().getAllObjects()) {

            if (obj == null || obj.toucharea == null||!obj.enabled) continue;

            boolean touched = false;

            for (Triangle t : obj.toucharea) {

                Triangle wt = Triangle.transform(t, obj.position, obj.scale);

                if (Triangle.pointInTriangle(touchWorld, wt)) {
                    touched = true;
                    break;
                }
            }

            if (touched) {
                for (Component c : obj.getAllComponents()) {
                    c.input(event, touchWorld);
                }
            }
        }

        return true;
    }
    public GameView(Context context) {
        super(context);
        p = new Paint();
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setScene(new Scene());
        setFocusable(true);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setScene(Scene s) {
        s.myView(this);
        currentScene = s;
    }

    public Scene getScene() {
        return currentScene;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (gameThread == null || !gameThread.isAlive()) {
            gameThread = new GameThread(getHolder(), this);
        }
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    public void render(Canvas canvas) {
        if (currentScene == null) return;

        long nano = System.nanoTime();
        Scene nowScene = currentScene;

        canvas.drawColor(nowScene.backgroundColor);

        int saved1 = canvas.save();

        for (String key : layered.keySet()) {
            layered.get(key).clear();
        }

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        canvas.translate(centerX, centerY);

        canvas.scale(nowScene.zoom, -nowScene.zoom);
        canvas.translate(-nowScene.cameraPos.x, -nowScene.cameraPos.y);

        for (GameObject g : nowScene.getAllObjects()) {
            if (g != null && g.Layer != null) {
                if (!layered.containsKey(g.Layer)) {
                    layered.put(g.Layer, new ArrayList<>());
                }
                layered.get(g.Layer).add(g);
            }
        }

        for (String layer : layers) {
            emulateUpdate(layer, canvas);
        }

        canvas.restoreToCount(saved1);

        delta = (System.nanoTime() - nano) / 1_000_000f;
    }

    private void emulateUpdate(String layer, Canvas canvas) {
        ArrayList<GameObject> layerobj = layered.get(layer);
        if (layerobj != null) {
            for (GameObject obj : layerobj) {
                if (obj == null) continue;

                for (Component comp : obj.getAllComponents()) {
                    if (comp == null) continue;

                    if (obj.enabled) comp.update(delta);
                    if (obj.visible && obj.enabled && comp instanceof Renderer) {
                        ((Renderer) comp).render(canvas,p);
                    }
                }
            }
        }
    }
}
