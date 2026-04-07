package com.raredeveloper.min2d;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ProjectsActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.makeFullScreen(this);
        setContentView(new LinearLayout(this));
    }
    
}
