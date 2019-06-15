package pathfinders;

import util.Location;


public interface Heuristic
{
    double evaluate( Location goal, Location next );
}
