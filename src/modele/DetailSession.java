package modele;

public class DetailSession 
{
    String libelle;
    String niveau;
    String type_form;
    String description;
    int diplomante;
    int duree;
    int coutRevient;
    public DetailSession(String nouvLibelle,String nouvNiveau,String nouvType_Form,String nouvDescription,int nouvDiplomante,int nouvDuree,int nouvCoutRevient)
    {
        libelle = nouvLibelle;
        niveau =  nouvNiveau;
        type_form = nouvType_Form;
        description = nouvDescription;
        diplomante = nouvDiplomante;
        duree = nouvDuree;
        coutRevient = nouvCoutRevient;
    }
    public DetailSession()
    {
        
    }
    public String getLibelle()
    {
        return libelle;
    }
    public String getNiveau()
    {
        return niveau;
    }
    public String getType_form()
    {
        return type_form;
    }
    public String getDescription()
    {
        return description;
    }
    public int getDiplomante()
    {
        return diplomante ;
    }
    public int getDuree()
    {
        return duree;
    }
    public int getCoutRevient()
    {
        return coutRevient;
    }
}
