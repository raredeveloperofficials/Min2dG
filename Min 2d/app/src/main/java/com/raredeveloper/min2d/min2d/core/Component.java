package min2d.core;
import android.view.MotionEvent;

public abstract class Component {
    protected GameObject myObject;
	public boolean enable = true;
	public  void start(){}
	public  void update(float delta){}
	public  void destroy(){}
    public Object getObject(){
        return myObject;
    }
    public void myObject(GameObject myObject){
        this.myObject = myObject;
    }
	public  void input(MotionEvent event){}
	public abstract Component copy();
}
