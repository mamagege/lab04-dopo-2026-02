package presentation; 
 
import domain.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * @version ECI 2025
 */
public class FifaGUI extends JFrame{


    private static final Dimension PREFERRED_DIMENSION =
                         new Dimension(700,700);

    private Fifa fifa;

    /*List*/
    private JButton buttonList;
    private JButton buttonRestartList;
    private JTextArea textDetails;
    
    /*Add*/
    private JTextField name;
    private JTextField position;   
    private JTextField minutes;
    private JTextField managerClub;
    private JTextField uniform;
    private JTextField value;
    private JTextArea  players;
    private JButton buttonAdd;
    private JButton buttonRestartAdd;
   
    /*Search*/
    private JTextField textSearch;
    private JTextArea textResults;
    
    
    private FifaGUI(){
        fifa=new Fifa();
        prepareElements();
        prepareActions();
    }


    private void prepareElements(){
        setTitle("Fifa 2026");
        name = new JTextField(50);
        position = new JTextField(50);
        minutes = new JTextField(50);
        managerClub = new JTextField(50);
        uniform = new JTextField(50);
        value = new JTextField(50);
        players = new JTextArea(10, 50);
        players.setLineWrap(true);
        players.setWrapStyleWord(true);
        
        JTabbedPane etiquetas = new JTabbedPane();
        etiquetas.add("Listar",   prepareAreaList());
        etiquetas.add("Adicionar",  prepareAreaAdd());
        etiquetas.add("Buscar", prepareSearchArea());
        getContentPane().add(etiquetas);
        setSize(PREFERRED_DIMENSION);
        
    }


    /**
     * Prepare list area
     * @return 
     */
    private JPanel prepareAreaList(){

        textDetails = new JTextArea(10, 50);
        textDetails.setEditable(false);
        textDetails.setLineWrap(true);
        textDetails.setWrapStyleWord(true);
        JScrollPane scrollArea =
                new JScrollPane(textDetails,
                                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                                
        JPanel  botones = new JPanel();
        buttonList = new JButton("Listar");
        buttonRestartList = new JButton("Limpiar");
        botones.add(buttonList);
        botones.add(buttonRestartList);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollArea, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
     }
     
    /**
     * Prepare adiction area
     * @return adiction area
     */
    private JPanel prepareAreaAdd(){

        JPanel fields = new JPanel(new GridLayout(13,1));
        fields.add(new JLabel("Nombre"));
        fields.add(name);
        fields.add(new JLabel("Posicion"));
        fields.add(position);
        fields.add(new JLabel("Minutos jugados"));
        fields.add(minutes);        
        fields.add(new JLabel("Director (para equipo) o Club (para jugador)"));
        fields.add(managerClub); 
        fields.add(new JLabel("Uniforme (solo para equipos)"));
        fields.add(uniform); 
        fields.add(new JLabel("Valor de mercado (solo para jugadores)"));
        fields.add(value); 
        fields.add(new JLabel("Jugadores (solo para equipos)"));
         
        JPanel textDetailsPanel = new JPanel();
        textDetailsPanel.setLayout(new BorderLayout());
        textDetailsPanel.add(fields, BorderLayout.NORTH);
        textDetailsPanel.add(players, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        buttonAdd = new JButton("Adicionar");
        buttonRestartAdd = new JButton("Limpiar");

        botones.add(buttonAdd);
        botones.add(buttonRestartAdd);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textDetailsPanel, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    



   /**
     * 
     * @return 
     */
    private JPanel prepareSearchArea(){

        Box busquedaEtiquetaArea = Box.createHorizontalBox();
        busquedaEtiquetaArea.add(new JLabel("Buscar", JLabel.LEFT));
        busquedaEtiquetaArea.add(Box.createGlue());
        textSearch = new JTextField(50);
        Box busquedaArea = Box.createHorizontalBox();
        busquedaArea.add(busquedaEtiquetaArea);
        busquedaArea.add(textSearch);
        
        textResults = new JTextArea(10,50);
        textResults.setEditable(false);
        textResults.setLineWrap(true);
        textResults.setWrapStyleWord(true);
        JScrollPane scrollArea = new JScrollPane(textResults,
                                     JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                     JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel buttonListea = new JPanel();
        buttonListea.setLayout(new BorderLayout());
        buttonListea.add(busquedaArea, BorderLayout.NORTH);
        buttonListea.add(scrollArea, BorderLayout.CENTER);

        return buttonListea;
    }


    private void prepareActions(){
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                setVisible(false);
                System.exit(0);
            }
        });
        
        /*List*/
        buttonList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                actionList();
            }
        });

        buttonRestartList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                textDetails.setText("");
            }
        });
        
        /*Add*/
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                actionAdd();                    
            }
        });
        
        buttonRestartAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                name.setText("");
                position.setText("");
                minutes.setText("");
                managerClub.setText("");
                uniform.setText("");
                value.setText("");
                players.setText("");
            }
        });
        
        /*Search*/
        textSearch.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent ev){
                actionSearch();
            }
           
            public void insertUpdate(DocumentEvent ev){
                actionSearch();
            }
            
            public void removeUpdate(DocumentEvent ev){
                actionSearch();
            }
        });
    }    

    
    private void actionList(){
        textDetails.setText(fifa.toString());
    }
    
    private void  actionAdd(){
        if (players.getText().trim().equals("")){
            fifa.addPlayer(name.getText(),position.getText(),minutes.getText(),managerClub.getText(),value.getText());
        }else{ 
            fifa.addTeam(name.getText(),position.getText(), minutes.getText(), managerClub.getText(),uniform.getText(),players.getText());
        }
    }

    private void actionSearch(){
        String patronBusqueda=textSearch.getText();
        String answer = "";
        if(patronBusqueda.length() > 0) {
            answer = fifa.search(patronBusqueda);
        }
        textResults.setText(answer);
    } 
    
   public static void main(String args[]){
       FifaGUI gui=new FifaGUI();
       gui.setVisible(true);
   }    
}
