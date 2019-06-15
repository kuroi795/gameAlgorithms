package pathfinders;

public class RatedMove<T>
    implements Comparable<RatedMove>
{
    private final double rating;
    private final T move;


    RatedMove( double rating, T move )
    {
        this.rating = rating;
        this.move = move;
    }


    public int compareTo( RatedMove o )
    {
        return Double.compare( rating, o.rating );
    }


    T getMove()
    {
        return move;
    }
}
