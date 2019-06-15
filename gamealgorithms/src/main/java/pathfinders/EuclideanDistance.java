package pathfinders;

import util.Location;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;


public class EuclideanDistance
    implements Heuristic
{
    @Override
    public double evaluate( Location goal, Location next )
    {
        if( goal.getY() < 0 || goal.getX() < 0 || next.getX() < 0 || next.getY() < 0 )
            return -1;

        double dx = abs( next.getX() - goal.getX() );
        double dy = abs( next.getY() - goal.getY() );
        return sqrt( dx * dx + dy * dy );
    }
}
