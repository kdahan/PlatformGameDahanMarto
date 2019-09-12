import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JPanel{

    public static final int WIDTH=1440, HEIGHT=850;
    private Timer timer;
    private boolean[] keys;
    private int level;

    Player player;
    ArrayList<Enemy> enemies;
    ArrayList<Platform> platforms;
    private boolean playerOnTopOfPlatform;


    public Main(){

        keys = new boolean[256];

        timer = new Timer(1000 / 60, e -> update());
        timer.start();
        setKeyListener();

        level = 1;
        player = new Player(50, 300, 50, 50);

        //spawn platforms
        platforms = new ArrayList<>();
        platforms.add(new Platform(0, 700,1920, 300));
        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setLevelShown(1, true);
        }
        playerOnTopOfPlatform = false;

        //spawn enemies
        enemies = new ArrayList<>();
    }

    public void update() {

        player.moveBy(0, player.getvY());
        //gravity on player
        if(!playerOnTopOfPlatform) {
            player.setvY(player.getvY() + 1);
        }
        playerOnTopOfPlatform = false;
        for (int i = 0; i < platforms.size(); i++) {
            if(player.isTouchingTop(platforms.get(i))) {
                playerOnTopOfPlatform = true;
                player.setvY(0);
                player.setY(platforms.get(i).getY() - player.getHeight());
            }
        }

        //momentum on player
        if(player.getvX() > 0)
            player.setvX(player.getvX() - 0.1);
        if(player.getvX() < 0)
            player.setvX(player.getvX() + 0.1);
        player.moveBy((int)player.getvX(), 0);

        //keeps player on screen
        if(player.getX() <= 0)
            player.setX(0);
        if(player.getX() >= 1400)
            player.setX(1400);

        //updates platforms & enemies
        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setLevel(level);
        }
//        for (int i = 0; i < enemies.size(); i++) {
//            enemies.get(i).setLevel(level);
//            enemies.get(i).move();
//        }

//        checks if player is on a platform


        movePlayer();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);

        //draws enemies and platforms
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g2);
        }
        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).draw(g2);
        }

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



            }
            @Override
            public void keyReleased(KeyEvent e) {

                keys[e.getKeyCode()] = false;

            }
        });
    }


    public void movePlayer(){
        if(keys[KeyEvent.VK_UP]){
            if(playerOnTopOfPlatform) {
                player.jump(20);
            }

        }

        if(keys[KeyEvent.VK_RIGHT]) { //should be replaced with vx code later
            if(player.getvX() < 5) {
                player.setvX((int)player.getvX() + 2);
            }
        }

        if(keys[KeyEvent.VK_LEFT]) {
            if (player.getvX() > -5) {
                player.setvX((int)player.getvX() - 2);
            }
        }
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
