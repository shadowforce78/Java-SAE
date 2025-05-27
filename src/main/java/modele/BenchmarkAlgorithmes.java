package modele;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Classe de benchmark pour comparer les performances des différents algorithmes
 * en termes de temps d'exécution et de qualité des solutions (distance totale).
 */
public class BenchmarkAlgorithmes {
    
    private CarteGraph carte;
    private Map<String, IAlgorithme> algorithmes;
    
    /**
     * Résultat d'un benchmark pour un algorithme donné
     */
    public static class ResultatBenchmark {
        public String nomAlgorithme;
        public int distanceTotale;
        public long tempsExecutionMs;
        public List<Ville> itineraire;
        
        public ResultatBenchmark(String nomAlgorithme, int distanceTotale, long tempsExecutionMs, List<Ville> itineraire) {
            this.nomAlgorithme = nomAlgorithme;
            this.distanceTotale = distanceTotale;
            this.tempsExecutionMs = tempsExecutionMs;
            this.itineraire = new ArrayList<>(itineraire);
        }
        
        @Override
        public String toString() {
            return String.format("%s: %d km en %d ms (%d villes)", 
                               nomAlgorithme, distanceTotale, tempsExecutionMs, itineraire.size());
        }
    }
    
    public BenchmarkAlgorithmes(CarteGraph carte) {
        this.carte = carte;
        this.algorithmes = new HashMap<>();
        
        // Initialiser tous les algorithmes disponibles
        algorithmes.put("Parcours Simple", new ParcoursSimple(carte));
        algorithmes.put("Heuristique Glouton", new ParcoursHeuristique(carte));
        algorithmes.put("K Meilleures Solutions", new KMeilleuresSolutions(carte, 3));
    }
    
    /**
     * Lance un benchmark complet sur un scénario donné pour tous les algorithmes.
     * 
     * @param scenario Le scénario à tester
     * @return Liste des résultats triés par distance totale (meilleur en premier)
     */
    public List<ResultatBenchmark> benchmarkerScenario(Scenario scenario) {
        List<ResultatBenchmark> resultats = new ArrayList<>();
        
        System.out.println("=== BENCHMARK SCENARIO: " + scenario.getNom() + " ===");
        System.out.println("Nombre de ventes: " + scenario.getVentes().size());
        System.out.println();
        
        for (Map.Entry<String, IAlgorithme> entry : algorithmes.entrySet()) {
            String nomAlgo = entry.getKey();
            IAlgorithme algo = entry.getValue();
            
            System.out.print("Test de " + nomAlgo + "... ");
            
            // Mesurer le temps d'exécution
            long debut = System.nanoTime();
            List<Ville> itineraire = algo.genererItineraire(scenario);
            int distance = algo.calculerDistanceTotale(itineraire);
            long fin = System.nanoTime();
            
            long tempsMs = TimeUnit.NANOSECONDS.toMillis(fin - debut);
            
            ResultatBenchmark resultat = new ResultatBenchmark(nomAlgo, distance, tempsMs, itineraire);
            resultats.add(resultat);
            
            System.out.println(resultat);
        }
        
        // Trier par distance totale (meilleur en premier)
        resultats.sort(Comparator.comparingInt(r -> r.distanceTotale));
        
        System.out.println();
        System.out.println("=== CLASSEMENT PAR QUALITE DE SOLUTION ===");
        for (int i = 0; i < resultats.size(); i++) {
            ResultatBenchmark r = resultats.get(i);
            System.out.printf("%d. %s\n", i + 1, r);
        }
        
        System.out.println();
        System.out.println("=== CLASSEMENT PAR VITESSE D'EXECUTION ===");
        List<ResultatBenchmark> parVitesse = new ArrayList<>(resultats);
        parVitesse.sort(Comparator.comparingLong(r -> r.tempsExecutionMs));
        for (int i = 0; i < parVitesse.size(); i++) {
            ResultatBenchmark r = parVitesse.get(i);
            System.out.printf("%d. %s\n", i + 1, r);
        }
        
        return resultats;
    }
    
