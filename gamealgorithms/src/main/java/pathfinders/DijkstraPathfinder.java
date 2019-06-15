package pathfinders;

import util.Location;

import java.util.*;


public class DijkstraPathfinder
    implements GraphPathfinder, GridPathfinder
{

    private Map<Node, Node> stepMapNodes;
    private Map<Node, Double> costByNode;
    private Queue<RatedMove<Node>> nodeQueue;
    private AStarPathfinder pathfinder;


    public DijkstraPathfinder()
    {
        this.pathfinder = new AStarPathfinder();
    }


    @Override
    public List<Node> findPath( Node startPoint, Node goal )
    {
        Validator.notNull( startPoint );
        Validator.notNull( goal );
        return findPathOnGraph( startPoint, goal );
    }


    private List<Node> findPathOnGraph( Node startPoint, Node goal )
    {
        nodeQueue = new PriorityQueue<>();
        costByNode = new HashMap<>();
        stepMapNodes = new HashMap<>();

        nodeQueue.offer( new RatedMove<>( 0, startPoint ) );

        while( !nodeQueue.isEmpty() )
        {
            Node currentNode = nodeQueue.poll().getMove();
            if( goal.equals( currentNode ) )
            {
                break;
            }
            Double currentCost = getCost( costByNode, currentNode );
            checkPossibleMoves( currentNode, currentCost, goal );
        }
        return getFinalStepList( startPoint, goal );
    }


    private List<Node> getFinalStepList( Node start, Node goal )
    {
        List<Node> stepList = new ArrayList<>();
        Node curr = goal;

        while( !start.equals( curr ) )
        {
            curr = Optional.ofNullable( stepMapNodes.get( curr ) )
                .orElseThrow( () -> new RuntimeException( "Goal not found in this graph" ) );
            stepList.add( curr );
        }
        Collections.reverse( stepList );
        stepList.add( goal );
        return stepList;
    }


    private void checkPossibleMoves( Node currentNode, Double currentCost, Node goal )
    {
        Map<Node, Double> neighboursWithDist = currentNode.getNeighbours();

        for( Map.Entry<Node, Double> neighbour : neighboursWithDist.entrySet() )
        {
            Double costOfMove = neighbour.getValue();

            double cost = currentCost + costOfMove;

            Node nextMove = neighbour.getKey();
            Double c = costByNode.get( nextMove );
            if( c == null || c > cost )
            {
                costByNode.put( nextMove, cost );
                addPotentialMoveOnGraphToQueue( goal, nextMove, cost );
                stepMapNodes.put( nextMove, currentNode );
            }
        }
    }


    private <T> Double getCost( Map<T, Double> costByLocation, T currentLocation )
    {
        return costByLocation.get( currentLocation ) == null ? 0d : costByLocation.get( currentLocation );
    }


    private void addPotentialMoveOnGraphToQueue( Node goal, Node nextMove, double cost )
    {
        nodeQueue.offer( new RatedMove<>( cost /*+ heuristic.evaluate( goal, nextMove )*/, nextMove ) );
    }


    @Override
    public List<Location> findPath( Tile[][] map, Location startPoint, Location goal, boolean diagonalMoves )
    {
        return pathfinder.findPath( map, startPoint, goal, diagonalMoves );
    }


    @Override
    public List<Location> findPath(
        Tile[][] map, int startPosX, int startPosY, int goalPosX, int goalPosY, boolean diagonalMoves )
    {
        return findPath( map, new Location( startPosX, startPosY ), new Location( goalPosX, goalPosY ), diagonalMoves );
    }


    @Override
    public List<Location> findPath( double[][] map, Location startPoint, Location goal, boolean diagonalMoves )
    {
        return pathfinder.findPath( map, startPoint, goal, diagonalMoves );
    }


    @Override
    public List<Location> findPath(
        double[][] map, int startPosX, int startPosY, int goalPosX, int goalPosY, boolean diagonalMoves )
    {
        return findPath( map, new Location( startPosX, startPosY ), new Location( goalPosX, goalPosY ), diagonalMoves );
    }


    @Override
    public List<Location> findPath( MapHolder map, int startPosX, int startPosY, int goalPosX, int goalPosY )
    {
        return findPath( map, new Location( startPosX, startPosY ), new Location( goalPosX, goalPosY ) );
    }


    @Override
    public List<Location> findPath( MapHolder map, Location startPoint, Location goal )
    {
        return pathfinder.findPath( map, startPoint, goal );
    }
}
