package pathfinders;

import util.Location;

import java.util.List;


public interface GridPathfinder
{
    List<Location> findPath(
        Tile[][] map,
        Location startPoint,
        Location goal,
        boolean diagonalMoves );

    List<Location> findPath(
        Tile[][] map,
        int startPosX,
        int startPosY,
        int goalPosX,
        int goalPosY,
        boolean diagonalMoves );

    List<Location> findPath(
        double[][] map,
        Location startPoint,
        Location goal,
        boolean diagonalMoves );

    List<Location> findPath(
        double[][] map,
        int startPosX,
        int startPosY,
        int goalPosX,
        int goalPosY,
        boolean diagonalMoves );

    List<Location> findPath(
        MapHolder map,
        int startPosX,
        int startPosY,
        int goalPosX,
        int goalPosY );

    List<Location> findPath(
        MapHolder map,
        Location startPoint,
        Location goal );
}
