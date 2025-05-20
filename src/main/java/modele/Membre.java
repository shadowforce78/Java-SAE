package modele;

public class Membre {
    private String pseudo;
    private Ville ville;

    public Membre(String pseudo, Ville ville) {
        this.pseudo = pseudo;
        this.ville = ville;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return pseudo + "("+ ville.getNom()+ ")";
    }
}
