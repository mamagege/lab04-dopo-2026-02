package domain;  

public class Player extends Participant{
    
    private Integer value;    //[0..300000000] 
    private String club;

    public Player(String name, Integer minutes, char position, Integer value, String club){
        super(name, minutes, position);
        this.value=value;
        this.club=club;
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
