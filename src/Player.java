import java.awt.*;

public class Player extends Sprite{

    private static int jumpHeight;
    private int x, y, width, height;

    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);

    }

    @Override
    public void draw(Graphics2D g2){
        g2.setColor(Color.RED);
        g2.fillRect(x, y, width, height);
    }

}
