package min2d.core;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.Collection;

public class Scene {
    public int backgroundColor = Color.WHITE,rotation = 0;
	protected GameView myView;
	private ArrayList<GameObject> objects;
    public float zoom =1;
    public Vector2 cameraPos;
    public GameView getView(){
        return myView;
    }
    public void myView(GameView myView){
        this.myView = myView;
    }
    public void addObject(GameObject o){
        objects.add(o);
        o.myScene(this);
    }
    public GameObject getObjectAt(int index){
        return objects.get(index);
    }
    public GameObject getWithName(String name){
        for(GameObject go:objects){
            if(go.name.equals(name))return go;
        }
        return null;
    }
	public Scene(){
		objects = new ArrayList<>();
        cameraPos = new Vector2();
	}
    public GameObject getObjectWithTags(ArrayList<String> tags){
        for(GameObject o:objects){
            if(o.id_tags.containsAll(tags))return o;
        }
        return null;
    }
    public GameObject[] getAllObjects(){
        return objects.toArray(new GameObject[0]);
    }
}
