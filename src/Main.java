import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JPanel{

    public static final int WIDTH=1440, HEIGHT=860;
    private Timer timer;
    private boolean[] keys;
    private int level, lives, points, framesSinceJump;
    private BufferedImage regBG, waterBG;

    Player player;
    Portal portal;
    ArrayList<Enemy> enemies;
    ArrayList<Platform> platforms;
    ArrayList<Spring> springs;
    private boolean playerOnTopOfPlatform, playerIsOnEnemy, playerIsOnSpring, isWaterLevel;

    public Main(){
        keys = new boolean[256];

        setKeyListener();
        level = 1;
        lives = 5;
        points = 0;

        enemies = new ArrayList<>();
        platforms = new ArrayList<>();
        springs = new ArrayList<>();
        player = new Player(30, 625, 50, 50);
        portal = new Portal(1000, 1000, 75, 75);

        //all levels
        platforms.add(new Platform(0, 700,1920, 300)); //this is the ground, Keren. don't get rid of the ground - Keren
        boolean[] levelsShown = {true, true, true, true, true, true, true, true, true, true, true, true, true};
        platforms.get(0).setLevelsShown(levelsShown);
        portal.setLevelsShown(levelsShown);

        //level 1 - weird slow-mo fall glitch present near enemy #1
        portal.move(1300, 700 - portal.getHeight());
        platforms.add(new Platform(300, 600, 125, 100, 1));
        platforms.add(new Platform(500, 450, 100, 75, 1));
        platforms.add(new Platform(650, 300, 30, 500, 1));
        platforms.add(new Platform(950, 300, 30, 500, 1));
        //springs.add(new Spring(900, 600, 30, 15, 1));
        enemies.add(new Enemy(500, 600, 75, 75, 400, 600, 1, 1));

        //level 2 because i got bored at 10:46pm
        enemies.add(new Enemy(500, 600, 75, 75, 400, 600, 1, 2));
        enemies.add(new Enemy(1000, 400, 75, 75, 900, 1150, 6, 2));
        platforms.add(new Platform(650, 650, 100, 75, 2));

        //level 3
        enemies.add(new Enemy(1000, 385, 75, 75, 250, 1400, 1, 3));
        enemies.add(new Enemy(500, 515, 75, 75, 250, 1400, 3, 3));
        enemies.add(new Enemy(250, 630, 75, 75, 250, 1400, 2, 3));
        platforms.add(new Platform(302, 300, 40, 500, 3));
        platforms.add(new Platform(502, 300, 40, 500, 3));
        platforms.add(new Platform(702, 300, 40, 500, 3));
        platforms.add(new Platform(902, 300, 40, 500, 3));
        platforms.add(new Platform(1102, 300, 40, 500, 3));
        platforms.add(new Platform(400, 450, 50, 100, 3));
        platforms.add(new Platform(600, 450, 50, 100, 3));
        platforms.add(new Platform(800, 450, 50, 100, 3));
        platforms.add(new Platform(1000, 450, 50, 100, 3));

        playerOnTopOfPlatform = false;
        playerIsOnEnemy = false;
        playerIsOnSpring = false;
//        isWaterLevel = false;

        //level 4
        platforms.add(new Platform(140, 115, 1300, 50, 4));
        platforms.add(new Platform(0, 245, 1300, 50, 4));
        platforms.add(new Platform(140, 375, 1300, 50, 4));
        platforms.add(new Platform(0, 505, 1300, 50, 4));
        platforms.add(new Platform(140, 635, 1300, 50, 4));

        //level 5
        platforms.add(new Platform(140, 115, 1300, 50, 5));
        platforms.add(new Platform(0, 245, 1300, 50, 5));
        platforms.add(new Platform(140, 375, 1300, 50, 5));
        platforms.add(new Platform(0, 505, 1300, 50, 5));
        platforms.add(new Platform(140, 635, 1300, 50, 5));
        enemies.add(new Enemy(70, 115, 75, 75, 30, 100, 5));
        enemies.add(new Enemy(1400, 245, 75, 75, 215, 275, 5));


        try{
            regBG = ImageIO.read(new File("res/" + "background.png"));
            waterBG = ImageIO.read(new File("res/" + "waterbackground.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

        timer = new Timer(1000 / 60, e -> update());
        timer.start();

    }

    public void update() {

        if(!isWaterLevel)
            player.moveBy(0, (int)player.getvY());
        else
            player.moveBy(0, (int)(player.getvY()*0.1));
        //gravity on player
        if(!playerOnTopOfPlatform) {
            player.setvY((int)player.getvY() + 1);
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
        framesSinceJump++;

        //checks if player is touching the portal
        if(player.isTouching(portal)){
            level++;
            player.reset();
            points += 100;
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
        if(isWaterLevel){
            if(player.getY() < 0){
                player.setY(0);
                player.setvY(-(int)(0.1*player.getvY()) + 1);
            }
        }

        //updates platforms & enemies
        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setLevel(level);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setLevel(level);
            enemies.get(i).move();
        }

        for (int i = 0; i < springs.size(); i++) {
            springs.get(i).setLevel(level);
        }

        //enemy kills player vs player kills enemy
        for(Enemy enema : enemies){
            if (enema.isOnScreen()){
                if (player.isTouchingTop(enema)){
                    enema.setLevelShown(level, false);
                    playerIsOnEnemy = true;
                    points += 50;
                } else if (player.isTouching(enema)){
                    player.reset();
                    lives--;
                }
            }
        }

        for (int i = 0; i < springs.size(); i++) {
            if(player.isTouching(springs.get(i)))
                playerIsOnSpring = true;
        }

        //if game == lost
        if(lives <= 0){
            level = 1;
            lives = 5;
            points = 0;
            for(Enemy enema : enemies) {
                enema.setLevelShown(enema.getLevelShown(), true);
            }

        }
        movePlayer();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if(isWaterLevel)
            g2.drawImage(waterBG, 0, 0, null);
        else
            g2.drawImage(regBG, 0, 0, null);

        player.draw(g2);
        portal.draw(g2);

        //draws enemies and platforms

        for(Platform plat: platforms){
            plat.draw(g2);
        }

        for(Spring sproing : springs){
            sproing.draw(g2);
        }

        for(Enemy enema : enemies){
            if(enema.isOnScreen()) {
                enema.draw(g2);
            }
        }

        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g2.drawString("Level: " + level, 50, 50);
        g2.drawString("Lives: " + lives, 50, 75);
        g2.drawString("Score: " + points, 50, 100);

        if(level == 4){ // level == 4 || any other levels
            isWaterLevel = true;
            portal.setX(1350);
            portal.setY(50);
        } else if (level == 5) {
            isWaterLevel = false;
            portal.setX(1350);
            portal.setY(115 - portal.getHeight());
        } else {
            isWaterLevel = false;
            portal.setX(1350);
            portal.setY(700 - portal.getHeight());
        }

        if(isWaterLevel){
            g2.setColor(new Color(60, 230, 255, 75));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
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
            if((playerOnTopOfPlatform || isWaterLevel) && framesSinceJump > 10) {
                if(!isWaterLevel)
                    player.jump(20);
                else if(player.getvY() < -20) {
                    player.jump(0);
                }
                else
                    player.jump(50);
                framesSinceJump = 0;
            }
        }

        if(keys[KeyEvent.VK_RIGHT]) { //should be replaced with vx code later
            if(!isWaterLevel) {
                if (player.getvX() < 7) {
                    player.setvX((int) player.getvX() + 1.11);
                }
            } else {
                if (player.getvX() < 2) {
                    player.setvX((int) player.getvX() + 1.11);
                }
            }
        }

        if(keys[KeyEvent.VK_LEFT]) {
            if(!isWaterLevel) {
                if (player.getvX() > -7) {
                    player.setvX((int) player.getvX() - 1.11);
                }
            } else {
                if (player.getvX() > -2) {
                    player.setvX((int) player.getvX() - 1.11);
                }
            }
        }

        if(playerIsOnEnemy){
            player.jump(35);
            playerIsOnEnemy = false;
        }

        if(playerIsOnSpring){
            player.jump(20);
            playerIsOnSpring = false;
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
