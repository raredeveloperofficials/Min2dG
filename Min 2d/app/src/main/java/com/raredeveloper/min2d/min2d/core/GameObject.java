package min2d.core;

import java.util.ArrayList;

public class GameObject {

    public String name;
    public String Layer = "object";

    public ArrayList<String> id_tags;
    public Vector2 scale, position;
    public float rotation;

    protected Scene myScene;

    public boolean enabled = true, visible = true;

    public ArrayList<Triangle> toucharea;

    private ArrayList<Component> components;
    private ArrayList<GameObject> children;
    private GameObject parent = null;

    public GameObject() {
        name = "";
        id_tags = new ArrayList<>();
        toucharea = new ArrayList<>();
        toucharea.add(new Triangle(new Vector2(1,1),new Vector2(-1,-1),new Vector2(1,-1)));
        toucharea.add(new Triangle(new Vector2(1,1),new Vector2(-1,1),new Vector2(-1,-1)));
        scale = new Vector2(100, 100);
        position = new Vector2();
        components = new ArrayList<>();
        children = new ArrayList<>();
        rotation = 0;
    }

    public void setParent(GameObject parent) {
        if (parent != null) parent.addChild(this);
    }

    public GameObject getChildAt(int index) {
        return children.get(index);
    }

    public GameObject getChildWithName(String name) {
        for (GameObject go : children) {
            if (go != null && go.name != null && go.name.equals(name)) return go;
        }
        return null;
    }

    public void addChild(GameObject o) {
        if (o == null) return;
        o.parent = this;
        children.add(o);
    }

    public GameObject getParent() {
        return parent;
    }

    public void addComponent(Component c) {
        if (c == null) return;
        components.add(c);
        c.myObject(this);
        c.start();
    }

    public boolean removeComponent(Component c) {
        if (c == null) return false;
        c.destroy();
        return components.remove(c);
    }

    public <C extends Component> C getComponent(Class<C> comp) {
        for (Component c : components) {
            if (comp.isInstance(c)) return comp.cast(c);
        }
        return null;
    }

    public Component[] getAllComponents() {
        return components.toArray(new Component[0]);
    }

    public Scene getScene() {
        return myScene;
    }

    public void myScene(Scene myScene) {
        this.myScene = myScene;
    }

    public GameObject copy() {
        GameObject copy = new GameObject();
        copy.name = name != null ? new String(name) : null;
        copy.id_tags = new ArrayList<>(id_tags);

        copy.toucharea = new ArrayList<>();
        for (Triangle t : toucharea) {
            copy.toucharea.add(new Triangle(t));
        }

        for (Component t : components) {
            Component c = t.copy();
            copy.addComponent(c);
        }

        copy.scale = new Vector2(scale);
        copy.position = new Vector2(position);
        copy.rotation = rotation;

        return copy;
    }

    public static GameObject create(String name, Component... comps) {
        GameObject o = new GameObject();
        o.name = name;
        for (Component c : comps) {
            o.addComponent(c);
        }
        return o;
    }
}
