package min2d.core.Components;
import min2d.core.Component;
import android.graphics.Canvas;
import java.util.HashMap;
import android.graphics.Paint;

public abstract class Renderer extends Component {
    protected HashMap<String,Object> renderData;
    public Renderer(){
        renderData = new HashMap<>();
    }
    public abstract void render(Canvas canvas,Paint p);

    @Override
    public Renderer copy() {
        return null;
    }
}
