import java.awt.*;
import java.util.ArrayList;

public class Platform extends Sprite {

    public Platform(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Platform(int x, int y, int width, int height, int levelShown) {
        super(x, y, width, height);
        setLevelShown(levelShown, true);
    }

}