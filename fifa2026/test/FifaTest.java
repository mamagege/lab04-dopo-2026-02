package test;

import domain.Fifa;
import org.junit.Test;

import static org.junit.Assert.*;

public class FifaTest {

    @Test
    public void shouldAddAPlayerAndATeamInDomainLayer(){
        // Arrange
        Fifa fifa = new Fifa();
        
        int initialParticipants = fifa.numberParticipants();
        

        // Act
        fifa.addPlayer("MESSI", "1420", "D", "15000000", "Inter");
        fifa.addTeam("ARGENTINA", "1620", "J", "Scaloni", "Azul-blanco", "MESSI");

        // Assert
        assertEquals(initialParticipants + 2, fifa.numberParticipants());
    }

    @Test
    public void shouldListParticipantsIncludingRecentlyAddedEntities(){
        // Arrange
        Fifa fifa = new Fifa();
        fifa.addPlayer("MESSI", "1420", "D", "15000000", "Inter");
        fifa.addTeam("ARGENTINA", "1620", "J", "Scaloni", "Azul-blanco", "MESSI");

        // Act
        String listing = fifa.toString();

        // Assert
        assertTrue(listing.contains("MESSI"));
        assertTrue(listing.contains("ARGENTINA"));
    }

    @Test
    public void shouldSearchByPrefixWithoutIndexErrorsAndReturnMatches(){
        // Arrange
        Fifa fifa = new Fifa();
        fifa.addPlayer("MESSI", "1420", "D", "15000000", "Inter");
        fifa.addTeam("ARGENTINA", "1620", "J", "Scaloni", "Azul-blanco", "MESSI");

        // Act
        String result = fifa.search("AR");

        // Assert
        assertTrue(result.contains(">ARGENTINA"));
    }

    @Test
    public void acceptanceShouldSupportAddAndListFlowFromWorkshopScenario(){
        // Arrange
        Fifa fifa = new Fifa();

        // Act
        fifa.addPlayer("MESSI", "1420", "D", "15000000", "Inter");
        fifa.addTeam("ARGENTINA", "1620", "J", "Scaloni", "Azul-blanco", "MESSI");
        String listing = fifa.toString();

        // Assert
        assertTrue(listing.contains(">MESSI"));
        assertTrue(listing.contains(">ARGENTINA"));
        assertTrue(listing.contains("8 elementos"));
    }
}