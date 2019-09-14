import java.awt.*;

public class Spring extends Sprite{

    public Spring(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Spring(int x, int y, int width, int height, int levelShown) {
        super(x, y, width, height);
        setLevelShown(levelShown, true);
    }

    @Override
    public void draw(Graphics2D g2){ //need to draw it in a way that doesn't look awful
        g2.setColor(Color.GRAY);
        g2.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}
