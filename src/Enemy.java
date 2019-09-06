import java.awt.*;

public class Enemy extends Sprite {

    private int xPos1, xPos2, speed;
    private boolean isMovingLeft;


    public Enemy(int x, int y, int width, int height, int xPos1, int xPos2, int speed) {
        super(x, y, width, height);
        this.xPos1 = xPos1;
        this.xPos2 = xPos2;
        this.speed = speed;
        isMovingLeft = true;
        setColor(Color.RED);
    }

    public void move(){
        if(getX() < xPos1){
            isMovingLeft = false;
        }
        if(getX() > xPos2){
            isMovingLeft = true;
        }
        if(isMovingLeft)
            setX(getX()-speed);
        else
            setX(getX()+speed);
    }


}
