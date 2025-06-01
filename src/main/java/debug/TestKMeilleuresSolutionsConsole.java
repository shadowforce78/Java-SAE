package debug;

import modele.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Classe de test autonome pour déboguer l'algorithme KMeilleuresSolutions.
 * Utilitaire en console qui permet de tester en détail le comportement de l'algorithme
 * sur n'importe quel scénario et d'observer les étapes intermédiaires.
 */
public class TestKMeilleuresSolutionsConsole {

    // Niveau de verbosité du débogage (1-minimal, 2-normal, 3-détaillé, 4-très détaillé)
    private static int DEBUG_LEVEL = 3;

    public static void main(String[] args) {
        System.out.println("=== TESTEUR D'ALGORITHME K MEILLEURES SOLUTIONS ===");
        
        try (Scanner scanner = new Scanner(System.in)) {
            // Étape 1: Charger les données de distances et membres
            System.out.println("Chargement des données de base...");
            CarteGraph carte = chargerCarte();
            if (carte == null) {
                System.err.println("Erreur lors du chargement de la carte. Impossible de continuer.");
                return;
            }
            System.out.println("Carte chargée avec " + carte.getToutesLesVilles().size() + " villes.");
            
            // Étape 2: Paramétrer le test
            int k = demanderValeurK(scanner);
            String fichierScenario = demanderFichierScenario(scanner);
            DEBUG_LEVEL = demanderNiveauDebug(scanner);
            
            // Étape 3: Chargement du scénario
            System.out.println("\nChargement du scénario: " + fichierScenario);
            Scenario scenario = null;
            try {
                scenario = chargerScenario(fichierScenario);
                System.out.println("Scénario chargé avec " + scenario.getVentes().size() + " ventes:");
                afficherDetailsScenario(scenario);
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement du scénario: " + e.getMessage());
                e.printStackTrace();
                return;
            }
            
            // Étape 4: Exécution de l'algorithme avec instrumentation de débogage
            System.out.println("\n=== EXÉCUTION DE L'ALGORITHME K MEILLEURES SOLUTIONS (k=" + k + ") ===");
            try {
                // Création d'une instance instrumentée de KMeilleuresSolutions
                KMeilleuresSolutionsDebug algo = new KMeilleuresSolutionsDebug(carte, k);
                
                // Lancement de l'algorithme
                long debut = System.currentTimeMillis();
                Map<Integer, List<Ville>> resultats = algo.genererPlusieursItineraires(scenario);
                long fin = System.currentTimeMillis();
                
                // Affichage des résultats
                System.out.println("\n=== RÉSULTATS (" + (fin - debut) + " ms) ===");
                if (resultats.isEmpty()) {
                    System.out.println("AUCUNE SOLUTION TROUVÉE!");
                } else {
                    System.out.println(resultats.size() + " solution(s) trouvée(s):");
                    for (Map.Entry<Integer, List<Ville>> entry : resultats.entrySet()) {
                        int distance = algo.calculerDistanceTotale(entry.getValue());
                        System.out.println("Solution " + entry.getKey() + " (distance: " + distance + " km):");
                        afficherItineraire(entry.getValue());
                    }
                }
                
                // Statistiques de débogage
                System.out.println("\n=== STATISTIQUES DE DÉBOGAGE ===");
                System.out.println("Solutions explorées: " + algo.getNombreSolutionsExplorees());
                System.out.println("Solutions valides trouvées: " + algo.getNombreSolutionsValides());
                System.out.println("Profondeur maximale de récursion: " + algo.getProfondeurMaximale());
                System.out.println("Nombre d'élagages: " + algo.getNombreElagages());

                // Information sur l'utilisation mémoire
                Runtime runtime = Runtime.getRuntime();
                long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("Mémoire utilisée: " + (memoryUsed / 1024 / 1024) + " Mo");
                
            } catch (OutOfMemoryError e) {
                System.err.println("ERREUR MÉMOIRE INSUFFISANTE: " + e.getMessage());
                System.err.println("Le scénario est trop complexe pour la mémoire disponible.");
                e.printStackTrace();
            } catch (StackOverflowError e) {
                System.err.println("ERREUR DÉPASSEMENT DE PILE: " + e.getMessage());
                System.err.println("La récursion est trop profonde. Essayez de réduire la taille du scénario.");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("ERREUR LORS DE L'EXÉCUTION: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Classe interne qui étend KMeilleuresSolutions pour ajouter des fonctionnalités de débogage
     */
    private static class KMeilleuresSolutionsDebug extends KMeilleuresSolutions {
        private int nombreSolutionsExplorees = 0;
        private int nombreSolutionsValides = 0;
        private int profondeurMaximale = 0;
        private int profondeurActuelle = 0;
        private int nombreElagages = 0;
        private CarteGraph carte;
        
        public KMeilleuresSolutionsDebug(CarteGraph carte, int k) {
            super(carte, k);
            this.carte = carte;
        }
        
        @Override
        public List<Ville> genererItineraire(Scenario scenario) {
            debugPrint(1, "Génération d'itinéraire avec " + scenario.getVentes().size() + " ventes");
            return super.genererItineraire(scenario);
        }
        
        @Override
        public Map<Integer, List<Ville>> genererPlusieursItineraires(Scenario scenario) {
            try {
                // Réinitialiser les compteurs
                nombreSolutionsExplorees = 0;
                nombreSolutionsValides = 0;
                profondeurMaximale = 0;
                profondeurActuelle = 0;
                nombreElagages = 0;
                
                // Instrumenter l'algorithme original
                List<Vente> ventes = scenario.getVentes();
                
                debugPrint(1, "Lancement de l'algorithme avec " + ventes.size() + " ventes");
                if (DEBUG_LEVEL >= 3) {
                    for (Vente vente : ventes) {
                        debugPrint(3, "Vente: " + vente.getVendeur().getPseudo() + " (" +
                                vente.getVendeur().getVille().getNom() + ") -> " +
                                vente.getAcheteur().getPseudo() + " (" +
                                vente.getAcheteur().getVille().getNom() + ")");
                    }
                }
                
                // Vérifier que toutes les villes sont connectées
                debugPrint(2, "Vérification de la connectivité des villes du scénario...");
                Set<Ville> villesVisitees = new HashSet<>();
                for (Vente vente : ventes) {
                    villesVisitees.add(vente.getVendeur().getVille());
                    villesVisitees.add(vente.getAcheteur().getVille());
                }
                
                Ville velizy = null;
                for (Ville ville : carte.getToutesLesVilles()) {
                    if (ville.getNom().equalsIgnoreCase("Velizy")) {
                        velizy = ville;
                        villesVisitees.add(velizy);
                        break;
                    }
                }
                
                if (velizy == null) {
                    debugPrint(1, "ERREUR: Ville de Velizy non trouvée dans la carte!");
                    return new TreeMap<>();
                }
                
                boolean toutConnecte = true;
                for (Ville v1 : villesVisitees) {
                    for (Ville v2 : villesVisitees) {
                        if (!v1.equals(v2)) {
                            int distance = carte.getDistance(v1, v2);
                            if (distance == Integer.MAX_VALUE) {
                                debugPrint(2, "AVERTISSEMENT: Pas de chemin direct entre " + 
                                        v1.getNom() + " et " + v2.getNom());
                                toutConnecte = false;
                            }
                        }
                    }
                }
                
                if (!toutConnecte) {
                    debugPrint(2, "AVERTISSEMENT: Certaines villes ne sont pas directement connectées!");
                }
                
                // Exécuter l'algorithme
                debugPrint(1, "Exécution de l'algorithme principal...");
                Map<Integer, List<Ville>> resultats = super.genererPlusieursItineraires(scenario);
                debugPrint(1, "Algorithme terminé, " + resultats.size() + " solutions trouvées");
                
                return resultats;
            } catch (Exception e) {
                debugPrint(1, "EXCEPTION PENDANT L'EXÉCUTION: " + e.getMessage());
                e.printStackTrace();
                return new TreeMap<>();
            }
        }

        public int getNombreSolutionsExplorees() {
            return nombreSolutionsExplorees;
        }

        public int getNombreSolutionsValides() {
            return nombreSolutionsValides;
        }

        public int getProfondeurMaximale() {
            return profondeurMaximale;
        }
        
        public int getNombreElagages() {
            return nombreElagages;
        }
    }
    
    /**
     * Charge la carte à partir des fichiers de distances et de membres
     */
    private static CarteGraph chargerCarte() {
        try {
            String fichierDistances = "pokemon_appli_data" + File.separator + "distances.txt";

            // Charger les distances
            Map<Pair<Ville, Ville>, Integer> distances = DistanceParser.lireFichierDistances(fichierDistances);
            debugPrint(2, "Distances chargées: " + distances.size() + " routes");
            
            // Créer la carte directement avec les distances
            CarteGraph carte = new CarteGraph(distances);
            return carte;
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des fichiers: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Charge un scénario à partir d'un fichier
     */
    private static Scenario chargerScenario(String fichierScenario) throws IOException {
        String cheminFichier = "scenario" + File.separator + fichierScenario;
        String fichierMembres = "pokemon_appli_data" + File.separator + "membres_APPLI.txt";
        debugPrint(2, "Chargement du scénario depuis: " + cheminFichier);

        // Utiliser le ScenarioParser pour charger le scénario
        return ScenarioParser.lireFichierScenario(cheminFichier, fichierMembres);
    }
    
    /**
     * Demande à l'utilisateur de sélectionner un fichier de scénario
     */
    private static String demanderFichierScenario(Scanner scanner) {
        System.out.println("\nFichiers de scénario disponibles:");
        File dossierScenarios = new File("scenario");
        File[] fichiers = dossierScenarios.listFiles((dir, name) -> name.startsWith("scenario_") && name.endsWith(".txt"));
        
        if (fichiers != null) {
            Arrays.sort(fichiers);
            for (int i = 0; i < fichiers.length; i++) {
                System.out.println((i+1) + ". " + fichiers[i].getName());
            }
            
            System.out.print("\nChoisissez un fichier (1-" + fichiers.length + "): ");
            int choix = lireEntier(scanner, 1, fichiers.length);
            return fichiers[choix-1].getName();
        } else {
            System.out.print("Aucun fichier trouvé. Entrez le nom du fichier manuellement: ");
            return scanner.nextLine();
        }
    }
    
    /**
     * Demande à l'utilisateur la valeur de k (nombre de solutions à générer)
     */
    private static int demanderValeurK(Scanner scanner) {
        System.out.print("Entrez la valeur de k (nombre de solutions à générer): ");
        return lireEntier(scanner, 1, 10);
    }
    
    /**
     * Demande à l'utilisateur le niveau de détail du débogage
     */
    private static int demanderNiveauDebug(Scanner scanner) {
        System.out.println("\nNiveau de débogage:");
        System.out.println("1. Minimal (uniquement les informations essentielles)");
        System.out.println("2. Normal (informations de base + avertissements)");
        System.out.println("3. Détaillé (informations détaillées sur l'exécution)");
        System.out.println("4. Très détaillé (toutes les informations possibles)");
        System.out.print("\nChoisissez un niveau (1-4): ");
        return lireEntier(scanner, 1, 4);
    }
    
    /**
     * Lit un entier dans une plage donnée depuis l'entrée utilisateur
     */
    private static int lireEntier(Scanner scanner, int min, int max) {
        int valeur = min;
        boolean valide = false;
        
        while (!valide) {
            try {
                String input = scanner.nextLine();
                valeur = Integer.parseInt(input);
                if (valeur >= min && valeur <= max) {
                    valide = true;
                } else {
                    System.out.print("Veuillez entrer un nombre entre " + min + " et " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Veuillez entrer un nombre valide: ");
            }
        }
        
        return valeur;
    }
    
    /**
     * Affiche les détails d'un scénario
     */
    private static void afficherDetailsScenario(Scenario scenario) {
        List<Vente> ventes = scenario.getVentes();
        for (int i = 0; i < ventes.size(); i++) {
            Vente vente = ventes.get(i);
            System.out.println((i+1) + ". " + vente.getVendeur().getPseudo() + " (" +
                    vente.getVendeur().getVille().getNom() + ") -> " +
                    vente.getAcheteur().getPseudo() + " (" +
                    vente.getAcheteur().getVille().getNom() + ")");
        }
    }
    
    /**
     * Affiche un itinéraire
     */
    private static void afficherItineraire(List<Ville> itineraire) {
        if (itineraire.isEmpty()) {
            System.out.println("Itinéraire vide");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itineraire.size(); i++) {
            if (i > 0) {
                sb.append(" -> ");
            }
            sb.append(itineraire.get(i).getNom());
        }
        System.out.println(sb.toString());
    }
    
    /**
     * Affiche un message de débogage si le niveau de verbosité est suffisant
     */
    private static void debugPrint(int level, String message) {
        if (DEBUG_LEVEL >= level) {
            System.out.println("[DEBUG-" + level + "] " + message);
        }
    }
}
