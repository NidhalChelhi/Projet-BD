package maquette.model;

// Sample Lieu class definition
public class Lieu {
    private String nom;
    private int capacite;
    private String adresse;
    private String ville;

    public Lieu(String nom, int capacite, String adresse, String ville) {
        this.nom = nom;
        this.capacite = capacite;
        this.adresse = adresse;
        this.ville = ville;
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville;
    }
}