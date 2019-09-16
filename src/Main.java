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
    private int level, lives, points;
    private BufferedImage bg;

    Player player;
    Portal portal;
    ArrayList<Enemy> enemies;
    ArrayList<Platform> platforms;
    ArrayList<Spring> springs;
    private boolean playerOnTopOfPlatform, playerIsOnEnemy, playerIsOnSpring;

    public Main(){
        keys = new boolean[256];

        setKeyListener();
        level = 1;
        lives = 5;
        points = 0;

        enemies = new ArrayList<>();
        platforms = new ArrayList<>();
        springs = new ArrayList<>();
        player = new Player(30, 500, 50, 50);
        portal = new Portal(1000, 1000, 75, 75);

        //all levels
        platforms.add(new Platform(0, 700,1920, 300)); //this is the ground, Keren. don't get rid of the ground - Keren
        boolean[] levelsShown = {true, true, true, true, true, true, true, true, true, true};
        platforms.get(0).setLevelsShown(levelsShown);
        portal.setLevelsShown(levelsShown);

        //level 1 - weird slow-mo fall glitch present near enemy #1
        portal.move(1300, 700 - portal.getHeight());
        platforms.add(new Platform(300, 600, 125, 100, 1));
        platforms.add(new Platform(500, 450, 100, 75, 1));
        platforms.add(new Platform(650, 300, 30, 500, 1));
        platforms.add(new Platform(1050, 300, 30, 500, 1));
        springs.add(new Spring(900, 600, 30, 15, 1));
        enemies.add(new Enemy(500, 600, 75, 75, 400, 600, 1, 1));

        //level 2 because i got bored at 10:46pm
        enemies.add(new Enemy(500, 600, 75, 75, 400, 600, 1, 2));
        //under first enemy, there's a weird glitch where player stands for a few seconds mid air before falling
        //only on level 2 though? weird
        enemies.add(new Enemy(1000, 400, 75, 75, 900, 1150, 6, 2));
        platforms.add(new Platform(650, 650, 100, 75, 2));

        playerOnTopOfPlatform = false;
        playerIsOnEnemy = false;
        playerIsOnSpring = false;


        //background image
        try{
            bg = ImageIO.read(new File("res/" + "background.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

        timer = new Timer(1000 / 60, e -> update());
        timer.start();

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

        g2.drawImage(bg, 0, 0, null);

        player.draw(g2);
        portal.draw(g2);

        //draws enemies and platforms
        for(Enemy enema : enemies){
            if(enema.isOnScreen()) {
                    enema.draw(g2);
            }
        }

        for(Platform plat: platforms){
            plat.draw(g2);
        }

        for(Spring sproing : springs){
            sproing.draw(g2);
        }

        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g2.drawString("Level: " + level, 50, 50);
        g2.drawString("Lives: " + lives, 50, 75);
        g2.drawString("Score: " + points, 50, 100);

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
