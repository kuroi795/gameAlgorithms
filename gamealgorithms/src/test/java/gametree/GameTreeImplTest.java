package gametree;

import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Location;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GameTreeImplTest
{

    private GameTree gameTree;
    private BattleArena mockBattleArena;


    @BeforeEach
    void setUp()
    {
        ArenaTile[][] arenaTiles = { { new MockArenaTile(), new MockArenaTile(), new MockArenaTile() },
                                     { new MockArenaTile(), new MockArenaTile(), new MockArenaTile() },
                                     { new MockArenaTile(), new MockArenaTile(), new MockArenaTile() } };
        Player o = new MockPlayer( "O" );
        Player x = new MockPlayer( "X" );
        mockBattleArena = new MockBattleArenaImpl( arenaTiles, x, o );
        GameStateEvaluator mockGameStateEvaluator = new MockGameStateEvaluator( o, x );
        PlacementMovesProvider mockPlacementMovesProvider = new MockPlacementProvider();
        gameTree = new GameTreeImpl( mockBattleArena, mockGameStateEvaluator, 2, o,
            mockPlacementMovesProvider );
    }


    @Test
    void givenUpreparedTree_returnNoMovesMinmax()
    {
        assertEquals( Collections.emptyList(), gameTree.findMovesUsingMinMax() );
    }


    @Test
    void givenUpreparedTree_returnNoMovesAlphaBeta()
    {
        assertEquals( Collections.emptyList(), gameTree.findMovesUsingAlphaBeta() );
    }


    @Test
    void givenPreparedTree_assureThatGameIsProgressing_Minmax()
    {
        gameTree.prepareTree();
        List<Move> movesUsingMinMax = gameTree.findMovesUsingMinMax();
        assertEquals( 9, movesUsingMinMax.size() );
        Move move = movesUsingMinMax.get( 0 );
        gameTree.performMove( move, mockBattleArena.getCurrentPlayer() );
        mockBattleArena.performMove( move );
        assertEquals( 8, gameTree.findMovesUsingMinMax().size() );
    }


    @Test
    void givenPreparedTree_assureThatGameIsProgressing_AlphaBeta()
    {
        gameTree.prepareTree();
        List<Move> movesUsingMinMax = gameTree.findMovesUsingAlphaBeta();
        assertEquals( 9, movesUsingMinMax.size() );
        Move move = movesUsingMinMax.get( 0 );
        gameTree.performMove( move, mockBattleArena.getCurrentPlayer() );
        mockBattleArena.performMove( move );
        assertEquals( 8, gameTree.findMovesUsingAlphaBeta().size() );
    }


    private class MockBattleArenaImpl
        implements BattleArena
    {
        private final PlacementMovesProvider placement;
        private ArenaTile[][] map;
        private Player currentPlayer;
        private Player nextPlayer;


        public MockBattleArenaImpl( ArenaTile[][] map, Player currentPlayer, Player nextPlayer )
        {

            this.map = map;
            this.currentPlayer = currentPlayer;
            this.nextPlayer = nextPlayer;
            this.placement = new MockPlacementProvider();
        }


        @Override
        public BattleArena deepCopy()
        {
            return new MockBattleArenaImpl( copyOfState( map ), currentPlayer, nextPlayer );
        }


        @Override
        public ArenaTile[][] getArena()
        {
            return map;
        }


        @Override
        public Player getCurrentPlayer()
        {
            return currentPlayer;
        }


        @Override
        public Player getNextPlayer( Player current )
        {
            return currentPlayer.equals( current ) ? nextPlayer : currentPlayer;
        }


        @Override
        public void setArena( ArenaTile[][] arena )
        {
            map = arena;
        }


        @Override
        public void performMove( Move move )
        {
            Location to = move.getTo();

            map[to.getY()][to.getX()].setUnit( new UnitImpl( currentPlayer, currentPlayer.toString() ) );

            Player p = nextPlayer;
            nextPlayer = currentPlayer;
            currentPlayer = p;
        }


        private ArenaTile[][] copyOfState( ArenaTile[][] arena )
        {
            ArenaTile[][] a = new ArenaTile[arena.length][];
            for( int y = 0; y < arena.length; y++ )
            {
                a[y] = new ArenaTile[arena[y].length];
                for( int x = 0; x < a[y].length; x++ )
                {
                    a[y][x] = arena[y][x].deepCopy();
                }
            }
            return a;
        }
    }


    private class MockGameStateEvaluator
        implements GameStateEvaluator
    {

        private final Player p1;
        private final Player p2;


        MockGameStateEvaluator( Player p1, Player p2 )
        {
            this.p1 = p1;
            this.p2 = p2;
        }


        @Override
        public double evaluateArena( ArenaTile[][] arena )
        {
            int p1Score = 0, p2Score = 0;
            for( ArenaTile[] arenaTiles : arena )
            {
                for( ArenaTile tile : arenaTiles )
                {
                    if( tile.getUnit() != null && tile.getUnit().getOwner().equals( p1 ) )
                    {
                        p1Score += 1;
                    }
                    else if( tile.getUnit() != null && tile.getUnit().getOwner().equals( p2 ) )
                    {
                        p2Score += 1;
                    }
                }
            }
            return p1Score - p2Score;
        }
    }


    @EqualsAndHashCode
    private class MockPlayer
        extends Player
    {
        private String name;


        private MockPlayer( String name )
        {
            this.name = name;
        }
    }


    private class MockPlacementProvider
        implements PlacementMovesProvider
    {

        @Override
        public List<Location> getPossiblePlacementMoves( ArenaTile[][] arena, int x, int y, Player player )
        {

            return arena[y][x].getUnit() == null ? Collections.singletonList( new Location( x, y ) ) :
                Collections.emptyList();
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    private class MockArenaTile
        implements ArenaTile
    {
        protected Unit unit;


        public MockArenaTile( Unit unit )
        {
            this.unit = unit;
        }


        @Override
        public ArenaTile deepCopy()
        {
            return new MockArenaTile( unit != null ? unit.deepCopy() : null );
        }


        @Override
        public boolean equals( Object o )
        {
            if( this == o )
                return true;
            if( o == null || !getClass().isAssignableFrom( o.getClass() ) )
            {
                return false;
            }
            MockArenaTile arenaTile = (MockArenaTile)o;
            return Objects.equals( unit, arenaTile.unit );
        }


        @Override
        public int hashCode()
        {
            return Objects.hash( unit );
        }
    }


    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    private class UnitImpl
        extends Unit
    {

        private Player owner;
        private String name;


        @Override
        public List<Location> getPossibleMoves( ArenaTile[][] battleArena, Location unitLocation )
        {
            return Collections.emptyList();
        }


        @Override
        public Player getOwner()
        {
            return owner;
        }


        @Override
        public Unit deepCopy()
        {
            return new UnitImpl( owner, name );
        }
    }
}
