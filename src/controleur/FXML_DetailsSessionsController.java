/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Client;
import modele.CreationPDF;
import modele.DetailSession;
import modele.GestionSql;
import modele.Session;

public class FXML_DetailsSessionsController implements Initializable 
{
    CreationPDF creerPDF;
    String libelle;
    Session session;
    ObservableList<Client> lesClients = FXCollections.observableArrayList();
    DetailSession sessionRecup;
    @FXML
    TextField textLibelle;
    @FXML
    private TableColumn<Client, String> colNomPrenom;
    @FXML
    TextField textNiveau;
    @FXML
    TableView tableClient;
    @FXML
    TextField textType;
    @FXML
    TextField textDescription;
    @FXML
    TextField textDiplomante;
    @FXML
    TextField textDateDebut;
    @FXML
    TextField textDuree;
    @FXML
    TextField textCoutRevient;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        session = MainApp.getMaSessionSelectionnee();
        sessionRecup = GestionSql.getLaSession(session.getId());
        lesClients = GestionSql.getLesClientsSession(session.getId());
        textLibelle.setText(session.getLibFormation());
        textLibelle.setEditable(false);
        textNiveau.setText(sessionRecup.getNiveau());
        textNiveau.setEditable(false);
        textType.setText(sessionRecup.getType_form());
        textType.setEditable(false);
        textDescription.setText(sessionRecup.getDescription());
        textDescription.setEditable(false);
        textDateDebut.setText(session.getDate_debut().toString());
        textDateDebut.setEditable(false);
        textDuree.setText(Integer.toString(sessionRecup.getDuree()));
        textDuree.setEditable(false);
        textCoutRevient.setText(Integer.toString(sessionRecup.getCoutRevient()));
        textCoutRevient.setEditable(false);
        colNomPrenom.setCellValueFactory(new PropertyValueFactory<Client,String>("nom"));
        if (sessionRecup.getDiplomante() == 0)
            {
                textDiplomante.setText("non");
            }
        else
            {
                textDiplomante.setText("oui");
            }
        textDiplomante.setEditable(false);
        tableClient.setItems(lesClients);
        
    } 
    @FXML
    public void handlePDF()
    {
         if (creerPDF.creation(lesClients,textLibelle.getText(),textDescription.getText(),textDateDebut.getText()) == true)
         {
             System.out.println("Réussite");
         }
         else
         {
             System.out.println("Echec");
         }
    }
    
}
