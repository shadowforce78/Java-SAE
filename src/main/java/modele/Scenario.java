package modele;

import java.util.List;

public class Scenario {
    private String nom;                    // Nom ou identifiant du scénario
    private List<Vente> ventes;           // Liste des ventes à effectuer


    public Scenario(String nom, List<Vente> ventes) {
        this.nom = nom;
        this.ventes = ventes;
    }

    public void ajouterVente(Vente vente) {
        ventes.add(vente);
    }

    public void supprimerVente(Vente vente) {
        ventes.remove(vente);
    }

    public List<Vente> getVentes() {
        return ventes;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scénario : ").append(nom).append("\n");
        for (Vente v : ventes) {
            sb.append("  ").append(v).append("\n");
        }
        return sb.toString();
    }


}
