//Arthur Micha
import acm.graphics.*;
import java.awt.Color;

public class Asteroid extends GVectorPolygon
{
    private double rotation;

    public Asteroid(int width, int height)
    {
       super(width, height);
       addVertex(8, 17);
       addVertex(10, 20);
       addVertex(13, 14);
       addVertex(16, 19);
       addVertex(18, 13);
       addVertex(20, 17);
       addVertex(22, 15);
       addVertex(25, 20);
       addVertex(21, 23);
       addVertex(23, 27);
       addVertex(26, 29);
       addVertex(24, 31);
       addVertex(27, 33);
       addVertex(25, 35);
       addVertex(28, 37);
       addVertex(25, 40);
       addVertex(23, 36);
       addVertex(21, 41);
       addVertex(18, 37);
       addVertex(15, 39);
       addVertex(13, 35);
       addVertex(9, 40);
       addVertex(6, 38);
       addVertex(9, 36);
       addVertex(6, 33);
       addVertex(8, 29);
       addVertex(11, 30);
       addVertex(11, 27);
       addVertex(8, 26);
       addVertex(6, 24);
       
       scale(4);
       setColor(Color.white);
       recenter();
       rotation = Math.random();
    }

    public void updatePosition()
    {
        super.updatePosition();
        rotate(rotation);
    }
}
