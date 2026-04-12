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
        return 0;
    }
    
    
    /**
     * Returns the Marked Value using default values 
     * @return
     * @throws FifaException, if the resistance cannot be calculate
     */
    //If a player's market value or minutes played are unknown, default values ​​are used.
    public int defaultMarkedValue(int defaultMarketValue, int defaultMinutes) throws FifaException{
        return 0;
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
