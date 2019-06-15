package gametree;

import utils.CellUtils;

import java.util.List;


public class CellFactory
{

    private final float size;


    public CellFactory( float size )
    {

        this.size = size;

    }


    public Cell2D createCell( int x, int y, double relativeSpeed )
    {
        return new Cell2D( x * (size + 1f) + size / 2f, y * (size + 1f) + size / 2f, size );
    }


    public Cell2D createCell( int x, int y )
    {
        return new Cell2D( x * (size + 1f) + size / 2f, y * (size + 1f) + size / 2f, size );
    }


    public Cell2D[][] populateCells( List<Cell2D> cells, int height, int width )
    {
        Cell2D[][] cell2DS = new Cell2D[height][width];
        cells.addAll( CellUtils.populateCells( cell2DS, this::createCell ) );
        return cell2DS;
    }

}
