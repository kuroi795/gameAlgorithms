package utils;

public interface Entity2D
{
    float getX();

    float getY();

    void setX( float x );

    void setY( float y );

    void setLocation( float x, float y );

    void setUp();

    void destroy();

    void draw();

    boolean inBounds( long mouseX, long mouseY );
}
