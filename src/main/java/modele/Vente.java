package modele;

public class Vente {
    private Membre vendeur;
    private Membre acheteur;

    public Vente(Membre vendeur, Membre acheteur) {
        this.vendeur = vendeur;
        this.acheteur = acheteur;
    }

    public Membre getVendeur() {
        return vendeur;
    }
    public void setVendeur(Membre vendeur) {
        this.vendeur = vendeur;
    }
    public Membre getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(Membre acheteur) {
        this.acheteur = acheteur;
    }

    @Override
    public String toString() {
        return vendeur.getPseudo() + " → " + acheteur.getPseudo();
    }
}
