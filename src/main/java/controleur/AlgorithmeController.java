package controleur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.CarteGraph;
import modele.IAlgorithme;
import modele.KMeilleuresSolutions;
import modele.ParcoursHeuristique;
import modele.ParcoursSimple;
import modele.Scenario;
import modele.Ville;

/**
 * Contrôleur principal qui fait le lien entre l'IHM et les algorithmes.
 * Cette classe suit le pattern Singleton pour garantir une instance unique.
 */
public class AlgorithmeController {

    private static AlgorithmeController instance;
    private Map<String, IAlgorithme> algorithmes;
    private CarteGraph carte;

    /**
     * Constructeur privé pour le pattern Singleton.
     * Initialise la liste des algorithmes disponibles.
     */
    private AlgorithmeController(CarteGraph carte) {
        this.carte = carte;
        this.algorithmes = new HashMap<>();

        // Enregistrement des algorithmes disponibles
        algorithmes.put("Parcours Simple", new ParcoursSimple(carte));
        algorithmes.put("Heuristique Glouton", new ParcoursHeuristique(carte));
        algorithmes.put("K Meilleures Solutions", new KMeilleuresSolutions(carte));
        // Les autres algorithmes seront ajoutés ici par la suite
    }

    /**
     * Méthode d'accès à l'instance unique du contrôleur (pattern Singleton).
     * 
     * @param carte La carte des distances entre villes
     * @return L'instance unique du contrôleur
     */
    public static AlgorithmeController getInstance(CarteGraph carte) {
        if (instance == null) {
            instance = new AlgorithmeController(carte);
        }
        return instance;
    }

    /**
     * Génère un itinéraire pour un scénario donné en utilisant l'algorithme
     * spécifié.
     * 
     * @param nomAlgorithme Le nom de l'algorithme à utiliser
     * @param scenario      Le scénario à traiter
     * @return La liste des villes à visiter dans l'ordre
     * @throws IllegalArgumentException si l'algorithme n'existe pas
     */
    public List<Ville> genererItineraire(String nomAlgorithme, Scenario scenario) {
        IAlgorithme algorithme = getAlgorithme(nomAlgorithme);
        return algorithme.genererItineraire(scenario);
    }

    /**
     * Calcule la distance totale d'un itinéraire.
     * 
     * @param nomAlgorithme Le nom de l'algorithme à utiliser
     * @param itineraire    La liste des villes à visiter dans l'ordre
     * @return La distance totale en kilomètres
     * @throws IllegalArgumentException si l'algorithme n'existe pas
     */
    public int calculerDistanceTotale(String nomAlgorithme, List<Ville> itineraire) {
        IAlgorithme algorithme = getAlgorithme(nomAlgorithme);
        return algorithme.calculerDistanceTotale(itineraire);
    }

    /**
     * Génère un itinéraire et calcule sa distance totale en une seule opération.
     * 
     * @param nomAlgorithme Le nom de l'algorithme à utiliser
     * @param scenario      Le scénario à traiter
     * @return Un tableau contenant l'itinéraire [0] et la distance totale [1]
     * @throws IllegalArgumentException si l'algorithme n'existe pas
     */
    public Object[] genererItineraireAvecDistance(String nomAlgorithme, Scenario scenario) {
        IAlgorithme algorithme = getAlgorithme(nomAlgorithme);
        List<Ville> itineraire = algorithme.genererItineraire(scenario);
        int distance = algorithme.calculerDistanceTotale(itineraire);

        return new Object[] { itineraire, distance };
    }

    /**
     * Compare les performances des différents algorithmes sur un scénario donné.
     * 
     * @param scenario Le scénario à traiter
     * @return Une Map associant chaque algorithme à sa performance (distance
     *         totale)
     */
    public Map<String, Integer> comparerAlgorithmes(Scenario scenario) {
        Map<String, Integer> resultats = new HashMap<>();

        for (String nomAlgo : algorithmes.keySet()) {
            IAlgorithme algo = algorithmes.get(nomAlgo);
            List<Ville> itineraire = algo.genererItineraire(scenario);
            int distance = algo.calculerDistanceTotale(itineraire);
            resultats.put(nomAlgo, distance);
        }

        return resultats;
    }

    /**
     * Retourne la liste des noms d'algorithmes disponibles.
     * 
     * @return La liste des noms d'algorithmes
     */
    public List<String> getNomsAlgorithmes() {
        return new ArrayList<>(algorithmes.keySet());
    }

    /**
     * Retourne l'algorithme correspondant au nom spécifié.
     * 
     * @param nomAlgorithme Le nom de l'algorithme
     * @return L'algorithme correspondant
     * @throws IllegalArgumentException si l'algorithme n'existe pas
     */
    private IAlgorithme getAlgorithme(String nomAlgorithme) {
        IAlgorithme algorithme = algorithmes.get(nomAlgorithme);
        if (algorithme == null) {
            throw new IllegalArgumentException("Algorithme non trouvé : " + nomAlgorithme);
        }
        return algorithme;
    }

    /**
     * Recommande automatiquement le meilleur algorithme selon la taille du
     * scénario.
     * 
     * @param scenario Le scénario à analyser
     * @return Le nom de l'algorithme recommandé
     */
    public String recommanderAlgorithme(Scenario scenario) {
        int nbVentes = scenario.getVentes().size();

        // Logique de recommandation basée sur la taille du scénario
        if (nbVentes <= 5) {
            return "K Meilleures Solutions"; // Pour les petits scénarios, on peut se permettre l'exhaustivité
        } else if (nbVentes <= 15) {
            return "Heuristique Glouton"; // Bon compromis qualité/vitesse
        } else {
            return "Parcours Simple"; // Pour les gros scénarios, privilégier la vitesse
        }
    }
}
