import java.awt.Color;

public class Bullet extends GVectorPolygon
{
    private int numTurns;
    
    private static final int MAXTURNS=200;
    
    public Bullet(int width, int height)
    {
       super(width, height);
       addVertex(0,0);
       addVertex(0,2);
       addVertex(2,2);
       addVertex(2,0);
       setColor(Color.white);
       recenter();
    }
    
    public boolean stillMoving()
    {
        return numTurns<MAXTURNS;
    }
    
    public void updatePosition()
    {
        super.updatePosition();
        numTurns++;
    }


}
