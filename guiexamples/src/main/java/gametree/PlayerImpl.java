package gametree;

import java.util.Objects;


public class PlayerImpl
    extends Player
{
    private String name;


    public PlayerImpl( String name )
    {
        this.name = name;
    }


    @Override
    public boolean equals( Object o )
    {
        if( this == o )
            return true;
        if( o == null || getClass() != o.getClass() )
            return false;
        PlayerImpl playerOne = (PlayerImpl)o;
        return name.equals( playerOne.name );
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( name );
    }
}
