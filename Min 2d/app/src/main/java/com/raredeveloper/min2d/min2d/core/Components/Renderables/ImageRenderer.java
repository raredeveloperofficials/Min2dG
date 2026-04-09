package min2d.core.Components.Renderables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.BitmapFactory;
import android.content.res.Resources;

import java.util.HashMap;

import min2d.core.Components.Renderer;

public class ImageRenderer extends Renderer {

    private Bitmap image;
    private final Matrix matrix = new Matrix();

    public ImageRenderer() {}

    public ImageRenderer(Resources res, int id) {
        image = BitmapFactory.decodeResource(res, id);
    }

    @Override
    public void render(Canvas canvas) {
        if (image == null || myObject == null) return;

        matrix.reset();

        float halfW = image.getWidth() / 2f;
        float halfH = image.getHeight() / 2f;

        // Pivot around image center
        matrix.postTranslate(-halfW, -halfH);

        // Scale using GameObject scale
        matrix.postScale(myObject.scale.x / image.getWidth(), -myObject.scale.y / image.getHeight());

        // Rotate
        matrix.postRotate(myObject.rotation);

        // Translate to object position
        matrix.postTranslate(myObject.position.x, myObject.position.y);

        canvas.drawBitmap(image, matrix, null);
    }

    @Override
    public ImageRenderer copy() {
        ImageRenderer ir = new ImageRenderer();
        ir.enabled = enabled;
        ir.renderData = new HashMap<>(renderData);
        ir.image = this.image;
        return ir;
    }
}
