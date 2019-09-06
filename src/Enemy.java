public class Enemy extends Sprite {

    private int xPos1, xPos2, yPos1, yPos2;
    private boolean isMovingLeft;


    public Enemy(int x, int y, int width, int height, int xPos1, int xPos2, int yPos1, int yPos2) {
        super(x, y, width, height);
        this.xPos1 = xPos1;
        this.xPos2 = xPos2;
        this.yPos1 = yPos1;
        this.yPos2 = yPos2;
        isMovingLeft = true;
    }

    @Override
    public void move(){

    }


}
