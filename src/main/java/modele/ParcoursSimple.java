package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe implémentant l'algorithme de parcours simple (Algo 1)
 * qui suit l'ordre vendeur → acheteur pour générer un itinéraire.
 */
public class ParcoursSimple {
    private CarteGraph carte;

    public ParcoursSimple(CarteGraph carte) {
        this.carte = carte;
    }

    /**
     * Génère un itinéraire simple en suivant l'ordre des ventes (vendeur → acheteur)
     * @param scenario Le scénario contenant les ventes à effectuer
     * @return Liste des villes à visiter dans l'ordre
     */
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
     * Calcule la distance totale d'un itinéraire
     * @param itineraire Liste des villes à visiter dans l'ordre
     * @return Distance totale en kilomètres
     */
    public int calculerDistanceTotale(List<Ville> itineraire) {
        int distanceTotale = 0;

        for (int i = 0; i < itineraire.size() - 1; i++) {
            Ville depart = itineraire.get(i);
            Ville arrivee = itineraire.get(i + 1);
            int distance = carte.getDistance(depart, arrivee);

            if (distance == Integer.MAX_VALUE) {
                // Si aucune distance n'est trouvée, utiliser une valeur par défaut de 50 km
                System.out.println("Avertissement: Distance non trouvée entre " + depart.getNom() +
                                  " et " + arrivee.getNom() + ". Utilisation d'une distance par défaut de 50 km.");
                distance = 50;
            }

            distanceTotale += distance;
        }

        return distanceTotale;
    }
}
