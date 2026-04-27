package com.raredeveloper.min2d;



import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import min2d.core.GameObject;
import min2d.core.GameView;

public class ProjectsActivity extends Activity {
    GameView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.makeFullScreen(this);
        gv = new GameView(this);
        GameObject initializer = GameObject.create("init",new com.raredeveloper.min2d.projectsscreen.Initializer());
        gv.getCurrentScene().addObject(initializer);
        setContentView(gv);
    }
    public static float dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return dp * density;
    }  
}
