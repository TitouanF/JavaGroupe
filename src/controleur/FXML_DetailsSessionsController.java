/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import modele.Session;

public class FXML_DetailsSessionsController implements Initializable 
{
    String libelle;
    Session session;
    @FXML
    TextField textLibelle;
    @FXML
    TextField textNiveau;
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
        textLibelle.setText(session.getLibFormation());
        
    }    
    
}
