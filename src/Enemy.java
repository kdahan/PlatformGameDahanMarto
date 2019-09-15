import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Enemy extends Sprite {

    private int xPos1, xPos2, speed, vX, dist;
    private boolean isMovingLeft;
    private BufferedImage image;

    public Enemy(int x, int y, int width, int height, int xPos1, int xPos2, int speed) { //xPos1 must be left of xPos2
        super(x, y, width, height);
        this.xPos1 = xPos1;
        this.xPos2 = xPos2;
        this.speed = speed;
        isMovingLeft = true;
        setColor(Color.RED);
        vX = speed;
        dist = Math.abs(xPos1 - xPos2);

        try{
            image = ImageIO.read(new File("res/" + "enemy.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Enemy(int x, int y, int width, int height, int xPos1, int xPos2, int speed, int levelShown) { //xPos1 must be left of xPos2
        super(x, y, width, height);
        this.xPos1 = xPos1;
        this.xPos2 = xPos2;
        this.speed = speed;
        isMovingLeft = true;
        setColor(Color.RED);
        vX = speed;
        dist = Math.abs(xPos1 - xPos2);
        setLevelShown(levelShown, true);

        try{
            image = ImageIO.read(new File("res/" + "enemy2.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2){
            g2.drawImage(image, getX(), getY(), null);
    }

    public void move(){
        if(getX() < xPos1){
            isMovingLeft = false;
        }
        if(getX() > xPos2){
            isMovingLeft = true;
        }
        if(isMovingLeft)
            setX(getX() - vX);
        else
            setX(getX() + vX);
    }

}
