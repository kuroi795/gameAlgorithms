package gametree;

public class GameStateEvaluatorImpl
    implements GameStateEvaluator
{
    private Player p1, p2;


    public GameStateEvaluatorImpl( Player p1, Player p2 )
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
