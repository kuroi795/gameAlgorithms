package pathfinders;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Location;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DijkstraPathfinderTest
    extends GridPathfinderTest
{

    private final List<Location> blockedPathResponse =
        Collections.singletonList( new Location( -1, -1 ) );
    private final List<Location> diagonalPathWithObstacleResponse =
        Arrays.asList( new Location( 0, 0 ), new Location( 1, 1 ), new Location( 1, 2 ),
            new Location( 2, 3 ), new Location( 3, 4 ), new Location( 4, 4 ) );
    private List<NodeImpl> graphRespose =
        Arrays.asList( new NodeImpl( 1 ), new NodeImpl( 2 ), new NodeImpl( 3 ), new NodeImpl( 4 ) );


    @BeforeEach
    void setUp()
    {
        pathfinder = new DijkstraPathfinder();
    }


    @Test
    void givenStrighPath_returnStrightPathAnswer()
    {
        assertEquals(
            strightPathAnswer,
            pathfinder.findPath( strightPath, 0, 0, 4, 0, false ) );
    }


    @Test
    void givenBlockedPath_returnListContainingOnlyBlockedLocation()
    {
        assertEquals(
            blockedPathResponse,
            pathfinder.findPath( blockedTilePath, 0, 0, 4, 0, true ) );
    }


    @Test
    void givenDiagonalPathWithObstacles_returnShortestPath()
    {
        assertEquals(
            diagonalPathWithObstacleResponse,
            pathfinder.findPath( new DoubleMapHolder( diagonalPathWithObstacle, true ), 0, 0, 4, 4 ) );
    }


    @Test
    void givenGraphBasedPath_returnListOfGraphNodesWithShortestPath()
    {
        NodeImpl start = new NodeImpl( 1 );
        NodeImpl end = new NodeImpl( 4 );
        NodeMapBuilder nmp = new NodeMapBuilder( start )
            .withNeighbourNode( new NodeImpl( -1 ), 1 )
            .withNeighbourNode( new NodeImpl( -1 ), 1 )
            .withNeighbourAndPass( new NodeImpl( 2 ), 1 )
            .withNeighbourAndPass( new NodeImpl( 3 ), 1 )
            .withNeighbourNode( new NodeImpl( -1 ), 1 )
            .withNeighbourAndPass( end, 1 );

        assertEquals(
            graphRespose,
            ((GraphPathfinder)pathfinder).findPath( start, end ) );
    }


    private class NodeMapBuilder
    {
        private Node current;


        private NodeMapBuilder( NodeImpl start )
        {
            this.current = start;
        }


        Node build()
        {
            return current;
        }


        NodeMapBuilder withNeighbourNode( Node node, double distance )
        {
            current.addNeughbour( node, distance );
            node.addNeughbour( current, distance );
            return this;
        }


        NodeMapBuilder withNeighbourAndPass( Node node, double distance )
        {
            withNeighbourNode( node, distance );
            current = node;
            return this;
        }
    }


    @ToString
    @EqualsAndHashCode
    private class NodeImpl
        implements Node
    {
        private int c;

        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        Map<Node, Double> neighbours = new HashMap<>();


        NodeImpl( int c )
        {
            this.c = c;
        }


        @Override
        public Map<Node, Double> getNeighbours()
        {
            return neighbours;
        }


        @Override
        public void addNeughbour( Node neighbour, double distance )
        {
            neighbours.put( neighbour, distance );
        }
    }
}
