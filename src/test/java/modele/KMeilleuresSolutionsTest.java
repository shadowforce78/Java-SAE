package modele;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Tests unitaires pour la classe KMeilleuresSolutions
 */
public class KMeilleuresSolutionsTest {

    private CarteGraph carte;
    private Scenario scenario;
    private KMeilleuresSolutions parcours;

    @BeforeEach
    public void setUp() throws IOException {
        // Chargement des données de test (distances, membres, scénario)
        File dossierData = new File("pokemon_appli_data");
        File dossierScenarios = new File("scenario");
        File fichierDistances = new File(dossierData, "distances.txt");
        File fichierScenario = new File(dossierScenarios, "scenario_0.txt");
        File fichierMembres = new File(dossierData, "membres_APPLI.txt");

        // Création des objets nécessaires aux tests
        Map<Pair<Ville, Ville>, Integer> distances = DistanceParser.lireFichierDistances(fichierDistances.getPath());
        carte = new CarteGraph(distances);
        scenario = ScenarioParser.lireFichierScenario(fichierScenario.getPath(), fichierMembres.getPath());
        parcours = new KMeilleuresSolutions(carte, 3); // On garde les 3 meilleures solutions
    }

    @Test
    public void testGenererItineraire() {
        List<Ville> itineraire = parcours.genererItineraire(scenario);

        // Vérifier que l'itinéraire n'est pas vide
        assertFalse(itineraire.isEmpty(), "L'itinéraire ne devrait pas être vide");

        // Vérifier que l'itinéraire commence et se termine à Velizy
        assertEquals("Velizy", itineraire.get(0).getNom(), "L'itinéraire doit commencer à Velizy");
        assertEquals("Velizy", itineraire.get(itineraire.size() - 1).getNom(), "L'itinéraire doit terminer à Velizy");

        // Vérifier que l'itinéraire contient toutes les ventes
        assertTrue(verifierToutesVentesCouvertes(itineraire, scenario),
                "L'itinéraire doit couvrir toutes les ventes du scénario");
    }

    @Test
    public void testCalculerDistanceTotale() {
        List<Ville> itineraire = parcours.genererItineraire(scenario);
        int distanceTotale = parcours.calculerDistanceTotale(itineraire);

        // Vérifier que la distance est positive
        assertTrue(distanceTotale > 0, "La distance totale doit être positive");

        // Recalculer manuellement la distance pour vérification
        int distanceManuelle = 0;
        for (int i = 0; i < itineraire.size() - 1; i++) {
            int distance = carte.getDistance(itineraire.get(i), itineraire.get(i + 1));
            distanceManuelle += distance;
        }

        // Vérifier que les deux calculs correspondent
        assertEquals(distanceManuelle, distanceTotale,
                "La distance calculée automatiquement doit correspondre au calcul manuel");
    }

    @Test
    public void testScenarioVide() {
        // Créer un scénario vide
        Scenario scenarioVide = new Scenario("ScenarioVide", new ArrayList<>());

        // Générer un itinéraire à partir du scénario vide
        List<Ville> itineraire = parcours.genererItineraire(scenarioVide);

        // Vérifier que l'itinéraire est vide pour un scénario vide
        assertTrue(itineraire.isEmpty(), "L'itinéraire doit être vide pour un scénario sans ventes");
    }

    @Test
    public void testContrainteVendeurAcheteur() {
        List<Ville> itineraire = parcours.genererItineraire(scenario);

        // Vérifier que pour chaque vente, le vendeur est visité avant l'acheteur
        for (Vente vente : scenario.getVentes()) {
            Ville villeVendeur = vente.getVendeur().getVille();
            Ville villeAcheteur = vente.getAcheteur().getVille();

            int indexVendeur = trouverPremiereOccurrence(itineraire, villeVendeur);
            int indexAcheteur = trouverDerniereOccurrence(itineraire, villeAcheteur);

            // Cas spécial: si le vendeur et l'acheteur sont dans la même ville
            if (villeVendeur.equals(villeAcheteur)) {
                continue;
            }

            assertTrue(indexVendeur != -1, "La ville du vendeur doit être dans l'itinéraire: " + villeVendeur.getNom());
            assertTrue(indexAcheteur != -1, "La ville de l'acheteur doit être dans l'itinéraire: " + villeAcheteur.getNom());
            assertTrue(indexVendeur < indexAcheteur,
                    "Le vendeur doit être visité avant l'acheteur pour chaque vente");
        }
    }

