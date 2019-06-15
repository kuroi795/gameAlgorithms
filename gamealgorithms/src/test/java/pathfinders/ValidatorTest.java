package pathfinders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ValidatorTest
{
    @Test
    void validate_givenNull_throwException()
    {
        assertThrows( NullPointerException.class, () -> Validator.notNull( null ) );
    }


    @Test
    void validate_givenNotNullObject_DoNotThrowException()
    {
        assertDoesNotThrow( () -> Validator.notNull( new Object() ) );
    }
}
