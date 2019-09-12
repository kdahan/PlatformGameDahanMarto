import java.awt.*;

public class Enemy extends Sprite {

    private int xPos1, xPos2, speed, vX, dist;
    private boolean isMovingLeft;


    public Enemy(int x, int y, int width, int height, int xPos1, int xPos2, int speed) { //xPos1 must be left of xPos2
        super(x, y, width, height);
        this.xPos1 = xPos1;
        this.xPos2 = xPos2;
        this.speed = speed;
        isMovingLeft = true;
        setColor(Color.RED);
        vX = speed;
        dist = Math.abs(xPos1 - xPos2);
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
