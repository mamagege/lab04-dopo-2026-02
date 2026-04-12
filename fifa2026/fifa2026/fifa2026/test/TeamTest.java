package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TeamTest{
   
 
    @Test
    public void shouldCalculateTheMarketValueOfATeam(){                              
        Team t = new Team("COLOMBIA",1620, 'K', "Lorenzo", "Amarill-Rojo-Azul");
        t.addPlayer(new Player("L.DIAZ", 690,'A',760000000,"Bayer"));
        t.addPlayer(new Player("JAMES", 516,'M',2200000,"Minnesota"));
        t.addPlayer(new Player("BORRE", 445,'A',4400000,"Sport Club"));
        t.addPlayer(new Player("LUCUMI", 1250,'D',125000000,"Bologna"));
        t.addPlayer(new Player("VARGAS", 1160,'P',540000,"Atlas"));
        try {
           assertEquals(168522432,t.marketValue());
        } catch (FifaException e){
            fail("Threw a exception");
        }    
    }    

    
    @Test
    public void shouldThrowExceptionIfTeamHasNoPlayer(){
        Team t = new Team("COLOMBIA",1620, 'K', "Lorenzo", "Amarill-Rojo-Azul");

        
        try { 
           int value=t.marketValue();
           fail("Did not throw exception");
        } catch (FifaException e) {
            assertEquals(FifaException.IMPOSSIBLE,e.getMessage());
        }    
    }    
    
    
    @Test
    public void shouldThrowExceptionIfPlayersMinutesSumZero(){
        Team t = new Team("COLOMBIA",1620, 'K', "Lorenzo", "Amarill-Rojo-Azul");
        t.addPlayer(new Player("L.DIAZ",0,'A',760000000,"Bayer"));
        t.addPlayer(new Player("JAMES", 0,'M',2200000,"Minnesota"));
        t.addPlayer(new Player("BORRE", 0,'A',4400000,"Sport Club"));
        t.addPlayer(new Player("LUCUMI", 0,'D',125000000,"Bologna"));
        t.addPlayer(new Player("VARGAS", 0,'P',540000,"Atlas"));
        try { 
           int value=t.marketValue();
           fail("Did not throw exception");
        } catch (FifaException e) {
            assertEquals(FifaException.IMPOSSIBLE,e.getMessage());
        }    
    } 
    
   @Test
    public void shouldThrowExceptionIfAMarketValueIsNotKnown(){
        Team t = new Team("COLOMBIA",1620, 'K', "Lorenzo", "Amarill-Rojo-Azul");
        t.addPlayer(new Player("L.DIAZ", 690,'A',760000000,"Bayer"));
        t.addPlayer(new Player("JAMES", 516,'M',null,"Minnesota"));
        t.addPlayer(new Player("BORRE", 445,'A',4400000,"Sport Club"));
        t.addPlayer(new Player("LUCUMI", 1250,'D',125000000,"Bologna"));
        t.addPlayer(new Player("VARGAS", 1160,'P',540000,"Atlas"));
        try { 
           int value=t.marketValue();
           fail("Did not throw exception");
        } catch (FifaException e) {
            assertEquals(FifaException.VALUE_UNKNOWN,e.getMessage());
        }    
    }     
    
   @Test
    public void shouldThrowExceptionIfAMinutesIsNotKnown(){
        Team t = new Team("COLOMBIA",1620, 'K', "Lorenzo", "Amarill-Rojo-Azul");
        t.addPlayer(new Player("L.DIAZ", 690,'A',760000000,"Bayer"));
        t.addPlayer(new Player("JAMES", 516,'M',2200000,"Minnesota"));
        t.addPlayer(new Player("BORRE", 445,'A',4400000,"Sport Club"));
        t.addPlayer(new Player("LUCUMI", 1250,'D',125000000,"Bologna"));
        t.addPlayer(new Player("VARGAS", null,'P',540000,"Atlas"));
        try { 
           int value=t.marketValue();
           fail("Did not throw exception");
        } catch (FifaException e) {
            assertEquals(FifaException.MINUTES_UNKNOWN,e.getMessage());
        }    
    }  
    
}