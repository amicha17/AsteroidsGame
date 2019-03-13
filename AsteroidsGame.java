import acm.program.*;
import acm.graphics.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import java.applet.AudioClip;
import acm.util.MediaTools;

public class AsteroidsGame extends GraphicsProgram
{
    private ArrayList<Asteroid> asteroids;
    private boolean playing;
    private GLabel notificationLabel, scoreLabel, livesLabel, levelLabel, pauseLabel, finalScoreLabel;
    private Ship ship;
    private AllyShip allyShip;
    private ArrayList<Bullet> bullets;
    private int level;
    private int ships;
    private int score;
    private AudioClip thrustClip, fireClip, bigBangClip, mediumBangClip, smallBangClip;

    public void init()
    {
        thrustClip = MediaTools.loadAudioClip("thrust.wav");   
        fireClip = MediaTools.loadAudioClip("fire.wav");   
        bigBangClip = MediaTools.loadAudioClip("bangLarge.wav");   
        mediumBangClip = MediaTools.loadAudioClip("bangMedium.wav");   
        smallBangClip = MediaTools.loadAudioClip("bangSmall.wav");   

        playing = false;
        level = 0;
        ships = 3;
        score = 0;

        setBackground(Color.BLACK);

        notificationLabel = new GLabel("(UP) = thrust, (LEFT) = rotate left, (RIGHT) = rotate right, (SPACE) = fire. Click mouse to continue");
        notificationLabel.setColor(Color.WHITE);
        notificationLabel.setFont("Courier-Plain-12");
        notificationLabel.setLocation((getWidth()-notificationLabel.getWidth())/2, getHeight()/2-40);
        add(notificationLabel);
        
        scoreLabel = new GLabel("Score:"+ score);
        scoreLabel.setColor(Color.WHITE);
        scoreLabel.setFont("Courier-Plain-10");
        scoreLabel.setLocation(16, 16);
        add(scoreLabel);
        
        livesLabel = new GLabel("Lives:" + ships);
        livesLabel.setColor(Color.WHITE);
        livesLabel.setFont("Courier-Plain-10");
        livesLabel.setLocation(16, 25);
        add(livesLabel);
        
        levelLabel = new GLabel("Level:  " + level);
        levelLabel.setColor(Color.WHITE);
        levelLabel.setFont("Courier-Plain-10");
        levelLabel.setLocation(16, 34);
        add(levelLabel);
        
        pauseLabel = new GLabel("Pause");
        pauseLabel.setColor(Color.WHITE);
        pauseLabel.setFont("Courier-Plain-100");
        pauseLabel.setLocation(250, 100);   
        
        finalScoreLabel = new GLabel("Your final score was " + score);
        finalScoreLabel.setColor(Color.WHITE);
        
        asteroids = new ArrayList<Asteroid>();
        bullets = new ArrayList<Bullet>();
        makeAsteroids();
        
        ship = new Ship(getWidth(), getHeight());
        ship.setLocation(getWidth()/2, getHeight()/2);
        add(ship);
        
        allyShip = new AllyShip(getWidth(), getHeight());
        allyShip.setLocation(0, getHeight()/2);
        
        addKeyListeners();
        addMouseListeners();
    }

    public void mouseClicked(MouseEvent e)
    {
        if (ships>0)
        {
            playing=true;
            remove(notificationLabel);
        }
        else
        {
            notificationLabel.setLabel("GAME OVER");
            finalScoreLabel.setLabel("Your final score was " + score);
            finalScoreLabel.setLocation(0, 400);
            notificationLabel.setLocation(0, 200);
            finalScoreLabel.setFont("Courier-Plain-45");
            notificationLabel.setFont("Courier-Plain-139");
            add(notificationLabel);
            add(finalScoreLabel);
        }
    }
    
