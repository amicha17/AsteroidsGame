import acm.graphics.*;

public class GVectorPolygon extends GPolygon
{
    private double vx, vy;
    public double theta;
    private double maxX, maxY; // dimensions of the "window" to enable "wrapdoping around"

    private static final double MAXVELOCITY = 3;

    public GVectorPolygon(int width, int height)
    {
        vx = 0;
        vy = 0;
        theta = 0;
        maxY = height;
        maxX = width;
    }

    public void increaseVelocity(double numPixels)
    {
        double thetaInRadians = theta*Math.PI/180;
        
        vx += numPixels * Math.cos(thetaInRadians);
        vy -= numPixels * Math.sin(thetaInRadians);
        
        double magnitude = Math.sqrt(vx*vx + vy*vy);
        if (magnitude>MAXVELOCITY)
        {
            vx = MAXVELOCITY *Math.cos(thetaInRadians);
            vy = -MAXVELOCITY *Math.sin(thetaInRadians);
        }
    }

    public void updatePosition()
    {
        move(vx, vy);
        if (getX()>maxX)
        {
            setLocation(0, getY());
        }
        else if (getX()<0)
        {
            setLocation(maxX, getY());
        }
        
        if (getY()>maxY)
        {
            setLocation(getX(), 0);
        }
        else if (getY()<0)
        {
            setLocation(getX(), maxY);
        }
    }

    public void rotate(double angle)
    {
        super.rotate(angle);
        theta += angle;
    }

    public double getTheta()
    {
        return theta;
    }

    public double getVelocityX()
    {
        return vx;
    }

    public double getVelocityY()
    {
        return vy;
    }

    public GRectangle getBoundingRectangle()
    {
        return new GRectangle(0,0,maxX, maxY);
    }
}
