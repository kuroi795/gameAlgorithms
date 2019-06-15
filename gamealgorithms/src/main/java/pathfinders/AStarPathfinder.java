package pathfinders;

import util.Location;

import java.util.*;


public class AStarPathfinder
    implements GridPathfinder
{

    private final Location BLOCKED_MOVE = new Location( -1, -1 );
    private Heuristic heuristic;
    private Map<Location, Location> stepMap;
    private Map<Location, Double> costByLocation;
    private Queue<RatedMove<Location>> queue;
    private Location bestHeuristically = null;
    private double bestDistToGoal = Double.MAX_VALUE;


    AStarPathfinder()
    {
    }


    public AStarPathfinder( Heuristic heuristic )
    {
        this.heuristic = heuristic;
    }


    @Override
    public List<Location> findPath( Tile[][] map, Location startPoint, Location goal, boolean diagonalMoves )
    {
        return findPathOnMap( new TileMapHolder( map, diagonalMoves ), startPoint, goal );
    }


    @Override
    public List<Location> findPath(
        Tile[][] map,
        int startPosX,
        int startPosY,
        int goalPosX,
        int goalPosY,
        boolean diagonalMoves )
    {
        return findPath( map, new Location( startPosX, startPosY ), new Location( goalPosX, goalPosY ), diagonalMoves );
    }


    @Override
    public List<Location> findPath( double[][] map, Location startPoint, Location goal, boolean diagonalMoves )
    {
        return findPathOnMap( new DoubleMapHolder( map, diagonalMoves ), startPoint, goal );
    }


    @Override
    public List<Location> findPath(
        double[][] map,
        int startPosX,
        int startPosY,
        int goalPosX,
        int goalPosY,
        boolean diagonalMoves )
    {
        return findPath( map, new Location( startPosX, startPosY ), new Location( goalPosX, goalPosY ), diagonalMoves );
    }


    @Override
    public List<Location> findPath(
        MapHolder map, int startPosX, int startPosY, int goalPosX, int goalPosY )
    {
        return findPath( map, new Location( startPosX, startPosY ), new Location( goalPosX, goalPosY ) );
    }


    @Override
    public List<Location> findPath( MapHolder map, Location startPoint, Location goal )
    {
        return findPathOnMap( map, startPoint, goal );
    }


    private List<Location> findPathOnMap( MapHolder mapHolder, Location start, Location goal )
    {
        validateArguments( mapHolder, start, goal );

        resetState();
        queue.offer( new RatedMove<>( 0, start ) );
        Location currentLocation;
        while( !queue.isEmpty() )
        {
            currentLocation = queue.poll().getMove();

            if( goal.equals( currentLocation ) )
                break;

            Double currentCost = getCost( costByLocation, currentLocation );
            checkPossibleMoves( mapHolder, goal, currentLocation, currentCost );

        }
        return getFinalStepList( start, goal );
    }


    private void checkPossibleMoves( MapHolder mapHolder, Location goal, Location currentLocation, Double currentCost )
    {
        List<Location> possibleMoves = mapHolder.getPossibleMoves( currentLocation );

        for( Location nextMove : possibleMoves )
        {
            double costOfMove = mapHolder.getCostOfMove( nextMove );
            checkStepAgainstExisting( goal, currentLocation, currentCost, nextMove, costOfMove );
        }
    }


    private void validateArguments( Object map, Object start, Object goal )
    {
        Validator.notNull( map );
        Validator.notNull( start );
        Validator.notNull( goal );
    }


    private void resetState()
    {
        queue = new PriorityQueue<>();
        costByLocation = new HashMap<>();
        stepMap = new HashMap<>();
        bestHeuristically = null;
        bestDistToGoal = Double.MAX_VALUE;
    }


    private List<Location> getFinalStepList( Location start, Location goal )
    {
        List<Location> locations = new ArrayList<>();
        Location curr = goal;
        while( !goalReached( start, curr ) && !noPathAvailable( curr ) )
        {
            Location value = getLocation( goal, curr );
            curr = Optional.ofNullable( value ).orElse( new Location( -1, -1 ) );
            locations.add( curr );
        }
        Collections.reverse( locations );
        if( wasGoalReached( locations, goal ) )
        {
            locations.add( goal );
        }
        return locations;
    }


    private boolean wasGoalReached( List<Location> locations, Location goal )
    {
        Location lastLocation = locations.get( locations.size() - 1 );
        return !lastLocation.equals( BLOCKED_MOVE ) && (lastLocation.getY() == goal.getY() - 1
            || lastLocation.getY() == goal.getY() + 1
            || lastLocation.getX() == goal.getX() - 1
            || lastLocation.getX() == goal.getX() + 1);
    }


    private Location getLocation( Location goal, Location curr )
    {
        return curr.equals( goal ) && stepMap.get( curr ) == null ? bestHeuristically : stepMap.get( curr );
    }


    private boolean noPathAvailable( Location curr )
    {
        return curr.equals( BLOCKED_MOVE );
    }


    private boolean goalReached( Location goal, Location curr )
    {
        return goal.equals( curr );
    }


    private void checkStepAgainstExisting(
        Location goal,
        Location currentLocation,
        Double currentCost,
        Location nextMove, double costOfMove )
    {
        if( !(costOfMove == -1) )
        {
            double cost = currentCost + costOfMove;

            Double c = costByLocation.get( nextMove );
            if( c == null || c > cost )
            {
                costByLocation.put( nextMove, cost );
                addPotentialMoveToQueue( goal, nextMove, cost );
                stepMap.put( nextMove, currentLocation );
            }
        }
    }


    private void addPotentialMoveToQueue( Location goal, Location nextMove, double cost )
    {
        double evaluate = heuristic != null ? heuristic.evaluate( goal, nextMove ) : 0;
        if( bestDistToGoal > evaluate && heuristic != null )
        {
            bestDistToGoal = evaluate;
            bestHeuristically = nextMove;
        }
        queue.offer( new RatedMove<>( cost + evaluate, nextMove ) );
    }


    private <T> Double getCost( Map<T, Double> costByLocation, T currentLocation )
    {
        return costByLocation.get( currentLocation ) == null ? 0d : costByLocation.get( currentLocation );
    }

}
