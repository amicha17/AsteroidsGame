import java.awt.Color;
import acm.graphics.*;
import java.util.*;

public class Ship extends GVectorPolygon
{
    public Ship(int w, int h)
    {
        super(w, h);
        addVertex(4, 4);
        addVertex(14, 8);
        addVertex(4, 10);
        addVertex(8, 8);
        scale(2);
        setColor(Color.white);
        recenter();
    }

    public Bullet shoot()
    {
        GRectangle rect = getBoundingRectangle();
        Bullet bullet = new Bullet((int)(rect.getWidth()), (int)(rect.getHeight()));
        bullet.setLocation(getX(), getY());
        bullet.rotate(theta);
        bullet.increaseVelocity(4);
        return bullet;
    }
    
    public ArrayList<Bullet> superShoot()
    {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        GRectangle rect = getBoundingRectangle();
        Bullet b1 = new Bullet((int)(rect.getWidth()), (int)(rect.getHeight()));
        b1.setLocation(0,0);
        b1.rotate(320);
        b1.increaseVelocity(4);
        bullets.add(b1);
        Bullet b2 = new Bullet((int)(rect.getWidth()), (int)(rect.getHeight()));
        b2.setLocation(getWidth(), 0);
        b2.rotate(220);
        b2.increaseVelocity(4);
        bullets.add(b2);
        Bullet b3 = new Bullet((int)(rect.getWidth()), (int)(rect.getHeight()));
        b3.setLocation(0, getHeight());
        b3.rotate(40);
        b3.increaseVelocity(4);
        bullets.add(b3);
        Bullet b4 = new Bullet((int)(rect.getWidth()), (int)(rect.getHeight()));
        b4.setLocation(getWidth(), getHeight());
        b4.rotate(140);
        b4.increaseVelocity(4);
        bullets.add(b4);
        return bullets;
    }
    
}
