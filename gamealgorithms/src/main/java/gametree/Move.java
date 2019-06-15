package gametree;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import util.Location;


@Getter
@ToString
@EqualsAndHashCode
public class Move
{
    private Unit unit;
    private Location from, to;


    public Move( Unit unit, Location from, Location to )
    {
        this.unit = unit;
        this.from = from;
        this.to = to;
    }
}
