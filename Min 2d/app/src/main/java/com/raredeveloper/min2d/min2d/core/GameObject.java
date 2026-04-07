package min2d.core;

import java.util.ArrayList;

public class GameObject {
    public String name;
	public ArrayList<String> id_tags;
	public Vector2 scale,position;
	public float rotation;
	protected Scene myScene;
	public ArrayList<Triangle> toucharea;
	public GameObject(){
		name = "";
		id_tags = new ArrayList<>();
		toucharea = new ArrayList<>();
		scale = new Vector2();
		position = new Vector2();
		rotation = 0;
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
