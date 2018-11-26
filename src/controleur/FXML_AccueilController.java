package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FXML_AccueilController implements Initializable 
{

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }    
    @FXML
    public void handleGestion()
    {
         try
            {
              FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FenFXML_GestionSession.fxml"));  
              AnchorPane page=(AnchorPane) loader.load();
              Stage dialogStage = new Stage();
              Scene scene = new Scene(page);
              dialogStage.setScene(scene);
              dialogStage.showAndWait();
            }
             catch(IOException ioe)
            {

              System.out.println("ERREUR chargement boite dialogue:" + ioe.getMessage());

            }
    }
    @FXML
    public void handleInscription()
    {
         try
            {
              FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FenFXML_Inscription.fxml"));  
              AnchorPane page=(AnchorPane) loader.load();
              Stage dialogStage = new Stage();
              Scene scene = new Scene(page);
              dialogStage.setScene(scene);
              dialogStage.showAndWait();
            }
             catch(IOException ioe)
            {

              System.out.println("ERREUR chargement boite dialogue:" + ioe.getMessage());

            }
    }

}
