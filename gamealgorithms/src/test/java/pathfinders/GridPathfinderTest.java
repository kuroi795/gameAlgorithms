package pathfinders;

import lombok.AllArgsConstructor;
import util.Location;

import java.util.Arrays;
import java.util.List;


public class GridPathfinderTest
{
    final double[][] strightPath = { { 1, 1, 1, 1, 1 } };
    final List<Location> strightPathAnswer =
        Arrays.asList( new Location( 0, 0 ), new Location( 1, 0 ), new Location( 2, 0 ),
            new Location( 3, 0 ), new Location( 4, 0 ) );
    final Tile[][] blockedTilePath =
        { { new MockTile( 1 ), new MockTile( 1 ), new MockTile( -1 ),
            new MockTile( 1 ), new MockTile( 1 ), } };
    final List<Location> blockedPathResponse =
        Arrays.asList( new Location( 0, 0 ), new Location( 1, 0 ) );
    final double[][] diagonalPathWithObstacle =
        {
            { 1, 1, 1, 1, 1 },
            { 1, 1, 1, -1, 1 },
            { 1, 1, -1, 1, 1 },
            { 1, -1, 1, 1, 1 },
            { 1, 1, 1, 1, 1 },
            };

    GridPathfinder pathfinder;


    @AllArgsConstructor
    private class MockTile
        implements Tile
    {

        private double speed;


        @Override
        public double getRelativeSpeed()
        {
            return speed;
        }
    }
}
