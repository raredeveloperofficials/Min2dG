package min2d.core.Components;
import min2d.core.Component;
import android.graphics.Canvas;
import java.util.HashMap;
import android.graphics.Paint;
import min2d.core.Vector2;

public abstract class Renderer extends Component {
    protected HashMap<String,Object> renderData;
    public Renderer(){
        renderData = new HashMap<>();
    }
    public abstract void render(Canvas canvas,Paint p);
    public void afterChildUpdateFinished(Canvas can){}
    @Override
    public Renderer copy() {
        try {
            Renderer r = getClass().getDeclaredConstructor().newInstance();

            // Copy base fields
            r.offset = new Vector2(offset);

            // Copy render data (shallow copy at least)
            r.renderData = new HashMap<>(this.renderData);

            return r;
        } catch (Exception e) {
            throw new RuntimeException("Renderer copy failed: " + getClass().getName(), e);
        }
    }
}
