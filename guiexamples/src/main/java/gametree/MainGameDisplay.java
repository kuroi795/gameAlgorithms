package gametree;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import util.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static utils.DisplayUtils.setupDisplay;


public class MainGameDisplay
{

    private static int width, height, size = 50;
    private static boolean moveMade = false;


    public static void main( String[] args )
    {
        CellFactory cf = new CellFactory( size );
        List<Cell2D> cells = new ArrayList<>();
        int arenaSize = 10;
        Cell2D[][] cell2DS = cf.populateCells( cells, arenaSize, arenaSize );
        width = 510;
        MainGameDisplay.height = 510;
        setupDisplay( width, MainGameDisplay.height );

        PlayerImpl white = new PlayerImpl( "White" );
        PlayerImpl black = new PlayerImpl( "Black" );
        BattleArenaImpl battleArena = new BattleArenaImpl( cell2DS, black, white );

        int i = arenaSize / 2;
        cell2DS[i][i].setUnit( new UnitImpl( black, black.toString() ) );
        cell2DS[i - 1][i].setUnit( new UnitImpl( white, white.toString() ) );
        cell2DS[i][i - 1].setUnit( new UnitImpl( white, white.toString() ) );
        cell2DS[i - 1][i - 1].setUnit( new UnitImpl( black, black.toString() ) );
        boolean keyPressed = false;

        PlacementMovesProviderImpl placementMovesProvider = new PlacementMovesProviderImpl();
        GameStateEvaluator gameStateEvaluator = new GameStateEvaluatorImpl( white, black );
        GameTreeImpl gt = new GameTreeImpl( battleArena, gameStateEvaluator, 2, white, placementMovesProvider );
        gt.prepareTree();
        while( !Display.isCloseRequested() )
        {
            glClear( GL_COLOR_BUFFER_BIT );

            if( Mouse.isButtonDown( 0 ) && !keyPressed )
            {
                keyPressed = true;
                for( int y = 0; y < cell2DS.length; y++ )
                {
                    for( int x = 0; x < cell2DS[y].length; x++ )
                    {
                        if( cell2DS[y][x].inBounds( Mouse.getX(), height - Mouse.getY() ) )
                        {
                            List<Location> possiblePlacementMoves = placementMovesProvider
                                .getPossiblePlacementMoves( cell2DS, x, y,
                                    battleArena.getCurrentPlayer() );
                            if( !possiblePlacementMoves.isEmpty() )
                            {
                                Move move = new Move(
                                    new UnitImpl(
                                        battleArena.getCurrentPlayer(),
                                        battleArena.getCurrentPlayer().toString() ), null,
                                    possiblePlacementMoves.get( 0 ) );
                                gt.performMove( move, battleArena.getCurrentPlayer() );
                                battleArena.performMove( move );
                                moveMade = true;
                            }
                        }
                    }
                }
            }

            if( !Mouse.isButtonDown( 0 ) )
            {
                keyPressed = false;
            }

            if( moveMade && !keyPressed && Mouse.isButtonDown( 1 ) )
            {
                keyPressed = true;
                List<Move> movesUsingMinMax = gt.findMovesUsingMinMax();
                if( !movesUsingMinMax.isEmpty() )
                {
                    Collections.shuffle( movesUsingMinMax );
                    gt.performMove( movesUsingMinMax.get( 0 ), battleArena.getCurrentPlayer() );
                    battleArena.performMove( movesUsingMinMax.get( 0 ) );
                    gt.prepareTree();
                }
                moveMade = false;
            }

            cells.forEach( Cell2D::draw );
            Display.update();
            Display.sync( 60 );
        }

        Display.destroy();
        System.exit( 0 );

    }

}
