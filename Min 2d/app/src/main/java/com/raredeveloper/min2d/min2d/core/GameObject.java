package min2d.core;

import java.util.ArrayList;

public class GameObject {
    public String name;
	public ArrayList<String> id_tags;
	public Vector2 scale,position;
	public float rotation;
	protected Scene myScene;
    public boolean enabled = true,visible = true;
	public ArrayList<Triangle> toucharea;
    private ArrayList<Component> components;
    private ArrayList<GameObject> children;
    private GameObject parent = null;
	public GameObject(){
		name = "";
		id_tags = new ArrayList<>();
		toucharea = new ArrayList<>();
		scale = new Vector2();
		position = new Vector2();
        components = new ArrayList<>();
		rotation = 0;
	}
    public void setParent(GameObject parent){
        parent.addChild(this);
    }
    public GameObject getChildAt(int index){
        return children.get(index);
    }
    public GameObject getChildWithName(String name){
        for(GameObject go:children){
            if(go.name.equals(name))return go;
        }
        return null;
    }
    public void addChild(GameObject o){
        o.parent = this;
        children.add(o);
    }
    public GameObject getParent(){return this;}
    public void addComponent(Component c){
        components.add(c);
        c.myObject(this);
        c.start();
    }
    public <C extends Component>Component getComponent(Class<?> comp){
        for(Component c : components){
            if(c.getClass().isInstance(comp))return c;
        }
        return null;
    }
    public Component[] getAllComponents(){
        return components.toArray(new Component[0]);
    }
    public Scene getScene(){
        return myScene;
    }
    public void myScene(Scene myScene){
        this.myScene = myScene;
    }
	public GameObject copy(){
		GameObject copy = new GameObject();
		copy.name = new String(name);
		copy.id_tags = new ArrayList<String>(id_tags);
		copy.toucharea = new ArrayList<>();
		for (Triangle t: toucharea) {
			copy.toucharea.add(new Triangle(t));
		}
		copy.scale = new Vector2(scale);
		copy.position = new Vector2(position);
		copy.rotation = rotation;
		return copy;
	}
}
