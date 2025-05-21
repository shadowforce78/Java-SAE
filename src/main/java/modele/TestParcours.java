package modele;

import java.io.File;
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
            // Utilisation de File pour des chemins compatibles multi-OS
            File dossierData = new File("pokemon_appli_data");
            File fichierDistances = new File(dossierData, "distances.txt");
            File fichierScenario = new File(dossierData, "scenario_0.txt");
            File fichierMembres = new File(dossierData, "membres_APPLI.txt");

            // Chargement des distances entre les villes
            System.out.println("Chargement du fichier des distances...");
            Map<Pair<Ville, Ville>, Integer> distances = DistanceParser.lireFichierDistances(fichierDistances.getPath());
            CarteGraph carte = new CarteGraph(distances);
            
            // Chargement du scénario 0
            System.out.println("Chargement du scénario 0...");
            Scenario scenario = ScenarioParser.lireFichierScenario(fichierScenario.getPath(), fichierMembres.getPath());

            // Génération de l'itinéraire avec l'algorithme de parcours simple
            System.out.println("\n=== ALGORITHME DE PARCOURS SIMPLE ===");
            ParcoursSimple parcours = new ParcoursSimple(carte);
            List<Ville> itineraire = parcours.genererItineraire(scenario);
            
            // Calcul et affichage de la distance totale
            int distanceTotale = parcours.calculerDistanceTotale(itineraire);
            
            // Affichage des résultats
            afficherResultats(scenario, itineraire, distanceTotale);
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture des fichiers : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
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
