package domain;

 

public class Player extends Participant{
    
    private Integer value;    //[0..300000000] 
    private String club;
    private int age;

    //Constructor sin edad del jugador, no relevante o desconocida
    public Player(String name, Integer minutes, char position, Integer value, String club){
        super(name, minutes, position);
        this.value=value;
        this.club=club;
    }
    
    //Constructor de clase incluyendo edad del jugador. 
    public Player(String name, Integer minutes, char position, Integer value, String club, int age){
        super(name, minutes, position);
        this.value=value;
        this.club=club;
        this.age = age;
    }
    
    /**
     * Returns player's age.
     * @return age or {@code null} when unknown
     */
    public Integer age(){
        return age;
    }
    
    
           
    @Override
    public int marketValue() throws FifaException{
       if (value == null) throw new FifaException(FifaException.VALUE_UNKNOWN);
       return value;
    }    
    
    
    
    @Override
    public String data(){
        String theData= name+".\t Rol: "+position;
        try{
            theData= theData+". \t Valor:" +marketValue()+"\t Minutos:"+ minutes();
        } catch (FifaException e){
            theData += ". *** Datos incompletos";
        }
        return theData;
    }
}
