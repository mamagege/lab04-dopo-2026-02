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
     * Returns the expectet Market Value 
     * @return
     * @throws FifaException, if any marker value or minutes is unknown
     */
    //If more than half of the players have no recorded minutes, the total number of players is used to average. 
    //Otherwise, the average minutes played by known players is used for those whose minutes are unknown.
    
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
     * Returns the Marked Value using default values 
     * @return
     * @throws FifaException, if the resistance cannot be calculate
     */
    //If a player's market value or minutes played are unknown, default values ​​are used.
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
