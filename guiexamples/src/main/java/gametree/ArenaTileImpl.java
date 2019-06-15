package gametree;

import java.util.Objects;


public class ArenaTileImpl
    implements ArenaTile
{
    protected Unit unit;


    public ArenaTileImpl( Unit unit )
    {
        this.unit = unit;
    }


    @Override
    public Unit getUnit()
    {
        return unit;
    }


    @Override
    public ArenaTile deepCopy()
    {
        return new ArenaTileImpl( unit != null ? unit.deepCopy() : null );
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
        ArenaTileImpl arenaTile = (ArenaTileImpl)o;
        return Objects.equals( unit, arenaTile.unit );
    }


    @Override
    public String toString()
    {
        return "ArenaTileImpl{" +
            "unit=" + unit +
            '}';
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( unit );
    }


    @Override
    public void setUnit( Unit unit )
    {
        this.unit = unit;
    }
}
