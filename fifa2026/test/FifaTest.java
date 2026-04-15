package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FifaTest {

    /**
     * Test de Adicionar en Dominio
     * */

    //Test para adicionar un jugador y un equipo correctamente.
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

    //Test para adicionar un jugador con un nombre ya existente.
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
    
    //Test para adicionar un jugador con valores numéricos no numéricos.
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
    
    //Test para adicionar un jugador con una posición no válida.
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
    

    //Test para adicionar cuando un jugador no existe al adicionar un equipo.
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

    //Test que verifica el numero de participantes.
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
    //Test que verifica la búsqueda por prefijo.
    @Test
    public void shouldSearchByPrefix(){
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
    


    /**Acceptance para List y Add en Dominio */



    @Test
    public void AcceptanceTestListAndAdd() {
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
    
    

    /**
     * Acceptance cases para adicionar en dominio:
     * 1) nombres duplicados
     * 2) valores no numéricos;
     * 3) posicion inválida;
     * 4) numero negativos:
     */

    @Test
    public void AcceptanceCaseTestAdd() {
        // Arrange
        Fifa fifa = new Fifa();

        // Act
        String duplicateNameError = tryAddPlayerAndGetError(fifa, "JAMES", "100", "M", "1000000", "ClubX");
        String nonNumericError = tryAddPlayerAndGetError(fifa, "NEW-10", "N/A", "D", "FIFTEEN", "Inter");
        String unexpectedValueError = tryAddPlayerAndGetError(fifa, "NEW-11", "300", "X", "1000", "Inter");
        String negativeValuesError = tryAddPlayerAndGetError(fifa, "NEG-01", "-10", "D", "-500", "Inter");

        // Assert
        assertEquals(FifaException.DUPLICATE_NAME, duplicateNameError);
        assertEquals(FifaException.INVALID_NUMBER, nonNumericError);
        assertEquals(FifaException.INVALID_POSITION, unexpectedValueError);
        assertEquals(FifaException.INVALID_NUMBER, negativeValuesError);
    }

    /**
     * Acceptance cases para List.
     */
    @Test
    public void AcceptanceCaseTestList() {
        // Arrange
        Fifa fifa = new Fifa();
        int initialParticipants = fifa.numberParticipants();
        String initialListing = fifa.toString();

        // Act
        tryAddPlayerAndGetError(fifa, "JAMES", "100", "M", "1000000", "ClubX");
        tryAddPlayerAndGetError(fifa, "NEW-20", "N/A", "D", "FIFTEEN", "Inter");
        tryAddPlayerAndGetError(fifa, "NEW-21", "300", "X", "1000", "Inter");
        tryAddPlayerAndGetError(fifa, "NEG-02", "-5", "D", "-1", "Inter");
        String listingAfterFailedAdds = fifa.toString();

        // Assert
        assertEquals(initialParticipants, fifa.numberParticipants());
        assertFalse(listingAfterFailedAdds.contains(">NEW-20"));
        assertFalse(listingAfterFailedAdds.contains(">NEW-21"));
        assertFalse(listingAfterFailedAdds.contains(">NEG-02"));
        assertTrue(listingAfterFailedAdds.contains(">JAMES"));
        assertEquals(initialListing, listingAfterFailedAdds);
    }

    private String tryAddPlayerAndGetError(Fifa fifa, String name, String minutes, String position, String value, String club) {
        try {
            fifa.addPlayer(name, minutes, position, value, club);
            fail("Did not throw exception");
            return null;
        } catch (FifaException e) {
            return e.getMessage();
        }
    }

}
