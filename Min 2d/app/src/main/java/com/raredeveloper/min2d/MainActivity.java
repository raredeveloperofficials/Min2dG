package com.raredeveloper.min2d;
 
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;

public class MainActivity extends Activity {
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.makeFullScreen(this);
		new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					Intent i = new Intent(MainActivity.this,ProjectsActivity.class);
					startActivity(i);
					finish();
				}
			}, 2000);
    }
	public static void makeFullScreen(Activity a){
		if(a.getActionBar()!=null)a.getActionBar().hide();
		a.getWindow().getDecorView().setSystemUiVisibility(
			View.SYSTEM_UI_FLAG_FULLSCREEN
			| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}
	
}
