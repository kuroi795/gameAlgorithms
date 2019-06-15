package gametree;

import java.util.ArrayList;
import java.util.List;


public class GameTreeNode
{
    private final Player player;
    private List<Move> moves;
    private double evaluation;

    private List<GameTreeNode> childrenStates;
    private double algorithmEval;


    public GameTreeNode( Player player, double evaluated )
    {
        this.player = player;
        this.evaluation = evaluated;
        childrenStates = new ArrayList<>();
        moves = new ArrayList<>();
    }


    void addMove( Move move )
    {
        moves.add( move );
    }


    void algorithmEval( double evaluation )
    {
        this.algorithmEval = evaluation;
    }


    public List<Move> getMoves()
    {
        return moves;
    }


    public double getEvaluation()
    {
        return evaluation;
    }


    public List<GameTreeNode> getChildrenStates()
    {
        return childrenStates;
    }


    public void addChildrenStates( GameTreeNode childrenState )
    {
        this.childrenStates.add( childrenState );
    }


    public Player getPlayer()
    {
        return player;
    }


    public double getAlgorithmEval()
    {
        return algorithmEval;
    }
}
