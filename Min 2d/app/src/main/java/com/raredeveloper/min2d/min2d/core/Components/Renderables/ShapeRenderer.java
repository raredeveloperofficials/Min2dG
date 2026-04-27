package min2d.core.Components.Renderables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.HashMap;
import min2d.core.Components.Renderer;
import min2d.core.Vector2;

public class ShapeRenderer extends Renderer {

    public enum ShapeType {
        RECT,
        CIRCLE,
        LINE,
        TRIANGLE,
        ROUNDED_RECT
        }

    public ShapeType type = ShapeType.RECT;

    public int color = Color.WHITE;
    public float strokeWidth = 5f;
    public boolean fill = true;
    public boolean outline = false;

    // For rounded rect
    public float cornerRadius = 20f;

    // Internal reusable paint
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    public void render(Canvas canvas, Paint p) {
        if (myObject == null) return;

        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        Vector2 pos = myObject.global_position;
        float x = pos.x + offset.x;
        float y = pos.y+ offset.y;

        float w = myObject.scale.x;
        float h = myObject.scale.y;
        
        canvas.save();

        // Rotate around object center
        canvas.rotate(myObject.rotation, x, y);

        switch (type) {

            case RECT:
                drawRect(canvas, x, y, w, h);
                break;

            case CIRCLE:
                drawCircle(canvas, x, y, w);
                break;

            case LINE:
                drawLine(canvas, x, y, w, h);
                break;

            case TRIANGLE:
                drawTriangle(canvas, x, y, w, h);
                break;

            case ROUNDED_RECT:
                drawRoundedRect(canvas, x, y, w, h);
                break;
        }

        canvas.restore();
    }

    private void drawRect(Canvas canvas, float x, float y, float w, float h) {
        float left = x - w / 2f;
        float top = y - h / 2f;
        float right = x + w / 2f;
        float bottom = y + h / 2f;

        if (fill) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(left, top, right, bottom, paint);
        }

        if (outline) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    private void drawCircle(Canvas canvas, float x, float y, float size) {
        float r = size / 2f;

        if (fill) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, r, paint);
        }

        if (outline) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(x, y, r, paint);
        }
    }

    private void drawLine(Canvas canvas, float x, float y, float w, float h) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(x - w / 2f, y - h / 2f, x + w / 2f, y + h / 2f, paint);
    }

    private void drawTriangle(Canvas canvas, float x, float y, float w, float h) {
        Path path = new Path();
        path.moveTo(x, y - h / 2f);
        path.lineTo(x - w / 2f, y + h / 2f);
        path.lineTo(x + w / 2f, y + h / 2f);
        path.close();

        if (fill) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);
        }

        if (outline) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);
        }
    }

    private void drawRoundedRect(Canvas canvas, float x, float y, float w, float h) {
        float left = x - w / 2f;
        float top = y - h / 2f;
        float right = x + w / 2f;
        float bottom = y + h / 2f;

        RectF rect = new RectF(left, top, right, bottom);

        if (fill) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
        }

        if (outline) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
        }
    }

    @Override
    public ShapeRenderer copy() {
        ShapeRenderer sr = new ShapeRenderer();
        sr.enabled = enabled;
        sr.renderData = new HashMap<>(renderData);

        sr.type = type;
        sr.color = color;
        sr.strokeWidth = strokeWidth;
        sr.fill = fill;
        sr.outline = outline;
        sr.cornerRadius = cornerRadius;

        return sr;
    }
}
