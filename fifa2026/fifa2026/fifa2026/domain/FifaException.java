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
    
    public FifaException(String message)
    {
        super(message);
        
    }

}
