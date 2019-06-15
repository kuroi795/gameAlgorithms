package gametree;

import util.Location;

import java.util.List;


public interface PlacementMovesProvider
{
    List<Location> getPossiblePlacementMoves( ArenaTile[][] arena, int x, int y, Player player );
}
