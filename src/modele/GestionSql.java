/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import javafx.collections.FXCollections;
import sql.GestionBdd;

import javafx.collections.ObservableList;

public class GestionSql
{
    static ObservableList<Session> lesSessions = FXCollections.observableArrayList();
    static ObservableList<Session> lesSessions2 = FXCollections.observableArrayList();
    //Requete permettant de retourner l'ensemble des clients
    public static ObservableList<Client> getLesClients()
    {
        Connection conn;
        Statement stmt1;
        Client monClient;
        ObservableList<Client> lesClients = FXCollections.observableArrayList();
        try
        {
            // On prévoit 2 connexions à la base
            stmt1 = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            
            // Liste des clients qui "ont un plan de formation"
            String req = "select distinct c.id, statut_id, nom, password, adresse, cp, ville, email, nbhcpta, nbhbur from client c, plan_formation p "
            + "where c.id = p.id order by c.id";
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt1,req);
            while (rs.next())
            {
                monClient = new Client(rs.getInt("id"), rs.getInt("statut_id"), rs.getInt("nbhcpta"), rs.getInt("nbhbur"), rs.getString("nom"), rs.getString("password"), rs.getString("adresse"), rs.getString("cp"), rs.getString("ville"), rs.getString("email"));
                lesClients.add(monClient);
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getLesClients : " + se.getMessage());
        }
        return lesClients;
    }
    
    //Requête permettant de  retourner les sessions autorisées pour le client sélectionné
    public static ObservableList<Session> getLesSessions(int client_id)
    {
        Connection conn;
        Statement stmt1;
        Session maSession;
        
        try
        {
            // On prévoit 2 connexions à la base
            stmt1 = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            
            // Sélection des sessions autorisées pour le client choisi
            String req = "select c.nom, s.id, f.libelle, f.niveau, date_debut, duree, nb_places, nb_inscrits, coutrevient ";
            req += "from session_formation s, client c, plan_formation p, formation f ";
            req += "where c.id = '" + client_id + "' ";
            req += "and p.client_id = c.id and nb_places > nb_inscrits ";
            req += "and p.formation_id = f.id ";
            req += "and s.formation_id = f.id ";
            // et date supérieure à la date du jour
            req += "and close = 0 and effectue = 0 and s.id Not In ";
            req += "(Select session_formation_id From inscription Where id = '" + client_id + "')";
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt1,req);
            System.out.println(req);
            while (rs.next())
            {
                // A MODIFIER
                maSession = new Session(rs.getInt("id"), rs.getString("libelle"), rs.getDate("date_debut"), rs.getInt("nb_places"), rs.getInt("nb_inscrits"));
                lesSessions.add(maSession);
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getLesSessions : " + se.getMessage());
        }
        return lesSessions;
    }
    
    //Requête permettant l'insertion de l'inscription dans la table inscription et
    //la mise à jour de la table session_formation (+1 inscrit) et
    //la mise à jour de la table plan_formation (effectue passe à 1)
    public static void insereInscription(int matricule, int session_formation_id)
        {
            Statement stmt1;
            GregorianCalendar dateJour = new GregorianCalendar();
            String ddate = dateJour.get(GregorianCalendar.YEAR) + "-" + (dateJour.get(GregorianCalendar.MONTH) + 1) + "-" + dateJour.get(GregorianCalendar.DATE);
            // Insertion dans la table inscription
            String req = "Insert into inscription(client_id, session_formation_id, date_inscription) values (" + matricule;
            req += ", " + session_formation_id + ",'" + ddate + "')";
            // M.A.J de la table session_formation (un inscrit de plus)
            String req2 = "Update session_formation set nb_inscrits = nb_inscrits +1 Where id = " + session_formation_id;
            // Récupération du numéro de la session concernée
            String req3 = "Select formation_id from session_formation where id = " + session_formation_id;
            stmt1 = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor", "localhost", "root", "");
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt1, req3);
            int numForm=0;
            try
            {
                rs.first();
                numForm = rs.getInt(1);
            }
            catch(Exception e)
            {
                System.out.println("Erreur requete3 " + e.getMessage());
            }
            // M.A.J de la table plan_formation (effectue passe à 1 pour le client et la session concernés)
            String req4 = "Update plan_formation set effectue = 1 Where client_id = " + matricule;
            req4 += " And formation_id = " + numForm;
            int nb1 = GestionBdd.envoiRequeteLID(stmt1, req);
            int nb2 = GestionBdd.envoiRequeteLID(stmt1, req2);
            int nb3 = GestionBdd.envoiRequeteLID(stmt1, req4);
        }
    public static ObservableList<Session> getLesSessions()
        {
            try
                {
                    LocalDate dateaujourdhui = LocalDate.now();
                    Statement stmt = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor", "localhost", "root", "");
                    Session maSession;
                    String requete = "SELECT session_formation.id,formation.libelle,session_formation.date_debut,nb_places,nb_inscrits from session_formation,formation where date_debut > "+dateaujourdhui+" and session_formation.formation_id =  formation.id order by date_debut asc";
                    ResultSet rs = GestionBdd.envoiRequeteLMD(stmt, requete);
                    while(rs.next())
                        {
                            maSession = new Session(rs.getInt("id"),rs.getString("libelle"),rs.getDate("date_debut"),rs.getInt("nb_places"),rs.getInt("nb_inscrits"));
                            lesSessions2.add(maSession);
                        }                 
                }
            catch(Exception e)
                {
                    System.out.println("Erreur requete3 " + e.getMessage());
                }
            return lesSessions2;
        }
    public static DetailSession getLaSession(int idSession)
        {
            DetailSession maSession = new DetailSession();
            try
                {
                    Statement stmt = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor", "localhost", "root", "");          
                    String requete = "SELECT * FROM `formation` WHERE id =" + idSession;
                    ResultSet rs = GestionBdd.envoiRequeteLMD(stmt, requete);
                    rs.next();
                            maSession = new DetailSession(rs.getString("libelle"),rs.getString("niveau"),rs.getString("type_form"),rs.getString("description"),rs.getInt("diplomante"),rs.getInt("duree"),rs.getInt("coutrevient"));           
                }
            catch(Exception e)
                {
                    System.out.println("Erreur requete3 " + e.getMessage());
                }
            return maSession;
        }
    public static ObservableList<Client> getLesClientsSession(int idSession)
    {
       ObservableList<Client> lesClients = FXCollections.observableArrayList();
       Client unClient = new Client();
       try
                {
                    Statement stmt = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor", "localhost", "root", "");          
                   String req = "SELECT client.* FROM `inscription`,client WHERE session_formation_id = "+idSession+" and client_id = client.id";
                    ResultSet rs = GestionBdd.envoiRequeteLMD(stmt, req);
                    while(rs.next())
                    {
                            unClient = new Client(rs.getInt("id"), rs.getInt("statut_id"), rs.getInt("nbhcpta"), rs.getInt("nbhbur"), rs.getString("nom"), rs.getString("password"), rs.getString("adresse"), rs.getString("cp"), rs.getString("ville"), rs.getString("email"));           
                            lesClients.add(unClient);
                    }
                }
            catch(Exception e)
                {
                    System.out.println("Erreur requete3 " + e.getMessage());
                }
       return lesClients;
    }
}
