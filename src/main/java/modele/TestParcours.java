package modele;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe de test pour l'algorithme de parcours simple
 */
public class TestParcours {

    public static void main(String[] args) {
        try {
            // Chargement des distances entre les villes
            System.out.println("Chargement du fichier des distances...");
            String cheminFichierDistances = "pokemon_appli_data/distances.txt";
            Map<Pair<Ville, Ville>, Integer> distances = DistanceParser.lireFichierDistances(cheminFichierDistances);
            CarteGraph carte = new CarteGraph(distances);

            // Création d'un scénario de test
            System.out.println("Création d'un scénario de test...");
            Scenario scenario = creerScenarioTest(carte);

            // Génération de l'itinéraire avec l'algorithme de parcours simple
            System.out.println("\n=== ALGORITHME DE PARCOURS SIMPLE ===");
            ParcoursSimple parcours = new ParcoursSimple(carte);
            List<Ville> itineraire = parcours.genererItineraire(scenario);

            // Calcul et affichage de la distance totale
            int distanceTotale = parcours.calculerDistanceTotale(itineraire);

            // Affichage des résultats
            afficherResultats(scenario, itineraire, distanceTotale);

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier de distances : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crée un scénario de test basé sur les villes disponibles dans la carte
     */
    private static Scenario creerScenarioTest(CarteGraph carte) {
        // Récupérer quelques villes pour créer un scénario
        List<Ville> villes = new ArrayList<>(carte.getToutesLesVilles());

        if (villes.size() < 4) {
            throw new IllegalStateException("Pas assez de villes pour créer un scénario de test");
        }

        // Création de quelques membres
        Membre alice = new Membre("Alice", villes.get(0));
        Membre bob = new Membre("Bob", villes.get(1));
        Membre charlie = new Membre("Charlie", villes.get(2));
        Membre diana = new Membre("Diana", villes.get(3));

        // Création de quelques ventes
        Vente vente1 = new Vente(alice, bob);
        Vente vente2 = new Vente(bob, charlie);
        Vente vente3 = new Vente(diana, alice);

        // Création du scénario avec les ventes
        List<Vente> ventes = new ArrayList<>();
        ventes.add(vente1);
        ventes.add(vente2);
        ventes.add(vente3);

        return new Scenario("Scénario de test", ventes);
    }

    /**
     * Affiche les résultats du parcours
     */
    private static void afficherResultats(Scenario scenario, List<Ville> itineraire, int distanceTotale) {
        System.out.println("\n=== DÉTAILS DU SCÉNARIO ===");
        System.out.println(scenario);

        System.out.println("\n=== ITINÉRAIRE GÉNÉRÉ ===");
        System.out.println("Nombre de villes à visiter : " + itineraire.size());

        System.out.println("\nOrdre de visite des villes :");
        for (int i = 0; i < itineraire.size(); i++) {
            System.out.println((i + 1) + ". " + itineraire.get(i).getNom());
        }

        System.out.println("\nDistance totale à parcourir : " + distanceTotale + " km");
    }
}
