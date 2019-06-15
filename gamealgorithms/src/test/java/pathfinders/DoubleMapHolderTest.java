package pathfinders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Location;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DoubleMapHolderTest
{
    private MapHolder doubleMapHolder;


    @BeforeEach
    void setUp()
    {
        double[][] map = {
            { 1, 0.5, 1 },
            { 1, 0.75, -1 },
            { -1, 0.8, 1 },
            { 1, 1, 0 },
            { -1, -1, 1 }
        };

        doubleMapHolder = new DoubleMapHolder( map, true );
    }


    @Test
    void costOfMove_givenLocation_returnItsCost()
    {
        double costOfMove = doubleMapHolder.getCostOfMove( new Location( 0, 0 ) );
        double costOfMove2 = doubleMapHolder.getCostOfMove( new Location( 1, 2 ) );
        double costOfMove3 = doubleMapHolder.getCostOfMove( new Location( 2, 3 ) );
        assertEquals( 1, costOfMove );
        assertEquals( 1.25, costOfMove2 );
        assertEquals( 0, costOfMove3 );
    }


    @Test
    void getPossibleMoves_givenCorner_return3()
    {
        List<Location> possibleMoves = doubleMapHolder.getPossibleMoves( new Location( 0, 0 ) );
        List<Location> possibleMoves2 = doubleMapHolder.getPossibleMoves( new Location( 2, 4 ) );
        assertEquals( 3, possibleMoves.size() );
        assertEquals( 3, possibleMoves2.size() );
    }


    @Test
    void getPossibleMoves_givenEdge_return5()
    {
        List<Location> possibleMoves = doubleMapHolder.getPossibleMoves( new Location( 2, 2 ) );
        List<Location> possibleMoves2 = doubleMapHolder.getPossibleMoves( new Location( 1, 0 ) );
        assertEquals( 5, possibleMoves.size() );
        assertEquals( 5, possibleMoves2.size() );
    }
}
