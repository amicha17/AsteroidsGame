import java.awt.Color;
import acm.graphics.*;
import java.util.*;

public class AllyShip extends GVectorPolygon
{
    public AllyShip(int w, int h)
    {
        super(w, h);
        addVertex(4, 4);
        addVertex(14, 8);
        addVertex(4, 10);
        addVertex(8, 8);
        scale(4);
        setColor(Color.cyan);
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
    }
