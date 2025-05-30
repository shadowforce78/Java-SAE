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
     * et en commençant et terminant à Velizy.
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

        // On récupère la ville Velizy comme point de départ et d'arrivée
        Ville velizy = null;
        for (Ville ville : carte.getToutesLesVilles()) {
            if (ville.getNom().equalsIgnoreCase("Velizy")) {
                velizy = ville;
                break;
            }
        }

        if (velizy == null) {
            throw new IllegalStateException("La ville de Velizy n'a pas été trouvée dans la carte");
        }

        // On commence à Velizy
        Ville villeActuelle = velizy;
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

        // On termine à Velizy (sauf si on y est déjà)
        if (!villeActuelle.equals(velizy)) {
            itineraire.add(velizy);
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
