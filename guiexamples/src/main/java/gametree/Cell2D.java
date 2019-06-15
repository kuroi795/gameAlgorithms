package gametree;

import utils.Entity2D;

import static org.lwjgl.opengl.GL11.*;
import static utils.CellUtils.drawCell;
import static utils.CellUtils.inBoxBounds;


public class Cell2D
    extends ArenaTileImpl
    implements Entity2D
{

    private float x;
    private float y;
    private float size;
    private byte green, blue, red = green = blue = (byte)255;


    public Cell2D( float x, float y, float size )
    {
        super( null );
        this.x = x;
        this.y = y;
        this.size = size;
    }


    @Override
    public float getX()
    {
        return x;
    }


    @Override
    public float getY()
    {
        return y;
    }


    @Override
    public void setX( float x )
    {

        this.x = x;
    }


    @Override
    public void setY( float y )
    {

        this.y = y;
    }


    @Override
    public void setLocation( float x, float y )
    {

        this.x = x;
        this.y = y;
    }


    @Override
    public void setUp()
    {

    }


    @Override
    public void destroy()
    {

    }


    @Override
    public void draw()
    {
        if( unit != null && unit.getOwner().equals( new PlayerImpl( "Black" ) ) )
        {
            red = blue = green = 0;
        }
        else if( unit != null && unit.getOwner().equals( new PlayerImpl( "White" ) ) )
        {
            red = blue = green = (byte)255;
        }
        else
        {
            red = green = blue = (byte)128;
        }
        glColor3ub( red, green, blue );
        drawCell( x, y, size );
    }


    @Override
    public boolean inBounds( long mouseX, long mouseY )
    {
        return inBoxBounds( mouseX, mouseY, x, y, size );
    }

}
