import java.awt.*;

public class Boss extends Sprite{

    private int lives;

    public Boss(int x, int y, int width, int height, int levelShown, int lives) {
        super(x, y, width, height, levelShown);
        this.lives = lives;
    }

    public void update(){
        if(lives == 3 || lives > 3)
            setColor(Color.GREEN);
        if(lives == 2)
            setColor(Color.YELLOW);
        if(lives == 1)
            setColor(Color.RED);
//        if(lives == 0)
//            setLevelShown(levelShown, false);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


}
