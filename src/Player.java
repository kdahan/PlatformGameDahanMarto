import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player extends Sprite{

    private int vY;
    private double vX;
    private BufferedImage image;

    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);

        vY = 0;
        vX = 0;
        setColor(Color.GREEN);

        for (int i = 0; i < getLevelsShown().length; i++) {
            setLevelShown(i, true);
        }

        try{
            image = ImageIO.read(new File("res/" + "player2.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void jump(int jumpPower){
        vY -= jumpPower;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, getX(), getY(), null);
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
