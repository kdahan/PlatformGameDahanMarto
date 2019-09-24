import java.awt.*;

public class FirstBoss extends Boss{

    private int xPos1, xPos2, yPos1, yPos2, speed;
    private boolean isMovingLeft;

    public FirstBoss(int x, int y, int width, int height, int levelShown, int lives, int xPos1, int xPos2,
                     int yPos1, int yPos2, int vertSpeed, int horSpeed) {
        super(x, y, width, height, levelShown, lives);
        this.xPos1 = xPos1;
        this.yPos1 = yPos1;
        this.xPos2 = xPos2;
        this.yPos2 = yPos2;
        this.speed = speed;
        isMovingLeft = true;

    }

    @Override
    public void update(){
        super.update();

        if(getLives() > 1){
            //changes direction
            if(getX() > xPos2){
                isMovingLeft = true;
            }
            if(getX() < xPos1){
                isMovingLeft = false;
            }

            //movement
            if(getX() < ((xPos2 - xPos1)/2)){
                if(isMovingLeft)
                    moveBy(-4, -3);
                else
                    moveBy(4, 3);
            } else {
                if(isMovingLeft)
                    moveBy(-4 , 3);
                else
                    moveBy(4, -3);
            }

        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (isOnScreen()) {
            g2.setColor(getColor());
            g2.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}