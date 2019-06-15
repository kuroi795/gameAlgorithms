package pathfinders;

import gametree.Move;
import org.junit.jupiter.api.Test;
import util.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RatedMoveTest
{
    @Test
    void getMove_givenMove_returnMove()
    {
        Move move = new Move( null, new Location( 5, 5 ), new Location( 3, 3 ) );
        RatedMove<Move> ratedMove = new RatedMove<>( 10, move );
        assertEquals( move, ratedMove.getMove() );
    }


    @Test
    void compareTo_givenSameRating_returnZero()
    {
        RatedMove<Move> ratedMove = new RatedMove<>( 10, null );
        RatedMove<Move> ratedMove2 = new RatedMove<>( 10, null );
        assertEquals( 0, ratedMove.compareTo( ratedMove2 ) );
    }
}
