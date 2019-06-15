package gametree;

public interface ArenaTile
{
    Unit getUnit();

    ArenaTile deepCopy();

    void setUnit( Unit unit );
}
