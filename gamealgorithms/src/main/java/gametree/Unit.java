package gametree;

import util.Location;

import java.util.List;


public abstract class Unit
{
    abstract public List<Location> getPossibleMoves( ArenaTile[][] battleArena, Location unitLocation );

    abstract public Player getOwner();

    abstract public Unit deepCopy();

    abstract public boolean equals( Object o );

    abstract public int hashCode();
}
