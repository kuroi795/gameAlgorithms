package gametree;

import util.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameTreeImpl
    implements GameTree
{
    private BattleArena baseArena;
    private BattleArena copyOfArena;
    private final GameStateEvaluator evaluator;
    private int searchDepth;
    private final Player maxPlayer;
    private GameTreeNode current;
    private PlacementMovesProvider movesProvider;


    public GameTreeImpl(
        BattleArena battleArena,
        GameStateEvaluator evaluator,
        int searchDepth,
        Player maxPlayer )
    {
        this.baseArena = battleArena;
        copyOfArena = battleArena.deepCopy();
        this.evaluator = evaluator;
        this.searchDepth = searchDepth;
        this.maxPlayer = maxPlayer;
        current = new GameTreeNode( maxPlayer, 0 );
    }


    public GameTreeImpl(
        BattleArena battleArena,
        GameStateEvaluator evaluator,
        int searchDepth,
        Player maximizingPlayer,
        PlacementMovesProvider movesProvider )
    {
        this( battleArena, evaluator, searchDepth, maximizingPlayer );
        this.movesProvider = movesProvider;
    }


    @Override
    public void performMove( Move move, Player player )
    {
        for( GameTreeNode childrenState : current.getChildrenStates() )
        {
            if( childrenState.getPlayer().equals( player ) && containsMove( move, childrenState ) )
            {
                current = childrenState;
                return;
            }
        }
    }


    private boolean containsMove( Move move, GameTreeNode childrenState )
    {
        for( Move childrenStateMove : childrenState.getMoves() )
        {
            if( childrenStateMove.equals( move ) )
            {
                return true;
            }
        }
        for( Move childrenStateMove : childrenState.getMoves() )
        {
            if( childrenStateMove.getUnit() == null || move.getUnit() == null )
            {
                if( childrenStateMove.getFrom().equals( childrenStateMove.getTo() )
                    && childrenStateMove.getTo().equals( move.getTo() ) )
                {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void prepareTree()
    {
        copyOfArena = baseArena.deepCopy();
        deepenTree( searchDepth + 1, current, baseArena.getNextPlayer( current.getPlayer() ) );
    }


    @Override
    public List<Move> findMovesUsingAlphaBeta()
    {
        double alphaBeta = alphaBeta( current, searchDepth, -Double.MAX_VALUE, Double.MAX_VALUE, maxPlayer );
        return getMovesByAlgorithmEval( alphaBeta );
    }


    @Override
    public List<Move> findMovesUsingAlphaBeta( int searchDepth )
    {
        double alphaBeta = alphaBeta( current, searchDepth, -Double.MAX_VALUE, Double.MAX_VALUE, maxPlayer );
        return getMovesByAlgorithmEval( alphaBeta );
    }


    private List<Move> getMovesByAlgorithmEval( double evaluation )
    {
        List<Move> moves = new ArrayList<>();
        for( GameTreeNode childrenState : current.getChildrenStates() )
        {
            if( childrenState.getAlgorithmEval() == evaluation )
            {
                moves.addAll( childrenState.getMoves() );
            }
        }
        return moves;
    }


    @Override
    public List<Move> findMovesUsingMinMax()
    {
        double minmax = minmax( current, searchDepth, maxPlayer );
        return getMovesByAlgorithmEval( minmax );
    }


    @Override
    public List<Move> findMovesUsingMinMax( int searchDepth )
    {
        double minmax = minmax( current, searchDepth, maxPlayer );
        return getMovesByAlgorithmEval( minmax );
    }


    private void deepenTree( long depth, GameTreeNode node, Player player )
    {
        if( depth == 0 )
        {
            return;
        }

        if( !node.getChildrenStates().isEmpty() )
        {
            for( GameTreeNode childrenState : node.getChildrenStates() )
            {
                Move move = childrenState.getMoves().get( 0 );
                BattleArena prevState = copyOfArena.deepCopy();
                copyOfArena.performMove( move );
                deepenTree( depth - 1, childrenState, baseArena.getNextPlayer( player ) );
                copyOfArena = prevState;
            }
            return;
        }

        ArenaTile[][] arena = copyOfArena.getArena();
        for( int y = 0; y < arena.length; y++ )
        {
            for( int x = 0; x < arena[y].length; x++ )
            {
                List<Location> possibleMoves = new ArrayList<>( movesProvider == null
                    ? Collections.emptyList()
                    : movesProvider.getPossiblePlacementMoves( arena, x, y, player ) );

                Unit unit = arena[y][x].getUnit();
                Location squareLocation = new Location( x, y );
                if( unit != null && player.equals( unit.getOwner() ) )
                {
                    possibleMoves.addAll( unit.getPossibleMoves( arena, squareLocation ) );
                }
                for( Location possibleMove : possibleMoves )
                {
                    BattleArena prevState = copyOfArena.deepCopy();
                    Move move = new Move( null, squareLocation, possibleMove );
                    copyOfArena.performMove( move );
                    double evaluated = evaluator.evaluateArena( copyOfArena.getArena() );
                    GameTreeNode gameTreeNode = new GameTreeNode( player, evaluated );
                    gameTreeNode.addMove( move );
                    node.addChildrenStates( gameTreeNode );
                    deepenTree( depth - 1, gameTreeNode, baseArena.getNextPlayer( player ) );
                    copyOfArena = prevState;
                    arena = prevState.getArena();
                }
            }
        }
    }


    double minmax( GameTreeNode node, long depth, Player maximizingPlayer )
    {
        if( depth == 0 || node.getChildrenStates().isEmpty() )
        {
            double evaluation = node.getEvaluation();
            node.algorithmEval( evaluation );
            return evaluation;
        }
        if( node.getPlayer().equals( maximizingPlayer ) )
        {
            double maximizingPlayerValue = getMaximizingPlayerValue( node, depth, maximizingPlayer );
            node.algorithmEval( maximizingPlayerValue );
            return maximizingPlayerValue;
        }
        else
        {
            double minimizingPlayerValue = getMinimizingPlayerValue( node, depth, maximizingPlayer );
            node.algorithmEval( minimizingPlayerValue );
            return minimizingPlayerValue;
        }
    }


    double alphaBeta( GameTreeNode node, long depth, double alpha, double beta, Player maximizingPlayer )
    {
        if( depth == 0 || node.getChildrenStates().isEmpty() )
        {
            double evaluation = node.getEvaluation();
            node.algorithmEval( evaluation );
            return evaluation;
        }

        if( node.getPlayer().equals( maximizingPlayer ) )
        {
            double maximizingPlayerValueAlphaBeta =
                getMaximizingPlayerValueAlphaBeta( node, depth, alpha, beta, maximizingPlayer );
            node.algorithmEval( maximizingPlayerValueAlphaBeta );
            return maximizingPlayerValueAlphaBeta;
        }
        else
        {
            double minimizingPlayerValueAlphaBeta =
                getMinimizingPlayerValueAlphaBeta( node, depth, alpha, beta, maximizingPlayer );
            node.algorithmEval( minimizingPlayerValueAlphaBeta );
            return minimizingPlayerValueAlphaBeta;
        }
    }


    private double getMaximizingPlayerValueAlphaBeta(
        GameTreeNode node,
        long depth,
        double alpha,
        double beta,
        Player maximizingPlayer )
    {
        double value = -Double.MAX_VALUE;
        for( GameTreeNode childrenState : node.getChildrenStates() )
        {
            value = Math.max( value, alphaBeta( childrenState, depth - 1, alpha, beta, maximizingPlayer ) );
            alpha = Math.max( alpha, value );
            if( alpha >= beta )
            {
                break;
            }
        }
        return value;
    }


    private double getMinimizingPlayerValueAlphaBeta(
        GameTreeNode node,
        long depth,
        double alpha,
        double beta,
        Player maximizingPlayer )
    {
        double value = Double.MAX_VALUE;
        for( GameTreeNode childrenState : node.getChildrenStates() )
        {
            value = Math.min( value, alphaBeta( childrenState, depth - 1, alpha, beta, maximizingPlayer ) );
            beta = Math.min( beta, value );
            if( alpha >= beta )
            {
                break;
            }
        }
        return value;
    }


    private double getMaximizingPlayerValue( GameTreeNode node, long depth, Player maximizingPlayer )
    {
        double value = -Double.MAX_VALUE;
        for( GameTreeNode childrenState : node.getChildrenStates() )
        {
            value = Math.max( value, minmax( childrenState, depth - 1, maximizingPlayer ) );
        }
        return value;
    }


    private double getMinimizingPlayerValue( GameTreeNode node, long depth, Player maximizingPlayer )
    {
        double value = Double.MAX_VALUE;
        for( GameTreeNode childrenState : node.getChildrenStates() )
        {
            value = Math.min( value, minmax( childrenState, depth - 1, maximizingPlayer ) );
        }
        return value;
    }

}
