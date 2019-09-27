import java.awt.*;

public class FirstBoss extends Boss{

    private int xPos1, xPos2, yPos1, yPos2, vertSpeed, horSpeed;
    private boolean isMovingLeft;

    public FirstBoss(int x, int y, int width, int height, int levelShown, int lives, int xPos1, int xPos2, int yPos1, int yPos2,
                     int vertSpeed, int horSpeed) {
        super(x, y, width, height, levelShown, lives);
        this.xPos1 = xPos1;
        this.yPos1 = yPos1;
        this.xPos2 = xPos2;
        this.yPos2 = yPos2;
        this.vertSpeed = vertSpeed;
        this.horSpeed = horSpeed;
        isMovingLeft = true;
        move(xPos1, yPos1);

    }

    @Override
    public void update(){
        super.update();

        if(getLives() > 3)
            setColor(Color.GREEN);
        else if(getLives() > 2)
            setColor(Color.YELLOW);
        else
            setColor(Color.RED);

        if(getLives() > 2){
            //changes direction
            if(getX() > xPos2){
                isMovingLeft = true;
                move(xPos2, yPos2);
            }
            if(getX() < xPos1){
                isMovingLeft = false;
                move(xPos1, yPos1);
            }

            //movement
            if(getX() < ((xPos2 - xPos1)/2) + xPos1){
                if(isMovingLeft)
                    moveBy(-horSpeed, -vertSpeed);
                else
                    moveBy(horSpeed, vertSpeed);
            } else {
                if(isMovingLeft)
                    moveBy(-horSpeed, vertSpeed);
                else
                    moveBy(horSpeed, -vertSpeed);
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

    public int getxPos1() {
        return xPos1;
    }

    public int getyPos1() {
        return yPos1;
    }

    public int getVertSpeed() {
        return vertSpeed;
    }

    public int getHorSpeed() {
        return horSpeed;
    }

    public void setVertSpeed(int vertSpeed) {
        this.vertSpeed = vertSpeed;
    }

    public void setHorSpeed(int horSpeed) {
        this.horSpeed = horSpeed;
    }
}