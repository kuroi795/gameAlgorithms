package pathfinders;

import java.util.List;


public interface GraphPathfinder
{
    List<Node> findPath( Node startPoint, Node goal );
}