    public void keyPressed(KeyEvent e)
    {
        if (!playing)
        {
            if (e.getKeyCode()==KeyEvent.VK_R)
            {
                playing = true;
                remove(pauseLabel);
            }
            else
                return;
        }
        if (e.getKeyCode()==KeyEvent.VK_P)
        {
            playing = false;
            add(pauseLabel);
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT)
        {
            ship.rotate(15);
            allyShip.rotate(15);
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT)
        {
            ship.rotate(-15);
            allyShip.rotate(-15);
        }
        if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            thrustClip.play();
            ship.increaseVelocity(0.2);
            allyShip.increaseVelocity(0.2);
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            Bullet b = ship.shoot();
            bullets.add(b);
            add(b);
            Bullet b2 = allyShip.shoot();
            bullets.add(b2);
            add(b2);
            fireClip.play();
        }
        if(e.getKeyCode()==KeyEvent.VK_S)
        {
            ArrayList<Bullet> superBullets = ship.superShoot();
            for (int i=0; i<superBullets.size(); i++)
            {
                bullets.add(superBullets.get(i));
                add(superBullets.get(i));
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_B)
        {
            Bullet bullet = ship.shoot();
            bullet.scale(10);
            bullets.add(bullet);
            add(bullet);
        }
    }

    private void makeAsteroids()
    {
        for (int i=0; i<3+level; i++)
        {
            Asteroid x = new Asteroid(getWidth(), getHeight());
            x.setLocation(Math.random()*getWidth(), Math.random()*getHeight());
            x.rotate(Math.random()*360);
            x.increaseVelocity(0.5);
            asteroids.add(x);
            add(x);
        }
    }

    public void run()
    {
        while (true)
        {
            pause(10);
            for (int i=0; i<asteroids.size(); i++)
            {
                asteroids.get(i).updatePosition();
                shotAsteroid(asteroids.get(i));
            }
            if (playing)
            {
                ship.updatePosition();
                allyShip.updatePosition();
                for (int i=0; i<bullets.size(); i++)
                {
                    if(!bullets.get(i).stillMoving())
                    {
                        remove(bullets.get(i));
                        bullets.remove(i);
                        i--;
                    }
                    else
                    {
                        bullets.get(i).updatePosition();
                    }
                }
                shipCollide();
                scoreLabel.setLabel("Score:"+ score);
                livesLabel.setLabel("Lives:"+ ships);
                if (asteroids.size()==0)
                {
                    int nextLevel = level+1;
                    notificationLabel.setLabel("You have passed Level " + level + ". Get ready for Level " + nextLevel);
                    notificationLabel.setLocation(20, 200);
                    notificationLabel.setFont("Courier-Plain-25");
                    add(notificationLabel);
                    waitForClick();
                    level++;
                    levelLabel.setLabel("Level " + level);
                    add(allyShip);
                    makeAsteroids();
                    notificationLabel.setLabel("Try again");
                    notificationLabel.setLocation(100, 220);
                    notificationLabel.setFont("Courier-Plain-100");
                }
            }
        }
    } 
    
    private void shotAsteroid(Asteroid a)
    {
        GPoint point = a.getLocation();
        for (int i=0; i<bullets.size(); i++)
        {
            a=checkForCollisions(bullets.get(i));
            if(a!=null)
            {
                asteroids.remove(a);
                remove(a);
                remove(bullets.get(i));
                bullets.remove(i);
                i--;
                if (a instanceof SmallAsteroid)
                {
                    score+=1000;
                    smallBangClip.play();
                }
                else if (a instanceof MediumAsteroid)
                {
                    double angle = Math.random()*360;
                    for (int j=0; j<3; j++)
                    {
                        SmallAsteroid b = new SmallAsteroid(getWidth(), getHeight());
                        b.setLocation(point);
                        b.rotate(angle+120*j);
                        b.increaseVelocity(1.5);
                        asteroids.add(b);
                        add(b);
                        mediumBangClip.play();
                    }
                    score+=500;
                }
                else 
                {
                    double angle = Math.random()*360;
                    for (int k=0; k<3; k++)
                    {
                        MediumAsteroid c = new MediumAsteroid(getWidth(), getHeight());
                        c.setLocation(point);   
                        c.rotate(angle+120*k);
                        c.increaseVelocity(1.0);
                        asteroids.add(c);
                        add(c);
                        bigBangClip.play();
                    }
                    score+=100;
                }
            }
        }
    }

    private void lostLife()
    {
        remove(ship);
        ship = new Ship(getWidth(), getHeight());
        ship.setLocation(getWidth()/2, getHeight()/2);
        add(ship);
        playing = false;
        ships--;
        add(notificationLabel);
    }

    private void shipCollide()
    {
        Asteroid x = checkForCollisions(ship);
        if (x!=null)
        {
            lostLife();
        }
    }

    private Asteroid checkForCollisions(GVectorPolygon obj)
    {
        for (Asteroid a:asteroids)
            if (a.getBounds().intersects(obj.getBounds()))
                return a;
        return null;
    }
}
