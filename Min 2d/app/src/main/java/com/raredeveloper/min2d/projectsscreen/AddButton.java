package com.raredeveloper.min2d.projectsscreen;
import min2d.core.Component;
import android.view.MotionEvent;
import min2d.core.Vector2;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

public class AddButton extends Component {
    private AlertDialog dialog;
    @Override
    public void start() {
        super.start();
        myObject.getScene().getView().runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    AlertDialog.Builder dia = new AlertDialog.Builder(myObject.getScene().getView().getContext());
                    dia.setTitle("Project Name:");
                    final EditText text = new EditText(myObject.getScene().getView().getContext());
                    text.setHint("Project Name");
                    dia.setView(text);
                    dia.setPositiveButton("create", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int p) {
                            }
                        });
                    dia.setNegativeButton("cancel", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int p) {
                            }
                        });

                    dialog = dia.create();
                }
            });
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
    @Override
    public void input(MotionEvent event, Vector2 touchPosition) {
        super.input(event, touchPosition);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            dialog.show();
        }
    }
    
    @Override
    public Component copy() {
        return new AddButton();
    }
    
}
