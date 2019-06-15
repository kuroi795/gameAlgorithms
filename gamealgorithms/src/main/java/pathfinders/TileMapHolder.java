package pathfinders;

import util.Location;

import java.util.List;


class TileMapHolder
    extends MapHolder
{
    private final Tile[][] map;


    TileMapHolder( Tile[][] map, boolean diagonalMoves )
    {
        super( diagonalMoves );
        this.map = map;
    }


    @Override
    double getCostOfMove( Location nextLocation )
    {
        double value = map[nextLocation.getY()][nextLocation.getX()].getRelativeSpeed();
        return evaluateCost( value );
    }


    @Override
    List<Location> getPossibleMoves( Location currentLocation )
    {
        return getPossibleMoves( map.length, map[currentLocation.getY()].length, currentLocation );
    }
}
