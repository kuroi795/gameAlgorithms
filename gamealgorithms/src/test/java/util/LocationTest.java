package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class LocationTest
{
    @Test
    void equals_givenSameObject_returnTrue()
    {
        Location location = new Location( 5, 8 );
        assertEquals( 8, location.getY() );
        assertEquals( 5, location.getX() );
        assertEquals( location, location );
    }


    @Test
    void equals_givenSameValue_returnTrue()
    {
        Location location = new Location( 7, 5 );
        Location location2 = new Location( 7, 5 );
        assertEquals( location, location2 );
        assertEquals( location.hashCode(), location2.hashCode() );
    }


    @Test
    void equals_givenNullLocations_returnFalse()
    {
        Location location = new Location( 7, 5 );
        assertNotEquals( location, null );
    }
}
