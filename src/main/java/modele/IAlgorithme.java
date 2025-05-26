package modele;

import java.util.List;

/**
 * Interface commune pour tous les algorithmes de parcours.
 * Permet d'uniformiser l'utilisation des différents algorithmes.
 */
public interface IAlgorithme {

    /**
     * Génère un itinéraire à partir d'un scénario donné.
     * @param scenario Le scénario contenant les ventes à effectuer
     * @return Liste des villes à visiter dans l'ordre
     */
    List<Ville> genererItineraire(Scenario scenario);

    /**
     * Calcule la distance totale d'un itinéraire donné.
     * @param itineraire Liste des villes à visiter dans l'ordre
     * @return La distance totale en kilomètres
     */
    int calculerDistanceTotale(List<Ville> itineraire);

    /**
     * Retourne le nom de l'algorithme.
     * @return Le nom de l'algorithme
     */
    String getNom();
}