    /**
     * Lance un benchmark sur plusieurs scénarios et génère un rapport comparatif.
     * 
     * @param scenarios Liste des scénarios à tester
     * @return Map associant chaque nom de scénario à ses résultats de benchmark
     */
    public Map<String, List<ResultatBenchmark>> benchmarkerMultiplesScenarios(List<Scenario> scenarios) {
        Map<String, List<ResultatBenchmark>> tousResultats = new HashMap<>();
        Map<String, Integer> victoires = new HashMap<>(); // Nombre de victoires par algorithme
        Map<String, Long> tempsTotaux = new HashMap<>(); // Temps totaux par algorithme
        
        // Initialiser les compteurs
        for (String nomAlgo : algorithmes.keySet()) {
            victoires.put(nomAlgo, 0);
            tempsTotaux.put(nomAlgo, 0L);
        }
        
        System.out.println("=== BENCHMARK MULTI-SCENARIOS ===");
        System.out.println();
        
        // Tester chaque scénario
        for (Scenario scenario : scenarios) {
            List<ResultatBenchmark> resultats = benchmarkerScenario(scenario);
            tousResultats.put(scenario.getNom(), resultats);
            
            // Compter les victoires (meilleure distance)
            if (!resultats.isEmpty()) {
                String gagnant = resultats.get(0).nomAlgorithme;
                victoires.put(gagnant, victoires.get(gagnant) + 1);
            }
            
            // Accumuler les temps
            for (ResultatBenchmark r : resultats) {
                tempsTotaux.put(r.nomAlgorithme, tempsTotaux.get(r.nomAlgorithme) + r.tempsExecutionMs);
            }
            
            System.out.println("----------------------------------------");
        }
        
        // Rapport final
        System.out.println("=== RAPPORT FINAL ===");
        System.out.println();
        
        System.out.println("Nombre de victoires par algorithme (meilleure distance):");
        victoires.entrySet().stream()
                 .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                 .forEach(entry -> System.out.printf("  %s: %d victoires\n", entry.getKey(), entry.getValue()));
        
        System.out.println();
        System.out.println("Temps d'exécution total par algorithme:");
        tempsTotaux.entrySet().stream()
                   .sorted(Map.Entry.comparingByValue())
                   .forEach(entry -> System.out.printf("  %s: %d ms\n", entry.getKey(), entry.getValue()));
        
        return tousResultats;
    }
    
    /**
     * Recommande le meilleur algorithme selon le type de scénario.
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
    
    /**
     * Teste la recommandation d'algorithme et valide la pertinence.
     */
    public void testerRecommandations(List<Scenario> scenarios) {
        System.out.println("=== TEST DES RECOMMANDATIONS ===");
        System.out.println();
        
        for (Scenario scenario : scenarios) {
            String recommande = recommanderAlgorithme(scenario);
            List<ResultatBenchmark> resultats = benchmarkerScenario(scenario);
            
            System.out.printf("Scénario %s (%d ventes): Recommandé = %s\n", 
                            scenario.getNom(), scenario.getVentes().size(), recommande);
            
            // Vérifier si la recommandation était bonne
            if (!resultats.isEmpty()) {
                String meilleur = resultats.get(0).nomAlgorithme;
                boolean bonneReco = recommande.equals(meilleur);
                System.out.printf("  Meilleur réel: %s %s\n", 
                                meilleur, bonneReco ? "✓" : "✗");
                
                // Afficher l'écart de performance si la recommandation n'était pas optimale
                if (!bonneReco) {
                    ResultatBenchmark recoResult = resultats.stream()
                        .filter(r -> r.nomAlgorithme.equals(recommande))
                        .findFirst().orElse(null);
                    
                    if (recoResult != null) {
                        int ecart = recoResult.distanceTotale - resultats.get(0).distanceTotale;
                        double pourcentage = (double) ecart / resultats.get(0).distanceTotale * 100;
                        System.out.printf("  Écart: +%d km (+%.1f%%)\n", ecart, pourcentage);
                    }
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Méthode principale pour lancer un benchmark complet du projet.
     */
    public static void main(String[] args) {
        try {
            // Chargement des données
            File dossierData = new File("pokemon_appli_data");
            File fichierDistances = new File(dossierData, "distances.txt");
            File fichierMembres = new File(dossierData, "membres_APPLI.txt");
            
            System.out.println("Chargement des distances...");
            Map<Pair<Ville, Ville>, Integer> distances = DistanceParser.lireFichierDistances(fichierDistances.getPath());
            CarteGraph carte = new CarteGraph(distances);
            
            // Chargement de plusieurs scénarios
            List<Scenario> scenarios = new ArrayList<>();
            for (int i = 0; i <= 3; i++) {
                File fichierScenario = new File("scenario", "scenario_" + i + ".txt");
                if (fichierScenario.exists()) {
                    Scenario scenario = ScenarioParser.lireFichierScenario(fichierScenario.getPath(), fichierMembres.getPath());
                    scenarios.add(scenario);
                }
            }
            
            // Lancer le benchmark
            BenchmarkAlgorithmes benchmark = new BenchmarkAlgorithmes(carte);
            benchmark.benchmarkerMultiplesScenarios(scenarios);
            
            System.out.println();
            benchmark.testerRecommandations(scenarios);
            
        } catch (IOException e) {
            System.err.println("Erreur lors du benchmark : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
