package fr.eni.carsharingaire.pojo;

public class Parking {
    private String adresse;
    private int capaciteVoiture;
    private String codePostale;
    private String commune;
    private String conditionAcces;
    private String exploitant;
    private Double latitude;
    private Double longitude;
    private String nom;
    private String presentation;
    private Boolean serviceVelo;
    private Boolean stationnementVelo;
    private Boolean stationnementVeloSecurisee;
    private String siteWeb;

    public Parking() {
    }

    public Parking(String adresse, int capaciteVoiture, String codePostale, String commune, String conditionAcces, String exploitant, Double latitude, Double longitude, String nom, String presentation, Boolean serviceVelo, Boolean stationnementVelo, Boolean stationnementVeloSecurisee, String siteWeb) {
        this.adresse = adresse;
        this.capaciteVoiture = capaciteVoiture;
        this.codePostale = codePostale;
        this.commune = commune;
        this.conditionAcces = conditionAcces;
        this.exploitant = exploitant;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nom = nom;
        this.presentation = presentation;
        this.serviceVelo = serviceVelo;
        this.stationnementVelo = stationnementVelo;
        this.stationnementVeloSecurisee = stationnementVeloSecurisee;
        this.siteWeb = siteWeb;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCapaciteVoiture() {
        return capaciteVoiture;
    }

    public void setCapaciteVoiture(int capaciteVoiture) {
        this.capaciteVoiture = capaciteVoiture;
    }

    public String getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getConditionAcces() {
        return conditionAcces;
    }

    public void setConditionAcces(String conditionAcces) {
        this.conditionAcces = conditionAcces;
    }

    public String getExploitant() {
        return exploitant;
    }

    public void setExploitant(String exploitant) {
        this.exploitant = exploitant;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public Boolean getServiceVelo() {
        return serviceVelo;
    }

    public void setServiceVelo(Boolean serviceVelo) {
        this.serviceVelo = serviceVelo;
    }

    public Boolean getStationnementVelo() {
        return stationnementVelo;
    }

    public void setStationnementVelo(Boolean stationnementVelo) {
        this.stationnementVelo = stationnementVelo;
    }

    public Boolean getStationnementVeloSecurisee() {
        return stationnementVeloSecurisee;
    }

    public void setStationnementVeloSecurisee(Boolean stationnementVeloSecurisee) {
        this.stationnementVeloSecurisee = stationnementVeloSecurisee;
    }

    public String getSiteWeb(){
        return this.siteWeb;
    }

    public void setSiteWeb(String siteWeb){
        this.siteWeb = siteWeb;
    }

}
