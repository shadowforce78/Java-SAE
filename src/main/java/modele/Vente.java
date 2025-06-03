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

    /**
     * Retourne une représentation formatée du vendeur: "Pseudo (Ville)"
     * @return String au format "Pseudo (Ville)"
     */
    public String getVendeurFormate() {
        if (vendeur != null) {
            return vendeur.getPseudo() + " (" + vendeur.getVille().getNom() + ")";
        } else if (villeVendeur != null) {
            return villeVendeur;
        }
        return "";
    }

    /**
     * Retourne une représentation formatée de l'acheteur: "Pseudo (Ville)"
     * @return String au format "Pseudo (Ville)"
     */
    public String getAcheteurFormate() {
        if (acheteur != null) {
            return acheteur.getPseudo() + " (" + acheteur.getVille().getNom() + ")";
        } else if (villeAcheteur != null) {
            return villeAcheteur;
        }
        return "";
    }

    @Override
    public String toString() {
        return vendeur.getPseudo() + " → " + acheteur.getPseudo();
    }
}
