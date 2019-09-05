public class Player extends Sprite{

    private static int jumpHeight;

    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);

        jumpHeight =

    }

    public void move(){

        super.moveBy(getX(), getY());

    }



}
