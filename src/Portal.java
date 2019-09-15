import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Portal extends Sprite {

    private BufferedImage image;

    public Portal(int x, int y, int width, int height) {
        super(x, y, width, height);

        try{
            image = ImageIO.read(new File("res/" + "portal.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void draw(Graphics2D g2){

        g2.drawImage(image, getX(), getY(), null);

    }
}
