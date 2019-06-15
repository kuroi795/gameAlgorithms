package gametree;

public interface BattleArena
{
    BattleArena deepCopy();

    ArenaTile[][] getArena();

    Player getCurrentPlayer();

    Player getNextPlayer( Player current );

    void setArena( ArenaTile[][] arena );

    void performMove( Move move );
}
