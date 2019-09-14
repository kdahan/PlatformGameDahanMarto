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
    private int level, lives;

    Player player;
    Portal portal;
    ArrayList<Enemy> enemies;
    ArrayList<Platform> platforms;
    private boolean playerOnTopOfPlatform, playerIsOnEnemy;

    public Main(){

        enemies = new ArrayList<>();

        keys = new boolean[256];

        timer = new Timer(1000 / 60, e -> update());
        timer.start();
        setKeyListener();

        level = 1;
        lives = 5;
        player = new Player(50, 300, 50, 50);
        portal = new Portal(1000, 1000, 75, 75);
        portal.setColor(new Color(212, 178, 32));

        //spawn platforms
        platforms = new ArrayList<>();

        //all levels
        platforms.add(new Platform(0, 700,1920, 300)); //this is the ground, Keren. don't get rid of the ground - Keren
        boolean[] levelsShown = {true, true, true, true, true, true, true, true, true, true};
        platforms.get(0).setLevelsShown(levelsShown);

        //level 1
        portal.move(1300, 700 - portal.getHeight());
        portal.setLevelsShown(levelsShown);
        platforms.add(new Platform(800, 600, 50, 100, 1));
        platforms.add(new Platform(1000, 600, 100, 100, 1));
        platforms.add(new Platform(500, 500, 100, 50, 1));
        enemies.add(new Enemy(500, 600, 75, 75, 400, 600, 1, 1));

        //level 2 because i got bored at 10:46pm
        enemies.add(new Enemy(500, 600, 75, 75, 400, 600, 1, 2));
        //under first enemy, there's a weird glitch where player stands for a few seconds mid air before falling
        //only on level 2 though? weird
        enemies.add(new Enemy(1000, 400, 75, 75, 900, 1150, 6, 2));
        platforms.add(new Platform(650, 650, 100, 75, 2));




        playerOnTopOfPlatform = false;
        playerIsOnEnemy = false;

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
            if(platforms.get(i).isTouchingTop(player)){
                player.setvY(-(int)(0.5*player.getvY()) + 1);
            }
        }

        //checks if player is touching the portal
        if(player.isTouching(portal)){
            level++;
            player.reset();
        }

        //momentum on player
        if(player.getvX() > 0)
            player.setvX(player.getvX() - 0.1);
        if(player.getvX() < 0)
            player.setvX(player.getvX() + 0.1);
        if((int)player.getvX() != 0) {
            player.moveBy((int) player.getvX(), 0);
            for(Platform plat : platforms){
                if (player.isTouchingSide(plat) && player.getX() < plat.getX()) {
                    player.setX(plat.getX() - player.getWidth());
                }
                if (player.isTouchingSide(plat) && player.getX() > plat.getX()) {
                    player.setX(plat.getX() + plat.getWidth());
                }
            }
        }
        //keeps player on screen
        if(player.getX() <= 0)
            player.setX(0);
        if(player.getX() >= 1440 - player.getWidth())
            player.setX(1440 - player.getWidth());

        //updates platforms & enemies
        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setLevel(level);
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setLevel(level);
            enemies.get(i).move();
        }

        for(Enemy enema : enemies){
            if (enema.isOnScreen()){
                if (player.isTouchingTop(enema)){
                    enema.setLevelShown(level, false);
                    playerIsOnEnemy = true;
                } else if (player.isTouching(enema)){
                    player.reset();
                    lives--;
                }
            }
        }

        if(lives <= 0){
            level = 1;
            lives = 5;
        }

        movePlayer();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        portal.draw(g2);

        //draws enemies and platforms
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g2);
        }
        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).draw(g2);
        }

        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g2.drawString("Level: " + level, 50, 50);
        g2.drawString("Lives: " + lives, 50, 75);

        if (level == 3){
            g2.setColor(Color.RED);
            g2.setFont(new Font("Serif", Font.PLAIN, 75));
            g2.drawString("Once upon a time, I was bored:", 100, 100);
            g2.drawString("I made a platformer. And everybody loved", 100, 200);
            g2.drawString("it. You too Jerry. And everybody loved", 100, 300);
            g2.drawString("and favorited it and followed BluCube.", 100, 400);
            g2.drawString("THE END.", 100, 500);
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
            if(player.getvX() < 7) {
                player.setvX((int)player.getvX() + 1.11);
            }
        }

        if(keys[KeyEvent.VK_LEFT]) {
            if (player.getvX() > -7) {
                player.setvX((int)player.getvX() - 1.11);
            }
        }

        if(playerIsOnEnemy){
            player.jump(35);
            playerIsOnEnemy = false;
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
