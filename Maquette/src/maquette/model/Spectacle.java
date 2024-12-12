package maquette.model;

public class Spectacle {

    private String titre;
    private String date;
    private String heure;
    private String duree;
    private int nombreSpectateurs;
    private String lieu;

    // Constructeur
    public Spectacle(String titre, String date, String heure, String duree, int nombreSpectateurs, String lieu) {
        this.titre = titre;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.nombreSpectateurs = nombreSpectateurs;
        this.lieu = lieu;
    }

    // Getters et Setters
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public int getNombreSpectateurs() {
        return nombreSpectateurs;
    }

    public void setNombreSpectateurs(int nombreSpectateurs) {
        this.nombreSpectateurs = nombreSpectateurs;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Override
    public String toString() {
        return "Spectacle{" +
                "titre='" + titre + '\'' +
                ", date='" + date + '\'' +
                ", heure='" + heure + '\'' +
                ", duree='" + duree + '\'' +
                ", nombreSpectateurs=" + nombreSpectateurs +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
