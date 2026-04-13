package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TeamTest{
   
     //Tests para el metodo marketValue() de Team.
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
    
    //Tests para métodos ExpectedValue() y DefaultValue() de Team.
    @Test
    public void shouldCalculateExpectedMarketValueWhenAllMinutesAreKnown(){
        // Arrange
        Team t = new Team("TEAM-A", 0, 'A', "Coach", "White");
        t.addPlayer(new Player("P1", 30, 'A', 100, "Club1"));
        t.addPlayer(new Player("P2", 30, 'M', 200, "Club2"));

        // Act & Assert
        try {
            assertEquals(150, t.expectedMarketValue());
        } catch (FifaException e){
            fail("Threw an exception");
        }
    }

    @Test
    public void shouldEstimateUnknownMinutesForExpectedMarketValue(){
        // Arrange
        Team t = new Team("TEAM-B", 0, 'B', "Coach", "Blue");
        t.addPlayer(new Player("P1", 60, 'A', 100, "Club1"));
        t.addPlayer(new Player("P2", 30, 'M', 200, "Club2"));
        t.addPlayer(new Player("P3", null, 'D', 300, "Club3"));

        // Act & Assert
        try {
            assertEquals(188, t.expectedMarketValue());
        } catch (FifaException e){
            fail("Threw an exception");
        }
    }

    @Test
    public void shouldThrowExceptionInExpectedMarketValueWhenMarketValueIsUnknown(){
        // Arrange
        Team t = new Team("TEAM-C", 0, 'C', "Coach", "Red");
        t.addPlayer(new Player("P1", 40, 'A', 100, "Club1"));
        t.addPlayer(new Player("P2", null, 'M', null, "Club2"));

        // Act & Assert
        try {
            t.expectedMarketValue();
            fail("Did not throw exception");
        } catch (FifaException e){
            assertEquals(FifaException.VALUE_UNKNOWN, e.getMessage());
        }
    }

    @Test
    public void shouldCalculateDefaultMarkedValueUsingDefaults(){
        // Arrange
        Team t = new Team("TEAM-D", 0, 'D', "Coach", "Green");
        t.addPlayer(new Player("P1", 50, 'A', 100, "Club1"));
        t.addPlayer(new Player("P2", null, 'M', null, "Club2"));

        // Act & Assert
        try {
            assertEquals(150, t.defaultMarkedValue(200, 50));
        } catch (FifaException e){
            fail("Threw an exception");
        }
    }

    @Test
    public void shouldThrowImpossibleInDefaultMarkedValueWhenTotalMinutesIsZero(){
        // Arrange
        Team t = new Team("TEAM-E", 0, 'E', "Coach", "Black");
        t.addPlayer(new Player("P1", null, 'A', 100, "Club1"));

        // Act & Assert
        try {
            t.defaultMarkedValue(500, 0);
            fail("Did not throw exception");
        } catch (FifaException e){
            assertEquals(FifaException.IMPOSSIBLE, e.getMessage());
        }
    }

}

