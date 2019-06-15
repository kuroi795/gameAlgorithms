package utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;


public class DisplayUtils
{
    public static void setupDisplay( int width, int height )
    {
        try
        {
            Display.setDisplayMode( new DisplayMode( width, height ) );
            Display.setTitle( "Game library tests!" );
            Display.create();
        }
        catch( LWJGLException e )
        {
            e.printStackTrace();
            Display.destroy();
            System.exit( 1 );
        }

        glMatrixMode( GL_PROJECTION );
        glLoadIdentity();
        glOrtho( 0, width, height, 0, 1, -1 );
        glMatrixMode( GL_MODELVIEW );
    }
}
