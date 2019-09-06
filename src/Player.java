import java.awt.*;

public class Player extends Sprite{

    private int vY;
    private double vX;

    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);
        vY = 0;
        vX = 0
        setColor(Color.GREEN);

    }

    public void jump(int jumpPower){
        vY -= jumpPower;
        setY(getY()-10);
    }

    public int getvY() {
        return vY;
    }

    public void setvY(int vY) {
        this.vY = vY;
    }

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }
}
