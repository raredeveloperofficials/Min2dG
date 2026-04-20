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
    public static Triangle scale(Triangle t, float scale) {
        // Find centroid
        float cx = (t.p1.x + t.p2.x + t.p3.x) / 3f;
        float cy = (t.p1.y + t.p2.y + t.p3.y) / 3f;

        // Scale each point relative to centroid
        Vector2 np1 = scalePoint(t.p1, cx, cy, scale);
        Vector2 np2 = scalePoint(t.p2, cx, cy, scale);
        Vector2 np3 = scalePoint(t.p3, cx, cy, scale);

        return new Triangle(np1, np2, np3);
    }

    private static Vector2 scalePoint(Vector2 p, float cx, float cy, float scale) {
        float x = cx + (p.x - cx) * scale;
        float y = cy + (p.y - cy) * scale;
        return new Vector2(x, y);
    }
	private static boolean lineIntersect(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4) {

        float d = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);

        if (d == 0) return false; // parallel

        float ua = ((p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x)) / d;
        float ub = ((p2.x - p1.x) * (p1.y - p3.y) - (p2.y - p1.y) * (p1.x - p3.x)) / d;

        return ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1;
    }
    public static boolean isColliding(
        Triangle t1, Triangle t2,
        Vector2 pos1, Vector2 pos2,
        Vector2 scale1, Vector2 scale2
    ) {
        Triangle wt1 = transform(t1, pos1, scale1);
        Triangle wt2 = transform(t2, pos2, scale2);

        // 1. Vertex inside checks
        if (pointInTriangle(wt1.p1, wt2)) return true;
        if (pointInTriangle(wt1.p2, wt2)) return true;
        if (pointInTriangle(wt1.p3, wt2)) return true;

        if (pointInTriangle(wt2.p1, wt1)) return true;
        if (pointInTriangle(wt2.p2, wt1)) return true;
        if (pointInTriangle(wt2.p3, wt1)) return true;

        // 2. Edge intersection checks (NEW - critical)
        Vector2[] a = {wt1.p1, wt1.p2, wt1.p3};
        Vector2[] b = {wt2.p1, wt2.p2, wt2.p3};

        for (int i = 0; i < 3; i++) {
            Vector2 a1 = a[i];
            Vector2 a2 = a[(i + 1) % 3];

            for (int j = 0; j < 3; j++) {
                Vector2 b1 = b[j];
                Vector2 b2 = b[(j + 1) % 3];

                if (lineIntersect(a1, a2, b1, b2)) return true;
            }
        }

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
    public static boolean pointInTriangle(Triangle t, Vector2 scale, Vector2 position, Vector2 camerapos, float zoom, Vector2 point) {

        Triangle t1 = t.copy();

        // Apply zoom
        t1.p1.mul(zoom);
        t1.p2.mul(zoom);
        t1.p3.mul(zoom);

        // Apply position (object position - camera)
        Vector2 offset = Vector2.sub(position, camerapos);

        t1.p1.add(offset);
        t1.p2.add(offset);
        t1.p3.add(offset);

        // Now check using sign method
        return sign(point, t1.p1, t1.p2) < 0.0f &&
            sign(point, t1.p2, t1.p3) < 0.0f &&
            sign(point, t1.p3, t1.p1) < 0.0f
            ||
            sign(point, t1.p1, t1.p2) > 0.0f &&
            sign(point, t1.p2, t1.p3) > 0.0f &&
            sign(point, t1.p3, t1.p1) > 0.0f;
    }
    public static Triangle transform(Triangle t, Vector2 pos, Vector2 scale) {

        // centroid
        float cx = (t.p1.x + t.p2.x + t.p3.x) / 3f;
        float cy = (t.p1.y + t.p2.y + t.p3.y) / 3f;

        return new Triangle(
            transformPoint(t.p1, cx, cy, pos, scale),
            transformPoint(t.p2, cx, cy, pos, scale),
            transformPoint(t.p3, cx, cy, pos, scale)
        );
    }

    private static Vector2 transformPoint(Vector2 p, float cx, float cy, Vector2 pos, Vector2 scale) {
        float x = cx + (p.x - cx) * scale.x + pos.x;
        float y = cy + (p.y - cy) * scale.y + pos.y;
        return new Vector2(x, y);
    }
    public static boolean pointInTriangle(Vector2 p, Triangle t,Vector2 o){
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
	
}
