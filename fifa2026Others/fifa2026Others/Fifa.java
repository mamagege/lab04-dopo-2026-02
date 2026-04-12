package domain;


import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Fifa
 * @author DOPO
 * @version ECI 2026
 */

public class Fifa{
    private ArrayList<Participant> participants;
    private TreeMap<String,Player> players;

    /**
     * Create a Fifa
     */
    public Fifa(){
        participants = new ArrayList<Participant>();
        players = new TreeMap<String,Player>();
        addSome();
    }

    private void addSome(){
        String [][] players= {{"L.DIAZ", "690","A","760000000","Bayer"},
                              {"JAMES", "516","M","2200000","Minnesota"},
                              {"BORRE", "445","A","4400000","Sport Club"},
                              {"LUCUMI", "1250","D","125000000","Bologna"},
                              {"VARGAS", "1160","P","540000","Atlas"}};
        for (String [] p: players){
            addPlayer(p[0],p[1],p[2],p[3],p[4]);
        }
        
        String [][] teams = {{"COLOMBIA","1620", "K", "Lorenzo", "Amarill-Rojo-Azul", "L.DIAZ\nJAMES\nBORRE\nLUCUMI\nVARGAS"}};
        for (String [] t: teams){
            addTeam(t[0],t[1],t[2],t[3],t[4],t[5]);
        }
    }


    /**
     * Consult a participant
     */
    public Participant consult(String name){
        Participant c=null;
        for(int i=0;i<participants.size() && c == null;i++){
            if (participants.get(i).name().compareToIgnoreCase(name)==0) 
               c=participants.get(i);
        }
        return c;
    }

    
    /**
     * Add a new player
    */
    public void addPlayer(String name, String minutes, String position, String value, String club){ 
        Player np=new Player(name,Integer.parseInt(minutes),position.charAt(0),Integer.parseInt(value),club);
        participants.add(np);
        players.put(name.toUpperCase(),np); 
    }
    
    /**
     * Add a new team
    */
    public void addTeam(String name, String minutes, String position, String manager, String uniform, String thePlayers){ 
        Team c = new Team(name,Integer.parseInt(minutes),position.charAt(0),manager, uniform);
        String [] aPlayers= thePlayers.split("\n");
        for (String b : aPlayers){
            c.addPlayer(players.get(b.toUpperCase()));
        }
        participants.add(c);
    }

    /**
     * Consults the participants that start with a prefix
     * @param  
     * @return 
     */
    public ArrayList<Participant> select(String prefix){
        ArrayList <Participant> answers=new ArrayList<Participant>();
        prefix=prefix.toUpperCase();
        for(int i=0;i<=participants.size();i++){
            if(participants.get(i).name().toUpperCase().startsWith(prefix)){
                answers.add(participants.get(i));
            }   
        }
        return answers;
    }


    
    /**
     * Consult selected participants
     * @param selected
     * @return  
     */
    public String data(ArrayList<Participant> selected){
        StringBuffer answer=new StringBuffer();
        answer.append(participants.size()+ " elementos\n");
        for(Participant p : selected) {
            try{
                answer.append('>' + p.data());
                answer.append("\n");
            }catch(FifaException e){
                answer.append("**** "+e.getMessage());
            }
        }    
        return answer.toString();
    }
    
    
     /**
     * Return the data of participants with a prefix
     * @param prefix
     * @return  
     */ 
    public String search(String prefix){
        return data(select(prefix));
    }
    
    
    /**
     * Return the data of all participants
     * @return  
     */    
    public String toString(){
        return data(participants);
    }
    
    /**
     * Consult the number of participants
     * @return 
     */
    public int numberParticipants(){
        return participants.size();
    }

}
