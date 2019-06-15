package gametree;

import java.util.List;


public interface GameTree
{
    void performMove( Move move, Player player );

    void prepareTree();

    List<Move> findMovesUsingAlphaBeta();

    List<Move> findMovesUsingAlphaBeta( int searchDepth );

    List<Move> findMovesUsingMinMax();

    List<Move> findMovesUsingMinMax( int searchDepth );
}
