package min2d.core;

public final class Vector2 {

    public float x;
    public float y;

    // --- Constructors ---
    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    // --- Static Constants ---
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 ONE = new Vector2(1, 1);
    public static final Vector2 UP = new Vector2(0, 1);
    public static final Vector2 DOWN = new Vector2(0, -1);
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 RIGHT = new Vector2(1, 0);

    // --- Basic Operations (Mutable) ---
    public Vector2 add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2 sub(Vector2 v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2 mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2 div(float scalar) {
        if (scalar != 0) {
            this.x /= scalar;
            this.y /= scalar;
        }
        return this;
    }

    // --- Immutable Versions ---
    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 sub(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 mul(Vector2 v, float scalar) {
        return new Vector2(v.x * scalar, v.y * scalar);
    }
    public static Vector2 getCenteredXY(float posX, float posY, float width, float height) {
        float centeredX = posX - width / 2f;
        float centeredY = posY - height / 2f;
        return new Vector2(centeredX, centeredY);
    }
    // --- Magnitude ---
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    // --- Normalize ---
    public Vector2 normalize() {
        float len = length();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    public static Vector2 normalize(Vector2 v) {
        float len = (float) Math.sqrt(v.x * v.x + v.y * v.y);
        return (len == 0) ? new Vector2(0, 0) : new Vector2(v.x / len, v.y / len);
    }

    // --- Dot Product ---
    public float dot(Vector2 v) {
        return this.x * v.x + this.y * v.y;
    }

    public static float dot(Vector2 a, Vector2 b) {
        return a.x * b.x + a.y * b.y;
    }

    // --- Distance ---
    public float distance(Vector2 v) {
        float dx = v.x - this.x;
        float dy = v.y - this.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static float distance(Vector2 a, Vector2 b) {
        float dx = b.x - a.x;
        float dy = b.y - a.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    // --- Lerp ---
    public static Vector2 lerp(Vector2 a, Vector2 b, float t) {
        return new Vector2(
            a.x + (b.x - a.x) * t,
            a.y + (b.y - a.y) * t
        );
    }

    // --- Clamp Magnitude ---
    public Vector2 clamp(float maxLength) {
        float lenSq = lengthSquared();
        if (lenSq > maxLength * maxLength) {
            float factor = maxLength / (float) Math.sqrt(lenSq);
            x *= factor;
            y *= factor;
        }
        return this;
    }

    // --- Copy ---
    public Vector2 copy() {
        return new Vector2(this);
    }

    // --- Set ---
    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    // --- Equals ---
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2)) return false;
        Vector2 v = (Vector2) obj;
        return Float.compare(x, v.x) == 0 &&
			Float.compare(y, v.y) == 0;
    }

    // --- String ---
    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")";
    }
}
