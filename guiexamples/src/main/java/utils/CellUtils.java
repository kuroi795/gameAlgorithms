package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static org.lwjgl.opengl.GL11.*;


public class CellUtils
{
    public static boolean inBoxBounds( long mouseX, long mouseY, float x, float y, float size )
    {
        return mouseX > (x - size / 2) &&
            mouseX < (x + size / 2) &&
            mouseY > (y - size / 2) &&
            mouseY < (y + size / 2);
    }


    public static void drawCell( float x, float y, float size )
    {
        glBegin( GL_TRIANGLES );
        glVertex2f( x + size / 2, y + size / 2 );
        glVertex2f( x + size / 2, y - size / 2 );
        glVertex2f( x - size / 2, y - size / 2 );
        glVertex2f( x - size / 2, y - size / 2 );
        glVertex2f( x - size / 2, y + size / 2 );
        glVertex2f( x + size / 2, y + size / 2 );
        glEnd();
    }


    public static <T> List<T> populateCells( T[][] cell2DS, BiFunction<Integer, Integer, T> function )
    {
        List<T> list = new ArrayList<>();
        for( int y = 0; y < cell2DS.length; y++ )
        {
            for( int x = 0; x < cell2DS[y].length; x++ )
            {
                cell2DS[y][x] = function.apply( x, y );
                list.add( cell2DS[y][x] );
            }
        }
        return list;
    }
}
