package min2d.core.Components.Renderables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import java.util.HashMap;
import min2d.core.Components.Renderer;

public class TextRenderer extends Renderer {

    private String text = "Text";
    private final Matrix matrix = new Matrix();

    // Settings
    private float textSize = 50f;
    private int color = Color.WHITE;

    private float outlineWidth = 0f;
    private int outlineColor = Color.BLACK;

    private boolean bold = false;
    private boolean italic = false;

    private Paint.Align align = Paint.Align.CENTER;

    private Typeface typeface = Typeface.DEFAULT;
    

    public TextRenderer() {}

    public TextRenderer(String text) {
        this.text = text;
    }

    // -------- GETTERS & SETTERS --------

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getOutlineWidth() {
        return outlineWidth;
    }

    public void setOutlineWidth(float outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public int getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(int outlineColor) {
        this.outlineColor = outlineColor;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public Paint.Align getAlign() {
        return align;
    }

    public void setAlign(Paint.Align align) {
        this.align = align;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    // -------- RENDER --------

    @Override
    public void render(Canvas canvas, Paint p) {
        if (text == null || myObject == null) return;

        int style = Typeface.NORMAL;
        if (bold && italic) style = Typeface.BOLD_ITALIC;
        else if (bold) style = Typeface.BOLD;
        else if (italic) style = Typeface.ITALIC;

        p.setTypeface(Typeface.create(typeface, style));
        p.setTextSize(textSize);
        p.setTextAlign(align);

        matrix.reset();
        float w =p.measureText(text);
        // KEEP normalization using textSize
        
        matrix.postScale(
            myObject.scale.x/textSize,
            -myObject.scale.y/textSize
        );

        matrix.postRotate(myObject.rotation);
        matrix.postTranslate(myObject.position.x, myObject.position.y);

        canvas.save();
        canvas.concat(matrix);

        float x = 0;
        float y = 0;

        String[] lines = text.split("\n");
        float lineHeight = p.getFontSpacing();

        for (int i = 0; i < lines.length; i++) {

            float lineY = y + (i * lineHeight);

            if (outlineWidth > 0) {
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(outlineWidth);
                p.setColor(outlineColor);
                canvas.drawText(lines[i], x, lineY, p);
            }

            p.setStyle(Paint.Style.FILL);
            p.setColor(color);
            canvas.drawText(lines[i], x, lineY, p);
        }

        canvas.restore();
    }

    @Override
    public TextRenderer copy() {
        TextRenderer tr = new TextRenderer(text);
        tr.enabled = enabled;
        tr.renderData = new HashMap<>(renderData);

        tr.textSize = textSize;
        tr.color = color;
        tr.outlineWidth = outlineWidth;
        tr.outlineColor = outlineColor;
        tr.bold = bold;
        tr.italic = italic;
        tr.align = align;
        tr.typeface = typeface;

        return tr;
    }
}
