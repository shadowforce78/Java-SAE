package modele;

public class Vente {
    private Membre vendeur;
    private Membre acheteur;
    private String villeVendeur;
    private String villeAcheteur;

    public Vente(Membre vendeur, Membre acheteur) {
        this.vendeur = vendeur;
        this.acheteur = acheteur;
    }

    public Vente(String villeVendeur, String villeAcheteur){
        this.villeVendeur = villeVendeur;
        this.villeAcheteur = villeAcheteur;
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

    public String getVilleVendeur(){
        return villeVendeur;
    }

    public String getVilleAcheteur(){
        return villeAcheteur;
    }
    @Override
    public String toString() {
        return vendeur.getPseudo() + " → " + acheteur.getPseudo();
    }
}
