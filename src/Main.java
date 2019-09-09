import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JPanel{

    public static final int WIDTH=1450, HEIGHT=850;
    private Timer timer;
    private boolean[] keys;

    Player player = new Player(200, 200, 50, 50);

    Enemy testEnemy = new Enemy(300, 400, 50, 50, 300, 500, 5);


    public Main(){

        keys = new boolean[256];

        timer = new Timer(1000 / 60, e -> update());
        timer.start();
        setKeyListener();
    }

    public void update() {

        //gravity on player
        if(player.getY() < 600 - player.getHeight())
            player.setvY(player.getvY()+1);
        else {
            player.setvY(0);
            player.setY(600 - player.getHeight());
        }
        player.moveBy(0, player.getvY());

        //momentum on player
        player.moveBy((int)player.getvX(), 0);
        if(player.getvX() > 0)
            player.setvX(player.getvX() - 0.1);
        if(player.getvX() < 0)
            player.setvX(player.getvX() + 0.1);

        if(player.getX() <= 0)
            player.setX(0);

        if(player.getX() >= 1400)
            player.setX(1400);


        testEnemy.move();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        //ground
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 600, 1920, 400);


        testEnemy.draw(g2);

        repaint();
    }

    public void setKeyListener(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {

                keys[e.getKeyCode()] = true;

                if(keys[KeyEvent.VK_UP]){
                    if( player.getY() >= 600 - player.getHeight()) { //replace 600 with touching the ground
                        player.jump(20);
                    }

                }

                if(keys[KeyEvent.VK_RIGHT]) { //should be replaced with vx code later
                    if(player.getvX() < 5) {
                        player.setvX((int)player.getvX() + 1.5);
                    }
                }

                if(keys[KeyEvent.VK_LEFT]) {
                    if (player.getvX() > -5) {
                        player.setvX((int)player.getvX() - 1.5);
                    }
                }

            }
            @Override
            public void keyReleased(KeyEvent e) {

                keys[e.getKeyCode()] = false;

            }
        });
    }

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setBounds(0, 0, WIDTH, HEIGHT + 22); //(x, y, w, h) 22 due to title bar.

        Main panel = new Main();

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);

    }

}
