package pathfinders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Location;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AStarPathfinderTest
    extends GridPathfinderTest
{
    private final List<Location> blockedPathResponse =
        Arrays.asList( new Location( 0, 0 ), new Location( 1, 0 ) );
    private final List<Location> diagonalPathWithObstacleResponse =
        Arrays.asList( new Location( 0, 0 ), new Location( 1, 1 ),
            new Location( 2, 1 ), new Location( 3, 2 ), new Location( 4, 3 ),
            new Location( 4, 4 ) );


    @BeforeEach
    void setUp()
    {
        pathfinder = new AStarPathfinder( new EuclideanDistance() );
    }


    @Test
    void givenStrightPath_returnStrightAnswer()
    {
        assertEquals(
            strightPathAnswer,
            pathfinder.findPath( strightPath, 0, 0, 4, 0, false ) );
    }


    @Test
    void givenBlockedPath_returnPathLeadingToBlock()
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

}
