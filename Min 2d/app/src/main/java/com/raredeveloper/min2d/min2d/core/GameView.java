package min2d.core;

import android.app.Activity;
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
    public Vector2 dragVal = new Vector2();
    private Vector2 prevtouchpos=new Vector2();
    private HashMap<String, ArrayList<GameObject>> layered = new HashMap<String, ArrayList<GameObject>>() {{
            put("object", new ArrayList<>());
            put("ui", new ArrayList<>());
        }};

    private static final ArrayList<String> layers = new ArrayList<String>() {{
            add("object");
            add("ui");
        }};
    public static final String LAYER_OBJECT = "object";
    public static final String LAYER_UI = "ui";
    public float delta = 0.12f;
    
    public void addLayer(String layer){
        if(layer.equals("object")||layer.equals("ui"))return;
        int index = GameView.layers.size()-1;
        GameView.layers.add(index,layer);
        layered.put(layer,new ArrayList<>());
    }
    public void removeLayer(String layer){
        if(layer.equals("object")||layer.equals("ui"))return;
        GameView.layers.remove(layer);
        for(GameObject o: layered.get(layer)){
            o.Layer = "object";
        }
        layered.remove(layer);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Vector2 touchWorld = Vector2.positionInWorld(
            new Vector2(event.getX(), event.getY()),
            new Vector2(getWidth(), getHeight()),
            getCurrentScene().cameraPos,
            getCurrentScene().zoom
        );
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevtouchpos.set(touchWorld.x,touchWorld.y);
                break;
            case MotionEvent.ACTION_MOVE:
                dragVal.set(touchWorld.x-prevtouchpos.x,touchWorld.y-prevtouchpos.y);
                prevtouchpos.set(touchWorld.x,touchWorld.y);
                break;
            case MotionEvent.ACTION_UP:
                dragVal.set(0,0);
                break;
        }
        for (GameObject obj : getCurrentScene().getAllObjects()) {
            if (obj == null || obj.toucharea == null||!obj.enabled) continue;
            DetectTouchForObj(obj,event,touchWorld);
            
        }

        return true;
    }
    
    private void DetectTouchForObj(GameObject obj,MotionEvent event,Vector2 touchWorld){
        

        boolean touched = false;
        for (Triangle t : obj.toucharea) {

            Triangle wt = Triangle.transform(t, obj.global_position, obj.scale);

            if (Triangle.pointInTriangle(touchWorld, wt)) {
                touched = true;
                if(obj.childCount()!=0&&obj.blockInputOutOfBound)
                    for(int i = 0;i<obj.childCount();i++){
                        obj.getChildAt(i).myScene(obj.getScene());
                        DetectTouchForObj(obj.getChildAt(i),event,touchWorld);
                    }
                break;
            }
        }

        if (touched) {
            for (Component c : obj.getAllComponents()) {
                c.input(event, touchWorld);
            }
        }
        
        if(obj.childCount()!=0&&!obj.blockInputOutOfBound)
            for(int i = 0;i<obj.childCount();i++){
                obj.getChildAt(i).myScene(obj.getScene());
                DetectTouchForObj(obj.getChildAt(i),event,touchWorld);
            }
    }
    public void runOnUiThread(Runnable run){
        ((Activity)getContext()).runOnUiThread(run);
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
                
                updateObj(obj,canvas);
            }
        }
    }
    private void updateObj(GameObject obj,Canvas canvas){
        for (Component comp : obj.getAllComponents()) {
            if (comp == null) continue;

            if (obj.enabled) comp.update(delta);
            if (obj.visible && obj.enabled && comp instanceof Renderer) {
                obj.getGlobalPosition();
                ((Renderer) comp).render(canvas,p);
            }
        }
        if(obj.childCount()!=0)
        for(int i = 0; i < obj.childCount();i++){
            GameObject child = obj.getChildAt(i);
            child.myScene(obj.getScene());
            if(child==null)continue;
            
            
            updateObj(child,canvas);
            
        }
        for (Component comp : obj.getAllComponents()) {
            if (comp == null) continue;
            if(comp instanceof Renderer)((Renderer)comp).afterChildUpdateFinished(canvas);
        }
    }
}
