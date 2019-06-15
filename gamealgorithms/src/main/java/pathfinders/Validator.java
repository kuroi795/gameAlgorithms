package pathfinders;

public class Validator
{
    public static void notNull( Object o )
    {
        if( o == null )
        {
            throw new NullPointerException( "Expected object was found to be null" );
        }
    }
}
