package com.raredeveloper.min2d.projectsscreen;
import android.graphics.Color;
import com.raredeveloper.min2d.R;
import com.raredeveloper.min2d.projectsscreen.AddButton;
import min2d.core.Component;
import min2d.core.Components.Renderables.BoundRenderer;
import min2d.core.Components.Renderables.ImageRenderer;
import min2d.core.Components.Renderables.ShapeRenderer;
import min2d.core.GameObject;
import min2d.core.GameView;
import android.app.Activity;
import com.raredeveloper.min2d.ProjectsActivity;

public class Initializer extends Component {
    public Initializer(){
        
    }
    @Override
    public void start() {
        super.start();
        GameView gv = myObject.getScene().getView();
        gv.getCurrentScene().backgroundColor= Color.rgb(65,60,62);
        ShapeRenderer topBarBackground = new ShapeRenderer();
        topBarBackground.color = Color.rgb(251,127,11);
        final GameObject backtb = GameObject.create(
            "backtb",
            topBarBackground

        );

        final GameObject topbar = GameObject.create("topbar",new BoundRenderer());

        final GameObject addBtn = GameObject.create("addBtn",new ImageRenderer(((Activity)gv.getContext()).getResources(),R.drawable.add_icon),new AddButton());
        
        gv.post(new Runnable(){

                @Override
                public void run() {
                    int swidth = myObject.getScene().getView().getWidth();
                    int sheight = myObject.getScene().getView().getHeight();
                    topbar.scale.set(swidth,ProjectsActivity.dpToPx(50));
                    backtb.scale.set(topbar.scale);
                    topbar.position.y =(sheight/2)-(topbar.scale.y/2);
                    addBtn.scale.set(ProjectsActivity.dpToPx(40),ProjectsActivity.dpToPx(40));
                    addBtn.position.x = ((-swidth/2)+(addBtn.scale.x/2));
                    
                }
            });
        
        
        topbar.addChild(backtb);
        topbar.addChild(addBtn);

        gv.getCurrentScene().addObject(topbar);
        
        //exit out of current scope
        myObject.getScene().removeObject(myObject);
    }
    
    @Override
    public Component copy() {
        return null;
    }
    
}
