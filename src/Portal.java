import java.awt.*;

public class Portal extends Sprite {
    public Portal(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g2){
        super.draw(g2);
        g2.setColor(new Color(215, 62, 232));
        g2.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}
