package pathfinders;

import util.Location;

import java.util.ArrayList;
import java.util.List;


abstract class MapHolder
{
    private boolean diagonalMoves;


    MapHolder( boolean diagonalMoves )
    {
        this.diagonalMoves = diagonalMoves;
    }


    abstract double getCostOfMove( Location nextLocation );

    abstract List<Location> getPossibleMoves( Location currentLocation );


    List<Location> getPossibleMoves( int rowsCount, int columnCount, Location currentLocation )
    {
        int x = currentLocation.getX();
        int y = currentLocation.getY();
        List<Location> locations = new ArrayList<>();
        if( x > 0 )
        {
            locations.add( new Location( x - 1, y ) );
        }
        if( x < columnCount - 1 )
        {
            locations.add( new Location( x + 1, y ) );
        }
        if( y > 0 )
        {
            locations.add( new Location( x, y - 1 ) );
        }
        if( y < rowsCount - 1 )
        {
            locations.add( new Location( x, y + 1 ) );
        }

        if( diagonalMoves )
        {
            if( x > 0 && y > 0 )
            {
                locations.add( new Location( x - 1, y - 1 ) );
            }
            if( x > 0 && y < rowsCount - 1 )
            {
                locations.add( new Location( x - 1, y + 1 ) );
            }
            if( x < columnCount - 1 && y < rowsCount - 1 )
            {
                locations.add( new Location( x + 1, y + 1 ) );
            }

            if( x < columnCount - 1 && y > 0 )
            {
                locations.add( new Location( x + 1, y - 1 ) );
            }
        }
        return locations;
    }


    double evaluateCost( double value )
    {
        if( value == 0 )
        {
            return 0d;
        }
        else
            return (1d / value) < 0 ? -1 : 1d / value;
    }
}
