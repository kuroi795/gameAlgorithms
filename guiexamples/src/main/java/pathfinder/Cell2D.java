package pathfinder;

import pathfinders.Tile;
import utils.Entity2D;

import static org.lwjgl.opengl.GL11.glColor3ub;
import static utils.CellUtils.drawCell;
import static utils.CellUtils.inBoxBounds;


public class Cell2D
    implements Entity2D, Tile
{
    private byte green, blue, red = green = blue = (byte)255;
    protected float size, x, y;
    private double relativeSpeed;


    public Cell2D( float x, float y, float size, double relativeSpeed )
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.relativeSpeed = relativeSpeed;
    }


    public void setColor( byte red, byte green, byte blue )
    {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }


    public float getX()
    {
        return x;
    }


    public float getY()
    {
        return y;
    }


    public void setX( float x )
    {
        this.x = x;
    }


    public void setY( float y )
    {
        this.y = y;
    }


    public void setLocation( float x, float y )
    {
        this.x = x;
        this.y = y;
    }


    public void setUp()
    {

    }


    public void destroy()
    {

    }


    public void draw()
    {
        if( relativeSpeed == -1 )
        {
            glColor3ub( (byte)128, (byte)128, (byte)128 );
        }
        else
        {
            glColor3ub( red, green, blue );
        }

        drawCell( x, y, size );
    }


    public double getRelativeSpeed()
    {
        return relativeSpeed;
    }


    public void revertSpeed()
    {
        relativeSpeed = relativeSpeed == 1 ? -1 : 1;
    }


    @Override
    public boolean inBounds( long mouseX, long mouseY )
    {
        return inBoxBounds( mouseX, mouseY, x, y, size );
    }
}