    @Test
    public void testNombreSolutions() {
        // Vérifier que le nombre de solutions conservées est correct
        assertEquals(3, parcours.getK(), "Le nombre de solutions conservées doit être 3");

        // Modifier le nombre de solutions et vérifier la mise à jour
        parcours.setK(5);
        assertEquals(5, parcours.getK(), "Le nombre de solutions conservées doit être 5 après mise à jour");

        // Vérifier que la valeur minimale est 1
        parcours.setK(0);
        assertEquals(1, parcours.getK(), "Le nombre de solutions conservées doit être au minimum 1");
    }

    @Test
    public void testComparaisonPerformance() {
        try {
            // Charger un petit scénario pour comparer les performances
            File dossierScenarios = new File("scenario");
            File fichierScenario = new File(dossierScenarios, "scenario_0.txt");
            File fichierMembres = new File("pokemon_appli_data", "membres_APPLI.txt");

            Scenario petitScenario = ScenarioParser.lireFichierScenario(fichierScenario.getPath(), fichierMembres.getPath());

            // Génération de l'itinéraire avec les trois algorithmes
            ParcoursSimple parcoursSimple = new ParcoursSimple(carte);
            ParcoursHeuristique parcoursHeuristique = new ParcoursHeuristique(carte);
            KMeilleuresSolutions parcoursK = new KMeilleuresSolutions(carte, 3);

            long debutSimple = System.currentTimeMillis();
            List<Ville> itineraireSimple = parcoursSimple.genererItineraire(petitScenario);
            long finSimple = System.currentTimeMillis();
            int distanceSimple = parcoursSimple.calculerDistanceTotale(itineraireSimple);

            long debutHeuristique = System.currentTimeMillis();
            List<Ville> itineraireHeuristique = parcoursHeuristique.genererItineraire(petitScenario);
            long finHeuristique = System.currentTimeMillis();
            int distanceHeuristique = parcoursHeuristique.calculerDistanceTotale(itineraireHeuristique);

            long debutK = System.currentTimeMillis();
            List<Ville> itineraireK = parcoursK.genererItineraire(petitScenario);
            long finK = System.currentTimeMillis();
            int distanceK = parcoursK.calculerDistanceTotale(itineraireK);

            // L'algorithme K meilleures solutions devrait trouver une solution au moins aussi bonne
            // que les autres algorithmes (ou meilleure)
            assertTrue(distanceK <= distanceSimple,
                    "L'algorithme K meilleures solutions devrait trouver un itinéraire au moins aussi bon que l'algorithme simple");

            System.out.println("=== COMPARAISON DES PERFORMANCES ===");
            System.out.println("Temps parcours simple: " + (finSimple - debutSimple) + " ms");
            System.out.println("Temps parcours heuristique: " + (finHeuristique - debutHeuristique) + " ms");
            System.out.println("Temps K meilleures solutions: " + (finK - debutK) + " ms");
            System.out.println("Distance parcours simple: " + distanceSimple + " km");
            System.out.println("Distance parcours heuristique: " + distanceHeuristique + " km");
            System.out.println("Distance K meilleures solutions: " + distanceK + " km");

        } catch (IOException e) {
            fail("Exception lors du chargement du scénario de test: " + e.getMessage());
        }
    }

    /**
     * Vérifie que toutes les ventes du scénario sont couvertes par l'itinéraire
     */
    private boolean verifierToutesVentesCouvertes(List<Ville> itineraire, Scenario scenario) {
        for (Vente vente : scenario.getVentes()) {
            Ville villeVendeur = vente.getVendeur().getVille();
            Ville villeAcheteur = vente.getAcheteur().getVille();

            if (!itineraire.contains(villeVendeur) || !itineraire.contains(villeAcheteur)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Trouve la première occurrence d'une ville dans l'itinéraire
     */
    private int trouverPremiereOccurrence(List<Ville> itineraire, Ville ville) {
        for (int i = 0; i < itineraire.size(); i++) {
            if (itineraire.get(i).equals(ville)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Trouve la dernière occurrence d'une ville dans l'itinéraire
     */
    private int trouverDerniereOccurrence(List<Ville> itineraire, Ville ville) {
        for (int i = itineraire.size() - 1; i >= 0; i--) {
            if (itineraire.get(i).equals(ville)) {
                return i;
            }
        }
        return -1;
    }
}
