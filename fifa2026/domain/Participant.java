package domain;

      

public abstract class Participant{
    protected String name;
    protected Integer minutes;
    protected char position; //For players [P(Goalkeepers),D(Defenders), M(Midfielders), A(Forwards)] 
                             // For teams [A .. L]
    
    
    public Participant(String name, Integer minutes, char position){
        this.name=name;
        this.minutes=minutes;
        this.position=position;
    }
    
    
    /**
     * Return the name
     * @return
     */
    public String name(){
        return name;
    }

    /**
     * Return the minutes played
     * @return
     */

    public int minutes() throws FifaException{
       if (minutes == null) throw new FifaException(FifaException.MINUTES_UNKNOWN);
       return minutes;
    } 

 
    /**
     * Return the position
     * @return
     */
    public char position(){
        return position;
    }
    
    /**
     * Returns the Market Value of a player or the Weighted Market Value of a team
     * @return
     * @throws FifaException, if any marker value or minutes is unknown
     */
    public abstract int marketValue() throws FifaException;
    
    
    /**
     * Return the representation as string
     * @return
     * @throws FifaException, if the data has problems (some unknown or erroneous data)
     */    
    public abstract String data() throws FifaException;

}
