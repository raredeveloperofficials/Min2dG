package min2d.core;
import android.view.MotionEvent;

public abstract class Component {
    protected GameObject myObject;
	public boolean enabled = true;
    public Vector2 offset;
    public Component(){
        offset = new Vector2();
    }
	public  void start(){}
	public  void update(float delta){}
	public  void destroy(){}
   
    public GameObject getObject(){
        return myObject;
    }
    public void myObject(GameObject myObject){
        this.myObject = myObject;
    }
	public  void input(MotionEvent event,Vector2 touchPosition){}
	public abstract Component copy();
}
