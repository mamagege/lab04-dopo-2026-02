package domain;

 

   
 
import java.util.ArrayList;

public class Team extends Participant{
   
    private String manager;
    private String uniform;
    
    private ArrayList<Player> players;
    
    /**
     * Constructs a new Team
     * @param name
     * @param type
     */
    public Team(String name, int minutes, char position, String manager, String uniform){
        super(name, minutes, position);
        this.manager=manager;
        this.uniform=uniform;
        players= new ArrayList<Player>();
    }


     /**
     * Add a new Player
     * @param c
     */   
    public void addPlayer(Player c){
        players.add(c);
    }
       
     /**
     * Calculates the marketValue of a team
     */ 
    
    public int marketValue() throws FifaException{ 
        if (players == null) {throw new FifaException(FifaException.IMPOSSIBLE);}
        
        int totalMinutes = 0;
        for (Player p: players){
        
            totalMinutes += p.minutes();
        
        }
        
        if (totalMinutes == 0) {throw new FifaException(FifaException.IMPOSSIBLE);}
        
        double totalValue = 0;
        for (Player p: players) {
    
            totalValue += (p.marketValue() * ( (double) p.minutes() / totalMinutes) );

        }
        
        return (int) totalValue;
    }
    

   /**
     * * Returns the expected market value of the team using estimated minutes for
     * players with unknown minutes.
     * 
     * If more than half of the players have unknown minutes, estimate each
     * unknown value with knownMinutesSum / totalPlayers
     * 
     * Otherwise, estimate each unknown value with
     *       knownMinutesSum / knownPlayers
     * 
     * Market values are never replaced; if any player's market value is unknown,
     * the method throws FifaException.VALUE_UNKNOWN
     *
     * @return the weighted expected market value
     * @throws FifaException if there are no players, if the total minutes is zero,
     *         or if a player's market value is unknown
     */
   
    
    public int expectedMarketValue() throws FifaException{
        if (players == null || players.isEmpty()) {
            throw new FifaException(FifaException.IMPOSSIBLE);
        }

        int knownMinutesSum = 0;
        int knownMinutesCount = 0;
        int unknownMinutesCount = 0;

        for (Player p : players) {
            try {
                knownMinutesSum += p.minutes();
                knownMinutesCount++;
            } catch (FifaException e) {
                if (!FifaException.MINUTES_UNKNOWN.equals(e.getMessage())) {
                    throw e;
                }
                unknownMinutesCount++;
            }
        }

        int estimatedMinutes;
        if (unknownMinutesCount > players.size() / 2) {
            estimatedMinutes = knownMinutesSum / players.size();
        } else {
            estimatedMinutes = (knownMinutesCount == 0) ? 0 : knownMinutesSum / knownMinutesCount;
        }

        int totalMinutes = knownMinutesSum + (unknownMinutesCount * estimatedMinutes);
        if (totalMinutes == 0) {
            throw new FifaException(FifaException.IMPOSSIBLE);
        }

        double totalValue = 0;
        for (Player p : players) {
            int playerValue = p.marketValue();
            int playerMinutes;
            try {
                playerMinutes = p.minutes();
            } catch (FifaException e) {
                if (!FifaException.MINUTES_UNKNOWN.equals(e.getMessage())) {
                    throw e;
                }
                playerMinutes = estimatedMinutes;
            }
            totalValue += playerValue * ((double) playerMinutes / totalMinutes);
        }

        return (int) totalValue;
    }

    
    
    /**
     * Returns the market value of the team using default values for unknown data.
     * 
     * If a player has unknown market value, defaultMarketValueis used.
     * If a player has unknown minutes, defaultMinutesis used.
     *
     * @param defaultMarketValue replacement value for unknown market value
     * @param defaultMinutes replacement value for unknown minutes
     * @return the weighted market value with defaults
     * @throws FifaException if there are no players or if the computed total
     *         minutes is zero
     */

    public int defaultMarkedValue(int defaultMarketValue, int defaultMinutes) throws FifaException{
        if (players == null || players.isEmpty()) {
            throw new FifaException(FifaException.IMPOSSIBLE);
        }

        int totalMinutes = 0;
        for (Player p : players) {
            try {
                totalMinutes += p.minutes();
            } catch (FifaException e) {
                if (!FifaException.MINUTES_UNKNOWN.equals(e.getMessage())) {
                    throw e;
                }
                totalMinutes += defaultMinutes;
            }
        }

        if (totalMinutes == 0) {
            throw new FifaException(FifaException.IMPOSSIBLE);
        }

        double totalValue = 0;
        for (Player p : players) {
            int playerValue;
            int playerMinutes;

            try {
                playerValue = p.marketValue();
            } catch (FifaException e) {
                if (!FifaException.VALUE_UNKNOWN.equals(e.getMessage())) {
                    throw e;
                }
                playerValue = defaultMarketValue;
            }

            try {
                playerMinutes = p.minutes();
            } catch (FifaException e) {
                if (!FifaException.MINUTES_UNKNOWN.equals(e.getMessage())) {
                    throw e;
                }
                playerMinutes = defaultMinutes;
            }

            totalValue += playerValue * ((double) playerMinutes / totalMinutes);
        }

        return (int) totalValue;
    }
    
    /**
     * Returns the "best" market value of the team by rewarding players in their
     * prime age range (18 to 30 years inclusive).
     * 
     * The method calculates a weighted market value by minutes played, similar to
     * marketValue(), but each eligible player receives a fixed bonus of
     * 20 points over their individual market value before weighting.
     *
     * @return weighted team value with prime-age bonus
     * @throws FifaException if team value cannot be calculated due to missing
     *         minutes/market value or when total minutes are zero
     */
    public int bestMarkedValue() throws FifaException{
        if (players == null || players.isEmpty()) {
            throw new FifaException(FifaException.IMPOSSIBLE);
        }

        int totalMinutes = 0;
        for (Player p : players) {
            totalMinutes += p.minutes();
        }

        if (totalMinutes == 0) {
            throw new FifaException(FifaException.IMPOSSIBLE);
        }

        double totalValue = 0;
        for (Player p : players) {
            int playerValue = p.marketValue();
            Integer playerAge = p.age();

            if (playerAge != null && playerAge >= 18 && playerAge <= 30) {
                playerValue += 20;
            }

            totalValue += playerValue * ((double) p.minutes() / totalMinutes);
        }

        return (int) totalValue;
    }
    
    
    @Override
    public String data() throws FifaException{
        StringBuffer answer=new StringBuffer();
        answer.append(name+".\t Grupo: "+position+".\t Valor Promedio:" +marketValue());
        for(Player p: players) {
            answer.append("\n\t"+p.data());
        }
        return answer.toString();
    } 
    

}
