package fr.eni.carsharingaire.pojo;

public class Fields {

    private String code_postal;

    private String stationnement_velo_securise;

    private String commune;

    private String exploitant;

    private String idobj;

    private String service_velo;

    private String nom_complet;

    private String stationnement_velo;

    private String presentation;

    private String conditions_d_acces;

    private String capacite_voiture;

    private String adresse;

    private String[] location;

    private String site_web;

    public String getCode_postal ()
    {
        return code_postal;
    }

    public void setCode_postal (String code_postal)
    {
        this.code_postal = code_postal;
    }

    public String getStationnement_velo_securise ()
    {
        return stationnement_velo_securise;
    }

    public void setStationnement_velo_securise (String stationnement_velo_securise)
    {
        this.stationnement_velo_securise = stationnement_velo_securise;
    }

    public String getCommune ()
    {
        return commune;
    }

    public void setCommune (String commune)
    {
        this.commune = commune;
    }

    public String getExploitant ()
    {
        return exploitant;
    }

    public void setExploitant (String exploitant)
    {
        this.exploitant = exploitant;
    }

    public String getIdobj ()
    {
        return idobj;
    }

    public void setIdobj (String idobj)
    {
        this.idobj = idobj;
    }

    public String getService_velo ()
    {
        return service_velo;
    }

    public void setService_velo (String service_velo)
    {
        this.service_velo = service_velo;
    }

    public String getNom_complet ()
    {
        return nom_complet;
    }

    public void setNom_complet (String nom_complet)
    {
        this.nom_complet = nom_complet;
    }

    public String getStationnement_velo ()
    {
        return stationnement_velo;
    }

    public void setStationnement_velo (String stationnement_velo)
    {
        this.stationnement_velo = stationnement_velo;
    }

    public String getPresentation ()
    {
        return presentation;
    }

    public void setPresentation (String presentation)
    {
        this.presentation = presentation;
    }

    public String getConditions_d_acces ()
    {
        return conditions_d_acces;
    }

    public void setConditions_d_acces (String conditions_d_acces)
    {
        this.conditions_d_acces = conditions_d_acces;
    }

    public String getCapacite_voiture ()
    {
        return capacite_voiture;
    }

    public void setCapacite_voiture (String capacite_voiture)
    {
        this.capacite_voiture = capacite_voiture;
    }

    public String getAdresse ()
    {
        return adresse;
    }

    public void setAdresse (String adresse)
    {
        this.adresse = adresse;
    }

    public String[] getLocation ()
    {
        return location;
    }

    public void setLocation (String[] location)
    {
        this.location = location;
    }

    public String getSite_web ()
    {
        return site_web;
    }

    public void setSite_web (String site_web)
    {
        this.site_web = site_web;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code_postal = "+code_postal+", stationnement_velo_securise = "+stationnement_velo_securise+", commune = "+commune+", exploitant = "+exploitant+", idobj = "+idobj+", service_velo = "+service_velo+", nom_complet = "+nom_complet+", stationnement_velo = "+stationnement_velo+", presentation = "+presentation+", conditions_d_acces = "+conditions_d_acces+", capacite_voiture = "+capacite_voiture+", adresse = "+adresse+", location = "+location+", site_web = "+site_web+"]";
    }

}
