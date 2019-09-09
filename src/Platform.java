import java.awt.*;
import java.util.ArrayList;

public class Platform extends Sprite {

    private boolean[] levelsShown;
    private int level;

    public Platform(int x, int y, int width, int height) {
        super(x, y, width, height);
        levelsShown  = new boolean[10];
        level = 0;
    }

    @Override
    public void draw(Graphics2D g2){
        if(levelsShown[level]){
            g2.setColor(Color.BLACK);
            g2.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    public void setLevelShown(int level, boolean isShown){
        levelsShown[level] = isShown;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isOnScreen(){
        if(levelsShown[level])
            return true;
        return false;
    }
}