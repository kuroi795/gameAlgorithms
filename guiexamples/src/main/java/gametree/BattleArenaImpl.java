package gametree;

import util.Location;

import java.util.function.BiFunction;
import java.util.function.Function;


public class BattleArenaImpl
    implements BattleArena
{
    private final PlacementMovesProviderImpl placement;
    private ArenaTile[][] map;
    private Player currentPlayer;
    private Player nextPlayer;


    public BattleArenaImpl( ArenaTile[][] map, Player currentPlayer, Player nextPlayer )
    {

        this.map = map;
        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;
        this.placement = new PlacementMovesProviderImpl();
    }


    @Override
    public BattleArena deepCopy()
    {
        return new BattleArenaImpl( copyOfState( map ), currentPlayer, nextPlayer );
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
        Location from = move.getFrom();
        Location to = move.getTo();
        Unit unit = move.getUnit();
        if( unit != null && from != null )
        {
            ArenaTile arenaTile = map[from.getY()][from.getX()];
            if( arenaTile.getUnit() != null && arenaTile.getUnit().equals( unit ) )
            {
                arenaTile.setUnit( null );
                map[to.getY()][to.getX()].setUnit( unit );
            }
        }
        else
        {
            map[to.getY()][to.getX()].setUnit( (unit = new UnitImpl( currentPlayer, currentPlayer.toString() )) );
        }

        flipSigns( to, unit );

        Player p = nextPlayer;
        nextPlayer = currentPlayer;
        currentPlayer = p;
    }


    private void flipSigns( Location to, Unit unit )
    {
        boolean up = placement.checkUp( map, to.getX(), to.getY(), unit.getOwner() );
        boolean down = placement.checkDown( map, to.getX(), to.getY(), unit.getOwner() );
        boolean left = placement.checkLeft( map, to.getX(), to.getY(), unit.getOwner() );
        boolean right = placement.checkRight( map, to.getX(), to.getY(), unit.getOwner() );
        boolean upLeft = placement.checkLeftUp( map, to.getX(), to.getY(), unit.getOwner() );
        boolean downLeft = placement.checkLeftDown( map, to.getX(), to.getY(), unit.getOwner() );
        boolean upRight = placement.checkRightUp( map, to.getX(), to.getY(), unit.getOwner() );
        boolean downRight = placement.checkRightDown( map, to.getX(), to.getY(), unit.getOwner() );

        flip( to.getX(), to.getY() - 1, e -> e, a -> --a, ( x, y ) -> y >= 0, up );
        flip( to.getX(), to.getY() + 1, e -> e, a -> ++a, ( x, y ) -> y < map.length, down );
        flip( to.getX() - 1, to.getY(), e -> --e, a -> a, ( x, y ) -> x >= 0, left );
        flip( to.getX() + 1, to.getY(), e -> ++e, a -> a, ( x, y ) -> x < map[y].length, right );
        flip( to.getX() - 1, to.getY() - 1, e -> --e, a -> --a, ( x, y ) -> x >= 0 && y >= 0, upLeft );
        flip( to.getX() - 1, to.getY() + 1, e -> --e, a -> ++a, ( x, y ) -> x >= 0 && y < map.length, downLeft );
        flip( to.getX() + 1, to.getY() - 1, e -> ++e, a -> --a, ( x, y ) -> y >= 0 && x < map[y].length, upRight );
        flip(
            to.getX() + 1, to.getY() + 1, e -> ++e, a -> ++a, ( x, y ) -> y < map.length && x < map[y].length,
            downRight );
    }


    private void flip(
        int startingX, int startingY, Function<Integer, Integer> funx, Function<Integer, Integer> funy,
        BiFunction<Integer, Integer, Boolean> stop, boolean starter )
    {
        for( int x = startingX, y = startingY; starter && stop.apply( x, y ); x = funx.apply( x ), y = funy.apply( y ) )
        {
            UnitImpl unit1 = new UnitImpl( currentPlayer, currentPlayer.toString() );
            if( map[y][x].getUnit() == null || map[y][x].getUnit().equals( unit1 ) )
            {
                starter = false;
            }
            map[y][x].setUnit( unit1 );
        }
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
