package controleur;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modele.GestionSql;
import modele.Session;

public class FXML_GestionSessionController implements Initializable 
    {
        @FXML
        TableView<Session> tableSessions;
        @FXML
        TableColumn<Session,String> colID;
        @FXML
        TableColumn<Session,String> colLibelle;
        @FXML
        TableColumn<Session,String> colDate;
        @FXML
        TableColumn<Session,String> colNbPlaces;
        @FXML
        TableColumn<Session,String> colNbInscrits;
        static ObservableList<Session> lesSessions = FXCollections.observableArrayList();

        @Override
        public void initialize(URL url, ResourceBundle rb) 
            {
                lesSessions.removeAll(lesSessions);
                colID.setCellValueFactory(new PropertyValueFactory<Session,String>("id"));
                colLibelle.setCellValueFactory(new PropertyValueFactory<Session,String>("libFormation"));
                colDate.setCellValueFactory(new PropertyValueFactory<Session,String>("date_debut"));
                colNbPlaces.setCellValueFactory(new PropertyValueFactory<Session,String>("nb_places"));
                colNbInscrits.setCellValueFactory(new PropertyValueFactory<Session,String>("nb_inscrits"));
                lesSessions = GestionSql.getLesSessions();
                tableSessions.setItems(lesSessions);
            }    
        @FXML
        public void handleDetails()
        {
            if (tableSessions.getSelectionModel().getSelectedItem() == null)
            {
                System.out.println("choisissez une session");
            }
            else
            {
            Session sessionSelectionnee = tableSessions.getSelectionModel().getSelectedItem();
            if (sessionSelectionnee.getLibFormation() != null)
            {
                try
                 {
                    MainApp.setMaSessionSelectionnee(sessionSelectionnee);
                    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FenFXML_DetailsSessions.fxml"));  
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
            else
            {
                System.out.println("choisissez une session");
            }
            }
        }
}
    
