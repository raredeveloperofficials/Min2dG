package min2d.core.Components;
import min2d.core.Component;
import android.graphics.Canvas;
import java.util.HashMap;

public abstract class Renderer extends Component {
    private HashMap<String,Object> renderData;
    public Renderer(){
        renderData = new HashMap<>();
    }
    public abstract void render(Canvas canvas);

    @Override
    public Renderer copy() {
        return null;
    }
}
