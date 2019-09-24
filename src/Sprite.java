import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Sprite {

    private int x, y, width, height;
    private Color color;
    private boolean[] levelsShown;
    private int level;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        levelsShown = new boolean[100];
        level = 0;
        color = Color.BLACK;
    }

    public Sprite(int x, int y, int width, int height, int levelShown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        levelsShown  = new boolean[100];
        level = 0;
        color = Color.BLACK;
        levelsShown[levelShown] = true;
    }


    public boolean isTouching(Sprite other){
        if(other.isOnScreen() && isOnScreen()) {
            int otherX = other.getX();
            int otherY = other.getY();
            int otherWidth = other.getWidth();
            int otherHeight = other.getHeight();

            Rectangle firstSprite = new Rectangle(getX(), getY(), getWidth(), getHeight());
            Rectangle otherSprite = new Rectangle(otherX, otherY, otherWidth, otherHeight);
            return firstSprite.intersects(otherSprite);
        }
        return false;
    }

    public boolean isTouchingTop(Sprite other){
        if(other.isOnScreen() && isOnScreen()) {
            int otherX = other.getX();
            int otherY = other.getY();
            int otherWidth = other.getWidth();
            int majorityHeight = (int) (getHeight() * 0.8);

            Rectangle bottom = new Rectangle(getX(), getY() + majorityHeight, getWidth(), getHeight() - majorityHeight);
            Rectangle topOfOther = new Rectangle(otherX, otherY, otherWidth, 30);
            return bottom.intersects(topOfOther);
        }
        return false;
    }

    public boolean isTouchingSide(Sprite other){

        int otherX = other.getX();
        int otherY = other.getY();
        int otherWidth = other.getWidth();
        int otherHeight = other.getHeight();

        Rectangle mainLeft = new Rectangle(x, y, (int)(width*0.1), height);
        Rectangle mainRight = new Rectangle(x + (int)(width*0.9), y, (int)(width*0.1), height);
        Rectangle otherLeft = new Rectangle(otherX, otherY, (int)(otherWidth*0.1), otherHeight);
        Rectangle otherRight = new Rectangle(otherX + (int)(otherWidth*0.9), otherY,
                (int)(otherWidth*0.1), otherHeight);

        if(other.isOnScreen() && isOnScreen()){
            if(mainLeft.intersects(otherRight) || mainRight.intersects(otherLeft))
                return true;
        }
        return false;
    }

    public void draw(Graphics2D g2){
        if(levelsShown[level]) {
            g2.setColor(color);
            g2.fillRect(x, y, width, height);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void move(int newX, int newY){
        x = newX;
        y = newY;
    }

    public void moveBy(int moveX, int moveY){
        x += moveX;
        y += moveY;
    }

    //getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public boolean[] getLevelsShown() {
        return levelsShown;
    }

    public void setLevelsShown(boolean[] levelsShown) {
        this.levelsShown = levelsShown;
    }

    public Color getColor() {
        return color;
    }
}
