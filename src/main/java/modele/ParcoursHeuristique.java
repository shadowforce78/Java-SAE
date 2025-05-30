package modele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe implémentant l'algorithme de parcours heuristique (Algo 2)
 * qui utilise une approche gloutonne basée sur la distance minimale
 * tout en respectant la contrainte vendeur → acheteur pour chaque vente.
 */
public class ParcoursHeuristique implements IAlgorithme {

    private CarteGraph carte;

    public ParcoursHeuristique(CarteGraph carte) {
        this.carte = carte;
    }

    /**
     * Génère un itinéraire heuristique en utilisant une approche gloutonne.
     * L'algorithme essaie de minimiser la distance totale en choisissant
     * à chaque étape la prochaine destination la plus proche parmi les
     * destinations possibles (vendeurs non visités ou acheteurs dont le vendeur a
     * été visité).
     * L'itinéraire commence et se termine à Velizy.
     *
     * @param scenario Le scénario contenant les ventes à effectuer
     * @return Liste des villes à visiter dans l'ordre optimisé
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

        // Ensemble des ventes pour lesquelles le vendeur a été visité
        Set<Vente> venteursVisites = new HashSet<>();
        // Ensemble des ventes complètement terminées (vendeur ET acheteur visités)
        Set<Vente> ventesCompletes = new HashSet<>();

        // On commence à Velizy
        Ville villeActuelle = velizy;
        itineraire.add(villeActuelle);

        // Marquer toutes les ventes dont le vendeur est dans cette ville comme "vendeur visité"
        for (Vente vente : ventes) {
            if (vente.getVendeur().getVille().equals(villeActuelle)) {
                venteursVisites.add(vente);
            }
        }

        // Tant qu'il reste des ventes à compléter
        while (ventesCompletes.size() < ventes.size()) {
            Ville prochaineDest = null;
            int distanceMin = Integer.MAX_VALUE;
            Vente venteChoisie = null;
            boolean versAcheteur = false;

            // Chercher la prochaine destination la plus proche parmi :
            // 1. Les acheteurs dont le vendeur a été visité (priorité)
            // 2. Les vendeurs pas encore visités

            // D'abord, chercher parmi les acheteurs disponibles
            for (Vente vente : venteursVisites) {
                if (!ventesCompletes.contains(vente)) {
                    Ville villeAcheteur = vente.getAcheteur().getVille();
                    int distance = carte.getDistance(villeActuelle, villeAcheteur);

                    if (distance < distanceMin) {
                        distanceMin = distance;
                        prochaineDest = villeAcheteur;
                        venteChoisie = vente;
                        versAcheteur = true;
                    }
                }
            }

            // Si aucun acheteur disponible, chercher le vendeur le plus proche
            if (prochaineDest == null) {
                for (Vente vente : ventes) {
                    if (!venteursVisites.contains(vente)) {
                        Ville villeVendeur = vente.getVendeur().getVille();
                        int distance = carte.getDistance(villeActuelle, villeVendeur);

                        if (distance < distanceMin) {
                            distanceMin = distance;
                            prochaineDest = villeVendeur;
                            venteChoisie = vente;
                            versAcheteur = false;
                        }
                    }
                }
            }

            // Aller à la destination choisie
            if (prochaineDest != null) {
                // Éviter d'ajouter la même ville deux fois consécutivement
                if (!villeActuelle.equals(prochaineDest)) {
                    itineraire.add(prochaineDest);
                    villeActuelle = prochaineDest;
                }

                if (versAcheteur) {
                    // On vient de visiter un acheteur, la vente est complète
                    ventesCompletes.add(venteChoisie);
                } else {
                    // On vient de visiter un vendeur
                    venteursVisites.add(venteChoisie);

                    // Vérifier si d'autres vendeurs de la même ville sont aussi visités
                    for (Vente autreVente : ventes) {
                        if (autreVente.getVendeur().getVille().equals(villeActuelle) &&
                                !venteursVisites.contains(autreVente)) {
                            venteursVisites.add(autreVente);
                        }
                    }
                }
            } else {
                // Situation d'erreur : aucune destination trouvée
                throw new IllegalStateException("Impossible de trouver une prochaine destination valide");
            }
        }

        // On termine à Velizy (sauf si on y est déjà)
        if (!villeActuelle.equals(velizy)) {
            itineraire.add(velizy);
        }

        return itineraire;
    }

    /**
     * Calcule la distance totale d'un itinéraire.
     * 
     * @param itineraire Liste des villes à visiter dans l'ordre
     * @return La distance totale en kilomètres
     */
    @Override
    public int calculerDistanceTotale(List<Ville> itineraire) {
        int distanceTotale = 0;

        for (int i = 0; i < itineraire.size() - 1; i++) {
            Ville villeActuelle = itineraire.get(i);
            Ville villeSuivante = itineraire.get(i + 1);

            int distance = carte.getDistance(villeActuelle, villeSuivante);

            if (distance == Integer.MAX_VALUE) {
                throw new IllegalStateException("Aucun chemin direct entre " + villeActuelle + " et " + villeSuivante);
            }

            distanceTotale += distance;
        }

        return distanceTotale;
    }

    /**
     * Retourne le nom de l'algorithme.
     * 
     * @return Le nom de l'algorithme
     */
    @Override
    public String getNom() {
        return "Heuristique Glouton";
    }
}
