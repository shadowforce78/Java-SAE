package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe implémentant l'algorithme de parcours simple (Algo 1)
 * qui suit l'ordre vendeur → acheteur pour générer un itinéraire.
 */
public class ParcoursSimple implements IAlgorithme {
    private CarteGraph carte;

    public ParcoursSimple(CarteGraph carte) {
        this.carte = carte;
    }

    /**
     * Génère un itinéraire simple en suivant l'ordre des ventes (vendeur → acheteur)
     * @param scenario Le scénario contenant les ventes à effectuer
     * @return Liste des villes à visiter dans l'ordre
     */
    @Override
    public List<Ville> genererItineraire(Scenario scenario) {
        List<Ville> itineraire = new ArrayList<>();
        List<Vente> ventes = scenario.getVentes();

        if (ventes.isEmpty()) {
            return itineraire;
        }

        // On commence par la ville du premier vendeur
        Ville villeActuelle = ventes.get(0).getVendeur().getVille();
        itineraire.add(villeActuelle);

        // Pour chaque vente, on ajoute la ville du vendeur puis celle de l'acheteur
        for (Vente vente : ventes) {
            Ville villeVendeur = vente.getVendeur().getVille();
            Ville villeAcheteur = vente.getAcheteur().getVille();

            // Si nous ne sommes pas déjà dans la ville du vendeur, on s'y rend
            if (!villeActuelle.equals(villeVendeur)) {
                itineraire.add(villeVendeur);
                villeActuelle = villeVendeur;
            }

            // On va ensuite à la ville de l'acheteur
            itineraire.add(villeAcheteur);
            villeActuelle = villeAcheteur;
        }

        return itineraire;
    }

    /**
     * Calcule la distance totale d'un itinéraire.
     * @param itineraire Liste des villes à visiter dans l'ordre
     * @return La distance totale en kilomètres
     */
    @Override
    public int calculerDistanceTotale(List<Ville> itineraire) {
        int distanceTotale = 0;

        // On parcourt l'itinéraire et on additionne les distances entre chaque paire de villes consécutives
        for (int i = 0; i < itineraire.size() - 1; i++) {
            Ville villeActuelle = itineraire.get(i);
            Ville villeSuivante = itineraire.get(i + 1);

            int distance = carte.getDistance(villeActuelle, villeSuivante);

            // Si la distance n'existe pas (ou est invalide), on lève une exception
            if (distance == Integer.MAX_VALUE) {
                throw new IllegalStateException("Aucun chemin direct entre " + villeActuelle + " et " + villeSuivante);
            }

            distanceTotale += distance;
        }

        return distanceTotale;
    }

    /**
     * Retourne le nom de l'algorithme.
     * @return Le nom de l'algorithme
     */
    @Override
    public String getNom() {
        return "Parcours Simple";
    }
}
