package pathfinders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ManhattanDistanceTest
{
    private ManhattanDistance manhattanDistance;


    @BeforeEach
    void setUp()
    {
        manhattanDistance = new ManhattanDistance();
    }


    @Test
    void evaluate_givenSameLocation_returnZero()
    {
        Location location = new Location( 3, 5 );
        double evaluation = manhattanDistance.evaluate( location, location );
        assertEquals( 0, evaluation );
    }


    @Test
    void evaluate_givenIncorrectLocation_returnMinusOne()
    {
        Location source = new Location( 3, 5 );
        Location goal = new Location( -1, -5 );
        double evaluation = manhattanDistance.evaluate( source, goal );
        assertEquals( -1, evaluation );
    }


    @Test
    void evaluate_givenProperLocations_returnDistance()
    {
        Location source = new Location( 3, 5 );
        Location goal = new Location( 5, 3 );
        double evaluation = manhattanDistance.evaluate( source, goal );
        assertEquals( 4.0, evaluation, 0.001 );
    }
}
