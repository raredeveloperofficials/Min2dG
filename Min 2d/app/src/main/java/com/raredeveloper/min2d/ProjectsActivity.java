package com.raredeveloper.min2d;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import min2d.core.Component;
import min2d.core.Components.Renderables.ImageRenderer;
import min2d.core.GameObject;
import min2d.core.GameView;
import min2d.core.Triangle;
import min2d.core.Vector2;
import android.util.Log;
import android.widget.Toast;
import min2d.core.Components.Renderables.TextRenderer;

public class ProjectsActivity extends Activity {

    GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.makeFullScreen(this);

        gv = new GameView(this);

        GameObject go = GameObject.create(
            "myo",
            new ImageRenderer(getResources(), R.drawable.ic_launcher),
            new TextRenderer(),
            new PlayerScript()
        );
        (go.getComponent(TextRenderer.class)).setText("Min 2d");
        go.scale.set(dpToPx(100), dpToPx(100));

        gv.getCurrentScene().addObject(go);

        setContentView(gv);
    }

    public static float dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return dp * density;
    }

    public class PlayerScript extends Component {
        private Vector2 prevpos;
        @Override
        public void start() {
            super.start();
        }

        @Override
        public void update(float delta) {
            super.update(delta);
            
        }

        @Override
        public void input(MotionEvent event, Vector2 touchPosition) {
            super.input(event, touchPosition);
            if(prevpos!=null){
                Vector2 drag = Vector2.sub(touchPosition,prevpos);
                myObject.position.set(myObject.position.x+drag.x,myObject.position.y+drag.y);
            }
            
            if(event.getAction() == MotionEvent.ACTION_UP){
                prevpos = null;
            }else{
                prevpos = touchPosition;
            }
        }
        
        

        @Override
        public PlayerScript copy() {
            return new PlayerScript();
        }
    }
}
