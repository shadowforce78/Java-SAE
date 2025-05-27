package modele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Classe implémentant l'algorithme des k meilleures solutions (Algo 3)
 * qui utilise un backtracking pour explorer différentes possibilités
 * et retourner les k meilleurs itinéraires selon la distance totale.
 */
public class KMeilleuresSolutions implements IAlgorithme {

    private CarteGraph carte;
    private int k; // Nombre de solutions à conserver

    /**
     * Classe interne pour représenter une solution (itinéraire + distance)
     */
    private static class Solution implements Comparable<Solution> {
        List<Ville> itineraire;
        int distance;

        Solution(List<Ville> itineraire, int distance) {
            this.itineraire = new ArrayList<>(itineraire);
            this.distance = distance;
        }

        @Override
        public int compareTo(Solution autre) {
            return Integer.compare(this.distance, autre.distance);
        }
    }

    public KMeilleuresSolutions(CarteGraph carte, int k) {
        this.carte = carte;
        this.k = Math.max(1, k); // Au minimum 1 solution
    }

    public KMeilleuresSolutions(CarteGraph carte) {
        this(carte, 3); // Par défaut, on garde les 3 meilleures solutions
    }

    /**
     * Génère un itinéraire en retournant la meilleure solution parmi les k
     * calculées.
     * 
     * @param scenario Le scénario contenant les ventes à effectuer
     * @return Liste des villes à visiter dans l'ordre de la meilleure solution
     */
    @Override
    public List<Ville> genererItineraire(Scenario scenario) {
        List<Solution> solutions = genererKMeilleuresSolutions(scenario);
        return solutions.isEmpty() ? new ArrayList<>() : solutions.get(0).itineraire;
    }

