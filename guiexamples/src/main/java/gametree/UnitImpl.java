package gametree;

import util.Location;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class UnitImpl
    extends Unit
{

    private Player owner;
    private String name;


    public UnitImpl( Player owner, String name )
    {
        this.owner = owner;
        this.name = name;
    }


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
    public String toString()
    {
        return "UnitImpl{" +
            "owner=" + owner +
            '}';
    }


    @Override
    public Unit deepCopy()
    {
        return new UnitImpl( owner, name );
    }


    @Override
    public boolean equals( Object o )
    {
        if( this == o )
            return true;
        if( o == null || getClass() != o.getClass() )
            return false;
        UnitImpl unit = (UnitImpl)o;
        return Objects.equals( owner, unit.owner ) &&
            Objects.equals( name, unit.name );
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( owner, name );
    }
}
