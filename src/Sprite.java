import java.awt.*;

public class Sprite {

    private int x, y, width, height;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public boolean isTouching(Sprite other){
        int otherX = other.getX();
        int otherY = other.getY();
        int otherWidth = other.getWidth();
        int otherHeight = other.getHeight();

        for (int i = x; i < x+width; i++) {
            for (int j = y; j < y+getWidth(); j++) {
                for (int k = otherX; k < otherX+otherWidth; k++) {
                    for (int l = otherY; l < otherY+otherHeight; l++) {
                        if(i == k && j == l)
                            return true;
                    }
                }
            }
        }
        return false;
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

    public void setHeight(int height) {
        this.height = height;
    }
}