    /**
     * Génère les k meilleures solutions pour un scénario donné.
     * 
     * @param scenario Le scénario contenant les ventes à effectuer
     * @return Liste des k meilleures solutions triées par distance croissante
     */
    public List<Solution> genererKMeilleuresSolutions(Scenario scenario) {
        List<Vente> ventes = scenario.getVentes();

        if (ventes.isEmpty()) {
            return new ArrayList<>();
        }

        // PriorityQueue pour maintenir les k meilleures solutions
        // Utilise un comparateur inverse pour garder les k plus petites distances
        PriorityQueue<Solution> meilleuresSolutions = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.distance, a.distance));

        // Variables pour le backtracking
        List<Ville> itineraireActuel = new ArrayList<>();
        Set<Vente> venteursVisites = new HashSet<>();
        Set<Vente> ventesCompletes = new HashSet<>();

        // On commence par la ville du premier vendeur
        Ville villeDepart = ventes.get(0).getVendeur().getVille();
        itineraireActuel.add(villeDepart);

        // Marquer toutes les ventes dont le vendeur est dans la ville de départ
        for (Vente vente : ventes) {
            if (vente.getVendeur().getVille().equals(villeDepart)) {
                venteursVisites.add(vente);
            }
        }

        // Lancer le backtracking
        backtrackingSolutions(ventes, itineraireActuel, venteursVisites, ventesCompletes,
                meilleuresSolutions, 0);

        // Convertir en liste triée (les meilleures en premier)
        List<Solution> resultat = new ArrayList<>(meilleuresSolutions);
        resultat.sort(Solution::compareTo);

        return resultat;
    }

    /**
     * Méthode récursive de backtracking pour explorer toutes les solutions
     * possibles.
     */
    private void backtrackingSolutions(List<Vente> ventes, List<Ville> itineraireActuel,
            Set<Vente> venteursVisites, Set<Vente> ventesCompletes,
            PriorityQueue<Solution> meilleuresSolutions, int distanceActuelle) {

        // Condition d'arrêt : toutes les ventes sont complètes
        if (ventesCompletes.size() == ventes.size()) {
            // Ajouter cette solution si elle fait partie des k meilleures
            if (meilleuresSolutions.size() < k) {
                meilleuresSolutions.offer(new Solution(itineraireActuel, distanceActuelle));
            } else if (distanceActuelle < meilleuresSolutions.peek().distance) {
                meilleuresSolutions.poll(); // Retirer la pire solution
                meilleuresSolutions.offer(new Solution(itineraireActuel, distanceActuelle));
            }
            return;
        }

        // Élagage : si la distance actuelle dépasse déjà la pire des k meilleures
        // solutions
        if (meilleuresSolutions.size() >= k && distanceActuelle >= meilleuresSolutions.peek().distance) {
            return;
        }

        Ville villeActuelle = itineraireActuel.get(itineraireActuel.size() - 1);

        // Générer toutes les destinations possibles
        Set<Ville> destinationsPossibles = new HashSet<>();

        // 1. Acheteurs dont le vendeur a été visité
        for (Vente vente : venteursVisites) {
            if (!ventesCompletes.contains(vente)) {
                destinationsPossibles.add(vente.getAcheteur().getVille());
            }
        }

        // 2. Vendeurs pas encore visités
        for (Vente vente : ventes) {
            if (!venteursVisites.contains(vente)) {
                destinationsPossibles.add(vente.getVendeur().getVille());
            }
        }

        // Explorer chaque destination possible
        for (Ville prochaineDest : destinationsPossibles) {
            int distance = carte.getDistance(villeActuelle, prochaineDest);

            if (distance == Integer.MAX_VALUE) {
                continue; // Pas de chemin direct, ignorer cette destination
            }

            // Éviter d'ajouter la même ville deux fois consécutivement
            if (villeActuelle.equals(prochaineDest)) {
                continue;
            }

            // Sauvegarder l'état actuel
            List<Ville> ancienItineraire = new ArrayList<>(itineraireActuel);
            Set<Vente> ancienVenteursVisites = new HashSet<>(venteursVisites);
            Set<Vente> ancienVentesCompletes = new HashSet<>(ventesCompletes);

            // Mettre à jour l'état
            itineraireActuel.add(prochaineDest);
            int nouvelleDistanceActuelle = distanceActuelle + distance;

            // Déterminer quelles ventes sont affectées par cette visite
            boolean aVisiteVendeur = false;
            List<Vente> ventesAffectees = new ArrayList<>();

            for (Vente vente : ventes) {
                if (vente.getVendeur().getVille().equals(prochaineDest) && !venteursVisites.contains(vente)) {
                    venteursVisites.add(vente);
                    aVisiteVendeur = true;
                    ventesAffectees.add(vente);
                } else if (vente.getAcheteur().getVille().equals(prochaineDest) &&
                        venteursVisites.contains(vente) && !ventesCompletes.contains(vente)) {
                    ventesCompletes.add(vente);
                    ventesAffectees.add(vente);
                }
            }

            // Appel récursif
            backtrackingSolutions(ventes, itineraireActuel, venteursVisites, ventesCompletes,
                    meilleuresSolutions, nouvelleDistanceActuelle);

            // Restaurer l'état (backtrack)
            itineraireActuel.clear();
            itineraireActuel.addAll(ancienItineraire);
            venteursVisites.clear();
            venteursVisites.addAll(ancienVenteursVisites);
            ventesCompletes.clear();
            ventesCompletes.addAll(ancienVentesCompletes);
        }
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
        return "K Meilleures Solutions";
    }

    /**
     * Définit le nombre de solutions à conserver.
     * 
     * @param k Le nombre de solutions à conserver (minimum 1)
     */
    public void setK(int k) {
        this.k = Math.max(1, k);
    }

    /**
     * Retourne le nombre de solutions conservées.
     * 
     * @return Le nombre de solutions conservées
     */
    public int getK() {
        return k;
    }

    /**
     * Retourne les détails de toutes les k meilleures solutions pour analyse.
     * 
     * @param scenario Le scénario à analyser
     * @return Une chaîne décrivant les k meilleures solutions
     */
    public String getDetailsSolutions(Scenario scenario) {
        List<Solution> solutions = genererKMeilleuresSolutions(scenario);
        StringBuilder sb = new StringBuilder();

        sb.append("=== ").append(k).append(" MEILLEURES SOLUTIONS ===\n");

        for (int i = 0; i < solutions.size(); i++) {
            Solution sol = solutions.get(i);
            sb.append("Solution ").append(i + 1).append(" (").append(sol.distance).append(" km) : ");
            for (int j = 0; j < sol.itineraire.size(); j++) {
                if (j > 0)
                    sb.append(" → ");
                sb.append(sol.itineraire.get(j).getNom());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
