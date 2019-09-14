import java.awt.*;

public class Player extends Sprite{

    private int vY;
    private double vX;

    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);
        vY = 0;
        vX = 0;
        setColor(Color.GREEN);

        for (int i = 0; i < getLevelsShown().length; i++) {
            setLevelShown(i, true);
        }

    }

    public void jump(int jumpPower){
        vY -= jumpPower;
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

    public void reset(){
        setvX(0);
        setvY(0);
        setX(50);
        setY(300);
    }

}
