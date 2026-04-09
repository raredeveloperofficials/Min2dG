package com.raredeveloper.min2d;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import min2d.core.GameView;
import min2d.core.GameObject;
import min2d.core.Components.Renderables.ImageRenderer;
import min2d.core.Component;
import min2d.core.Components.Renderer;

public class ProjectsActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.makeFullScreen(this);
        GameView gv = new GameView(this);
        GameObject go = GameObject.create("myo",new ImageRenderer(getResources(),R.drawable.ic_launcher));
        gv.getCurrentScene().addObject(go);
        setContentView(gv);
    }
    
}
