package test;

import domain.Fifa;
import org.junit.Test;
import domain.FifaException;

import static org.junit.Assert.*;

public class FifaTest {

    @Test
    public void shouldAddAPlayerAndATeamInDomainLayer() {
        // Arrange
        Fifa fifa = new Fifa();
        
        int initialParticipants = fifa.numberParticipants();
        
        // Act
         try {
            fifa.addPlayer("MESSI", "1420", "D", "15000000", "Inter");
            fifa.addTeam("ARGENTINA", "1620", "J", "Scaloni", "Azul-blanco", "MESSI");
        } catch (FifaException e) {
            fail("Threw an exception");
        }

        // Assert
        assertEquals(initialParticipants + 2, fifa.numberParticipants());
    }

    @Test
    public void shouldRejectAddWhenNameAlreadyExists() throws FifaException{
        // Arrange
        Fifa fifa = new Fifa();
        // Act & Assert
        try {
            fifa.addPlayer("JAMES", "100", "M", "1000000", "ClubX");
            fail("Did not throw exception");
        } catch (FifaException e) {
            assertEquals(FifaException.DUPLICATE_NAME, e.getMessage());
        }
    }
    
     @Test
    public void shouldRejectAddPlayerWhenNumericValuesAreNotNumbers(){
        // Arrange
        Fifa fifa = new Fifa();

        // Act & Assert
        try {
            fifa.addPlayer("NEW-10", "N/A", "D", "FIFTEEN", "Inter");
            fail("Did not throw exception");
        } catch (FifaException e) {
            assertEquals(FifaException.INVALID_NUMBER, e.getMessage());
        }
    }
    
    @Test
        public void shouldRejectAddWhenValuesAreNotExpected(){
        // Arrange
        Fifa fifa = new Fifa();
        // Act & Assert
        try {
            fifa.addPlayer("NEW-11", "300", "X", "1000", "Inter");
            fail("Did not throw exception");
        } catch (FifaException e) {
            assertEquals(FifaException.INVALID_POSITION, e.getMessage());
        }

    }
    
         @Test
        public void shouldRejectTeamWhenReferencedPlayerDoesNotExist(){
        // Arrange
        Fifa fifa = new Fifa();

        // Act & Assert
        try {
            fifa.addTeam("CHILE", "1200", "C", "Coach", "Red", "NO-PLAYER");
            fail("Did not throw exception");
        } catch (FifaException e) {
            assertEquals(FifaException.PLAYER_NOT_FOUND, e.getMessage());
        }
        
    }

    @Test
        public void shouldListParticipantsIncludingRecentlyAddedEntities(){
        // Arrange
        Fifa fifa = new Fifa();
         try {
            fifa.addPlayer("MESSI", "1420", "D", "15000000", "Inter");
            fifa.addTeam("ARGENTINA", "1620", "J", "Scaloni", "Azul-blanco", "MESSI");
        } catch (FifaException e) {
            fail("Threw an exception");
        }
        
        String listing = fifa.toString();

        // Assert
        assertTrue(listing.contains(">MESSI"));
        assertTrue(listing.contains(">ARGENTINA"));
 
    }
    
    @Test
    public void shouldSearchByPrefixWithoutIndexErrorsAndReturnMatches(){
        // Arrange
        Fifa fifa = new Fifa();
        try {
            fifa.addPlayer("MESSI", "1420", "D", "15000000", "Inter");
            fifa.addTeam("ARGENTINA", "1620", "J", "Scaloni", "Azul-blanco", "MESSI");
        } catch (FifaException e) {
            fail("Threw an exception");
        }

        // Act
        String result = fifa.search("AR");

        // Assert
        assertTrue(result.contains(">ARGENTINA"));
        
        
    }
    
     @Test
    public void AcceptanceTestAddList(){
        // Arrange
        Fifa fifa = new Fifa();

        // Act
        try {
            fifa.addPlayer("MESSI", "1420", "D", "15000000", "Inter");
            fifa.addTeam("ARGENTINA", "1620", "J", "Scaloni", "Azul-blanco", "MESSI");
        } catch (FifaException e) {
            fail("Threw an exception");
        }
        String listing = fifa.toString();

        // Assert
        assertTrue(listing.contains(">MESSI"));
        assertTrue(listing.contains(">ARGENTINA"));
    }
    
    @Test
    public void shouldAddPlayerWhenNumericInputsContainLeadingOrTrailingSpaces(){
        // Arrange
        Fifa fifa = new Fifa();
        int initialParticipants = fifa.numberParticipants();

        // Act
        try {
            fifa.addPlayer("MESSI-SPACE", " 1900 ", "D", " 1909209 ", " Inter ");
        } catch (FifaException e) {
            fail("Threw an exception");
        }

        // Assert
        assertEquals(initialParticipants + 1, fifa.numberParticipants());
        assertTrue(fifa.toString().contains(">MESSI-SPACE"));
    }

}