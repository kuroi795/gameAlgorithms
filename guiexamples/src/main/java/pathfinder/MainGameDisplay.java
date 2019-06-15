package pathfinder;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import pathfinders.AStarPathfinder;
import pathfinders.GridPathfinder;
import pathfinders.ManhattanDistance;
import util.Location;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static utils.DisplayUtils.setupDisplay;


public class MainGameDisplay
{

    private static final int size = 50;
    private static final Location BLOCKED_PATH = new Location( -1, -1 );
    private static int width;
    private static int height;
    private static boolean keyPressed = false, pathChanged = false;
    private static List<Location> path1 = new ArrayList<>();


    public static void main( String[] args )
    {
        width = 510;
        height = 510;

        setupDisplay( width, height );

        CellFactory cf = new CellFactory( size );
        List<Cell2D> cells = new ArrayList<>();
        Cell2D[][] cell2DS = cf.populateCells( cells, 10, 10 );
        GridPathfinder aStar = new AStarPathfinder( new ManhattanDistance() );
        Cell2D goal = null, start = cells.get( 0 );
        cells.forEach( Cell2D::setUp );
        while( !Display.isCloseRequested() )
        {
            glClear( GL_COLOR_BUFFER_BIT );

            cells.forEach( e -> e.setColor( (byte)255, (byte)255, (byte)255 ) );

            if( Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && Mouse.isButtonDown( 0 ) && !keyPressed )
            {
                keyPressed = true;
                for( Cell2D cell : cells )
                {
                    if( cell.inBounds( Mouse.getX(), height - Mouse.getY() ) )
                    {
                        cell.revertSpeed();
                    }
                }
                pathChanged = goal != null && start != null;
            }

            if( !Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && Mouse.isButtonDown( 0 ) && !keyPressed )
            {
                keyPressed = true;
                goal = updateGoal( cells, goal );
                pathChanged = goal != null && start != null;
            }

            if( !Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && Mouse.isButtonDown( 1 ) && !keyPressed )
            {
                keyPressed = true;
                start = updateStart( cells, start );
                pathChanged = goal != null && start != null;
            }

            if( pathChanged )
            {
                path1 = getPath( cell2DS, aStar, goal, start );

                pathChanged = false;
            }

            colorPath( cell2DS, path1 );

            if( !Mouse.isButtonDown( 0 ) )
            {
                keyPressed = false;
            }

            drawCells( cells, goal, start );
            Display.update();
            Display.sync( 60 );
        }
        cells.forEach( Cell2D::destroy );
        Display.destroy();
        System.exit( 0 );
    }


    private static void drawCells( List<Cell2D> cells, Cell2D goal, Cell2D start )
    {
        for( Cell2D cell : cells )
        {
            if( cell.equals( start ) )
            {
                cell.setColor( (byte)0, (byte)255, (byte)0 );
            }
            else if( cell.equals( goal ) )
            {
                cell.setColor( (byte)255, (byte)0, (byte)0 );
            }
            cell.draw();
        }
    }


    private static Cell2D updateStart( List<Cell2D> cells, Cell2D start )
    {
        return getCell( cells, start );
    }


    private static Cell2D getCell( List<Cell2D> cells, Cell2D point )
    {
        for( Cell2D cell : cells )
        {
            if( cell.inBounds( Mouse.getX(), height - Mouse.getY() ) )
            {
                point = cell;
            }
        }
        return point;
    }


    private static Cell2D updateGoal( List<Cell2D> cells, Cell2D goal )
    {
        return getCell( cells, goal );
    }


    private static List<Location> getPath( Cell2D[][] cell2DS, GridPathfinder aStar, Cell2D goal, Cell2D start )
    {
        return aStar.findPath( cell2DS, new Location( (int)(start.getX() / size), (int)(start.getY() / size) ),
            new Location( (int)(goal.getX() / size), (int)(goal.getY() / size) ), false );
    }


    private static void colorPath( Cell2D[][] cell2DS, List<Location> path1 )
    {
        for( Location location : path1 )
        {
            if( !location.equals( BLOCKED_PATH ) )
            {
                Cell2D e = cell2DS[location.getY()][location.getX()];
                e.setColor( (byte)0, (byte)0, (byte)255 );
            }
        }
    }
}
