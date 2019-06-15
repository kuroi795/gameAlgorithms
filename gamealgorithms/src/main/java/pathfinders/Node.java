package pathfinders;

import java.util.Map;


public interface Node
{
    Map<Node, Double> getNeighbours();

    void addNeughbour( Node neighbour, double distance );

    boolean equals( Object o );

    int hashCode();
}
