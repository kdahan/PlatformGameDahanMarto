import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JPanel{

    public static final int WIDTH=1920, HEIGHT=850;
    private Timer timer;

    Player player = new Player(200, 200, 50, 50);
    private boolean playerJumping = false;
    private int playerJumpCounter = 10;


    public Main(){


        timer = new Timer(1000 / 60, e -> update());
        timer.start();
        setKeyListener();
    }

    public void update() {

        //gravity on player
        if(player.getY() < 600 - player.getHeight())
            player.setvY(player.getvY()+1);
        else
            player.setvY(0);
        player.moveBy(0, player.getvY());

        //code for player jumps
        if(playerJumping){
            playerJumpCounter--;
            player.jump(playerJumpCounter/2);
            if(playerJumpCounter == 0){
                playerJumpCounter = 10;
                playerJumping = false;
            }
        }

        //momentum on player
        player.moveBy((int)player.getvX(), 0);
        if(player.getvX() > 0)
            player.setvX(player.getvX() - 0.1);
        if(player.getvX() < 0)
            player.setvX(player.getvX() + 0.1);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //ground
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 600, 1920, 400);

        player.draw(g2);

        repaint();
    }

    public void setKeyListener(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    if(!playerJumping && player.getY() >= 600 - player.getWidth()) //replace 600 with touching the ground
                        playerJumping = true;
                }

                if(e.getKeyCode() == KeyEvent.VK_RIGHT) { //should be replaced with vx code later
                    if(player.getvX() < 5) {
                        player.setvX((int)player.getvX() + 1.5);
                    }
                }

                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (player.getvX() > -5) {
                        player.setvX((int)player.getvX() - 1.5);
                    }
                }

            }
            @Override
            public void keyReleased(KeyEvent e) {

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
