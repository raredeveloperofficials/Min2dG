package min2d.core.Components.Renderables;
import android.graphics.Canvas;
import android.graphics.Paint;
import min2d.core.Components.Renderer;
import min2d.core.Vector2;

public class BoundRenderer extends Renderer {
    private int savedPoint;

    @Override
    public void update(float delta) {
        super.update(delta);
        myObject.blockInputOutOfBound = true;
    }
    
    @Override
    public void render(Canvas canvas, Paint p) {
        savedPoint =canvas.save();
        float scalex= myObject.scale.x/2;
        float scaley= myObject.scale.y/2;
        Vector2 pos = myObject.global_position;
        if(pos!=null)canvas.clipRect(pos.x+offset.x-scalex,pos.y+offset.y-scaley,pos.x+offset.x+scalex,pos.y+offset.y+scaley);
    }

    @Override
    public void afterChildUpdateFinished(Canvas can) {
        super.afterChildUpdateFinished(can);
        if(can!=null) can.restoreToCount(savedPoint);
    }
    
    
}
