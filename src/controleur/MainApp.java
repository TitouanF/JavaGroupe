/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modele.Client;
import modele.Session;
import javafx.event.ActionEvent;

/**
 *
 * @author Philippe
 */
public class MainApp extends Application
{
    private Stage primaryStage;
    private Stage secondaryStage;
    
    // Pour conserver la session sélectionnée dans le TableView de la fenêtre inscription
    private static Session maSessionSelectionne;
    // Pour conserver le client sélectionné dans le ComboBox de la fenêtre inscription
    private static Client monClientSelectionne;
    private AnchorPane rootLayout;
    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion des inscriptions aux sessions de formations");
        try
        {          
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FenFXML_Accueil.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            FXML_AccueilController controleur2 = loader.getController();
            primaryStage.show();
        }
        catch (IOException e)
        {
            System.out.println("Erreur chargement fenetre principale : " + e.getMessage());
        }
    }
    public void creationFenConfirm()
    {
        
    }
    
    // Getter et Setter pour l'item selectionné dans le tableView des sessions (fenetre Inscription)
    public static void setMaSessionSelectionnee(Session maSession)
    {
        maSessionSelectionne = maSession;
    }
    public static Session getMaSessionSelectionnee()
    {
        return maSessionSelectionne;
    }
    // Getter et Setter pour l'item selectionné dans le ComboBox des clients (fenetre Inscription)
    public static void setMonClientSelectionne(Client monClient)
    {
        monClientSelectionne = monClient;
    }
    public static Client getMonClientSelectionne()
    {
        return monClientSelectionne;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
