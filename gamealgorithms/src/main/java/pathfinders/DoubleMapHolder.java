package pathfinders;

import util.Location;

import java.util.List;


class DoubleMapHolder
    extends MapHolder
{

    private double[][] map;


    DoubleMapHolder( double[][] map, boolean diagonalMoves )
    {
        super( diagonalMoves );
        this.map = map;
    }


    @Override
    public double getCostOfMove( Location nextLocation )
    {
        double value = map[nextLocation.getY()][nextLocation.getX()];
        return evaluateCost( value );
    }


    @Override
    List<Location> getPossibleMoves( Location currentLocation )
    {
        return getPossibleMoves( map.length, map[currentLocation.getY()].length, currentLocation );
    }

}
