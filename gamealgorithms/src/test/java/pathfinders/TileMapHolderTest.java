package pathfinders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Location;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TileMapHolderTest
{
    private MapHolder tileMapHolder;


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

        Tile[][] tiles = prepareTileTable( map );

        tileMapHolder = new TileMapHolder( tiles, true );
    }


    @Test
    void costOfMove_givenLocation_returnItsCost()
    {
        double costOfMove = tileMapHolder.getCostOfMove( new Location( 0, 0 ) );
        double costOfMove2 = tileMapHolder.getCostOfMove( new Location( 1, 2 ) );
        double costOfMove3 = tileMapHolder.getCostOfMove( new Location( 2, 3 ) );
        assertEquals( 1, costOfMove );
        assertEquals( 1.25, costOfMove2 );
        assertEquals( 0, costOfMove3 );
    }


    @Test
    void getPossibleMoves_givenCorner_return3()
    {
        List<Location> possibleMoves = tileMapHolder.getPossibleMoves( new Location( 0, 0 ) );
        List<Location> possibleMoves2 = tileMapHolder.getPossibleMoves( new Location( 2, 4 ) );
        assertEquals( 3, possibleMoves.size() );
        assertEquals( 3, possibleMoves2.size() );
    }


    @Test
    void getPossibleMoves_givenEdge_return5()
    {
        List<Location> possibleMoves = tileMapHolder.getPossibleMoves( new Location( 2, 2 ) );
        List<Location> possibleMoves2 = tileMapHolder.getPossibleMoves( new Location( 1, 0 ) );
        assertEquals( 5, possibleMoves.size() );
        assertEquals( 5, possibleMoves2.size() );
    }


    @Test
    void getPossibleMoves_givenMiddle_return8()
    {
        List<Location> possibleMoves = tileMapHolder.getPossibleMoves( new Location( 1, 2 ) );
        List<Location> possibleMoves2 = tileMapHolder.getPossibleMoves( new Location( 1, 1 ) );
        assertEquals( 8, possibleMoves.size() );
        assertEquals( 8, possibleMoves2.size() );
    }


    private Tile[][] prepareTileTable( double[][] map )
    {
        Tile[][] tiles = new Tile[map.length][];
        for( int y = 0; y < map.length; y++ )
        {
            tiles[y] = new Tile[map[y].length];
            for( int x = 0; x < map[y].length; x++ )
            {
                int finalX = x;
                int finalY = y;
                tiles[y][x] = () -> map[finalY][finalX];
            }
        }
        return tiles;
    }
}
