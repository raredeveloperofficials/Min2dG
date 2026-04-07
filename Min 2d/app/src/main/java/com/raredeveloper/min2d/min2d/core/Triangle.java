package min2d.core;

public class Triangle {
    Vector2 p1,p2,p3;
	public Triangle(){
		p1= new Vector2();
		p2 = new Vector2();
		p3 = new Vector2();
	}
	public Triangle(Vector2 p1,Vector2 p2,Vector2 p3){
		this.p1 = new Vector2(p1);
		this.p2 = new Vector2(p2);
		this.p3 = new Vector2(p3);
	}
	public static Triangle singlePointTriangle(Vector2 p){
		return new Triangle(p,p,p);
	}
	public Triangle(Triangle t){
		p1 = new Vector2(t.p1);
		p2 = new Vector2(t.p2);
		p3 = new Vector2(t.p3);
	}
	public Triangle copy(){
		return new Triangle(this);
	}
	public static boolean isColliding(
		Triangle t1, Triangle t2,
		Vector2 pos1, Vector2 pos2,
		Vector2 scale1, Vector2 scale2
	){
		Triangle wt1 = transform(t1, pos1, scale1);
		Triangle wt2 = transform(t2, pos2, scale2);

		// check t1 points inside t2
		if (pointInTriangle(wt1.p1, wt2)) return true;
		if (pointInTriangle(wt1.p2, wt2)) return true;
		if (pointInTriangle(wt1.p3, wt2)) return true;

		// check t2 points inside t1
		if (pointInTriangle(wt2.p1, wt1)) return true;
		if (pointInTriangle(wt2.p2, wt1)) return true;
		if (pointInTriangle(wt2.p3, wt1)) return true;

		return false;
	}
	public static boolean pointInTriangle(Vector2 p, Triangle t){
		float d1 = sign(p, t.p1, t.p2);
		float d2 = sign(p, t.p2, t.p3);
		float d3 = sign(p, t.p3, t.p1);

		boolean hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
		boolean hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

		return !(hasNeg && hasPos);
	}

	private static float sign(Vector2 p1, Vector2 p2, Vector2 p3){
		return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
	}
	public static Triangle transform(Triangle t, Vector2 pos, Vector2 scale){
		return new Triangle(
			new Vector2(t.p1.x * scale.x + pos.x, t.p1.y * scale.y + pos.y),
			new Vector2(t.p2.x * scale.x + pos.x, t.p2.y * scale.y + pos.y),
			new Vector2(t.p3.x * scale.x + pos.x, t.p3.y * scale.y + pos.y)
		);
	}
}
