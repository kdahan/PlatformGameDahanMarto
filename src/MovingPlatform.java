import java.awt.*;

public class MovingPlatform extends Sprite {

    private int xPos1, xPos2, yPos1, yPos2;
    private int speed, vX, dist, levelShown, vY;
    private boolean isMovingLeft, isMovingDown, verticalMove;

    public MovingPlatform(int x, int y, int width, int height, int xPos1, int xPos2, int speed, int levelShown) {
        super(x, y, width, height, levelShown);
        this.xPos1 = xPos1;
        this.xPos2 = xPos2;
        speed = vX;
        isMovingLeft = true;
        dist = Math.abs(xPos1 - xPos2);
        verticalMove = false;
    }

    public MovingPlatform(int x, int y, int width, int height, int yPos1, int yPos2, int speed, boolean verticalMove, int levelShown) {
        super(x, y, width, height, levelShown);
        this.yPos1 = yPos1;
        this.yPos2 = yPos2;
        speed = vY;
        this.verticalMove = verticalMove;
        isMovingDown = true;
        dist = Math.abs(yPos1 - yPos2);
    }

    public void moveHorizontal(){ // <-- doesn't work
        if(!verticalMove) {
            if (getX() < xPos1) {
                isMovingLeft = false;
            }
            if (getX() > xPos2) {
                isMovingLeft = true;
            }
            if (isMovingLeft)
                setX(getX() - vX);
            else
                setX(getX() + vX);
        }
    }

    public void moveVertical(){ // <-- doesn't work
        if(verticalMove) {
            if (getY() < yPos1)
                isMovingDown = false;

            if (getY() > yPos2)
                isMovingDown = true;

            if (isMovingDown)
                setY(getY() - vY);

            else
                setY(getY() + vY);
        }
    }

    public boolean isVerticalMove() {
        return verticalMove;
    }

    public void draw(Graphics2D g2){
        super.draw(g2);
    }

    //notes: platform draws regularly but won't move :(

}
