package gametree;

import util.Location;

import java.util.Collections;
import java.util.List;


@SuppressWarnings( "Duplicates" )
public class PlacementMovesProviderImpl
    implements PlacementMovesProvider
{
    @Override
    public List<Location> getPossiblePlacementMoves( ArenaTile[][] arena, int x, int y, Player player )
    {
        if( arena[y][x].getUnit() != null )
        {
            return Collections.emptyList();
        }
        if( checkDown( arena, x, y, player )
            || checkUp( arena, x, y, player )
            || checkLeft( arena, x, y, player )
            || checkRight( arena, x, y, player )
            || checkLeftUp( arena, x, y, player )
            || checkLeftDown( arena, x, y, player )
            || checkRightUp( arena, x, y, player )
            || checkRightDown( arena, x, y, player ) )
        {
            return Collections.singletonList( new Location( x, y ) );
        }

        return Collections.emptyList();
    }


    boolean checkLeft( ArenaTile[][] arena, int x, int y, Player player )
    {
        boolean containing = false;
        for( int i = x - 1; i >= 0; i-- )
        {
            if( isEmptySquare( arena[y][i] ) )
            {
                return false;
            }
            if( i == x - 1 && !arena[y][i].getUnit().getOwner().equals( player ) )
            {
                containing = true;
            }

            if( isOwnPiece( player, containing, arena[y][i] ) )
            {
                return true;
            }
        }
        return false;
    }


    boolean checkRight( ArenaTile[][] arena, int x, int y, Player player )
    {
        boolean containing = false;
        for( int i = x + 1; i < arena[y].length; i++ )
        {
            if( isEmptySquare( arena[y][i] ) )
            {
                return false;
            }
            if( i == x + 1 && !arena[y][i].getUnit().getOwner().equals( player ) )
            {
                containing = true;
            }

            if( isOwnPiece( player, containing, arena[y][i] ) )
            {
                return true;
            }
        }
        return false;
    }


    boolean checkDown( ArenaTile[][] arena, int x, int y, Player player )
    {
        boolean containing = false;
        for( int i = y + 1; i < arena.length; i++ )
        {
            if( isEmptySquare( arena[i][x] ) )
            {
                return false;
            }
            if( i == y + 1 && !arena[i][x].getUnit().getOwner().equals( player ) )
            {
                containing = true;
            }

            if( isOwnPiece( player, containing, arena[i][x] ) )
            {
                return true;
            }
        }
        return false;
    }


    private boolean isOwnPiece( Player player, boolean containing, ArenaTile arenaTile )
    {
        return containing && arenaTile.getUnit().getOwner().equals( player );
    }


    boolean checkUp( ArenaTile[][] arena, int x, int y, Player player )
    {
        boolean containing = false;
        for( int i = y - 1; i >= 0; i-- )
        {
            if( isEmptySquare( arena[i][x] ) )
            {
                return false;
            }
            if( i == y - 1 && !arena[i][x].getUnit().getOwner().equals( player ) )
            {
                containing = true;
            }

            if( isOwnPiece( player, containing, arena[i][x] ) )
            {
                return true;
            }
        }
        return false;
    }


    boolean checkLeftUp( ArenaTile[][] arena, int x, int y, Player player )
    {
        boolean containing = false;
        for( int i = y - 1, j = x - 1; i >= 0 && j >= 0; i--, j-- )
        {
            if( isEmptySquare( arena[i][j] ) )
            {
                return false;
            }
            if( i == y - 1 && !arena[i][j].getUnit().getOwner().equals( player ) )
            {
                containing = true;
            }

            if( isOwnPiece( player, containing, arena[i][j] ) )
            {
                return true;
            }
        }
        return false;
    }


    boolean checkRightUp( ArenaTile[][] arena, int x, int y, Player player )
    {
        boolean containing = false;
        for( int i = y - 1, j = x + 1; i >= 0 && j < arena[y].length; i--, j++ )
        {
            if( isEmptySquare( arena[i][j] ) )
            {
                return false;
            }
            if( i == y - 1 && !arena[i][j].getUnit().getOwner().equals( player ) )
            {
                containing = true;
            }

            if( isOwnPiece( player, containing, arena[i][j] ) )
            {
                return true;
            }
        }
        return false;
    }


    boolean checkLeftDown( ArenaTile[][] arena, int x, int y, Player player )
    {
        boolean containing = false;
        for( int i = y + 1, j = x - 1; i < arena.length && j >= 0; i++, j-- )
        {
            if( isEmptySquare( arena[i][j] ) )
            {
                return false;
            }
            if( i == y + 1 && !arena[i][j].getUnit().getOwner().equals( player ) )
            {
                containing = true;
            }

            if( isOwnPiece( player, containing, arena[i][j] ) )
            {
                return true;
            }
        }
        return false;
    }


    boolean checkRightDown( ArenaTile[][] arena, int x, int y, Player player )
    {
        boolean containing = false;
        for( int i = y + 1, j = x + 1; i < arena.length && j < arena[y].length; i++, j++ )
        {
            if( isEmptySquare( arena[i][j] ) )
            {
                return false;
            }
            if( i == y + 1 && !arena[i][j].getUnit().getOwner().equals( player ) )
            {
                containing = true;
            }

            if( isOwnPiece( player, containing, arena[i][j] ) )
            {
                return true;
            }
        }
        return false;
    }


    private boolean isEmptySquare( ArenaTile arenaTile )
    {
        return arenaTile.getUnit() == null;
    }
}
