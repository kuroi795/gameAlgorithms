package pathfinders;

import util.Location;


public class ManhattanDistance
    implements Heuristic
{
    @Override
    public double evaluate( Location goal, Location next )
    {
        if( goal.getY() < 0 || goal.getX() < 0 || next.getX() < 0 || next.getY() < 0 )
        {
            return -1;
        }
        return Math.abs( goal.getX() - next.getX() ) + Math.abs( goal.getY() - next.getY() );
    }
}
