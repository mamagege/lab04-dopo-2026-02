package domain;
/**
 * Clase de excepciones para Fifa
 *
 * @author Juan Diego Gaitan, David Lasso
 * @version 1.0
 */
public class FifaException extends Exception
{

    public static final String VALUE_UNKNOWN = "Marker value is unknown";
    public static final String IMPOSSIBLE = "The value cannot be calculate";
    public static final String MINUTES_UNKNOWN = "Minutes is unknown";
    public static final String DUPLICATE_NAME = "Name already exists";
    public static final String INVALID_NUMBER = "Numeric value is invalid";
    public static final String INVALID_POSITION = "Position is invalid";
    public static final String INVALID_DATA = "Data is invalid";
    public static final String PLAYER_NOT_FOUND = "Player not found";
    public static final String NO_RESULTS = "No participants found for the requested prefix";
    
    
    public FifaException(String message)
    {
        super(message);
        
    }

}
