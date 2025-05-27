package modele;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Classe utilitaire pour la sauvegarde de scénarios.
 * Permet de créer de nouveaux fichiers de scénarios et de les sauvegarder
 * dans le format approprié.
 */
public class SauvegardeScenario {
    
    private static final String DOSSIER_SCENARIOS = "scenario";
    private static final String EXTENSION_SCENARIO = ".txt";
    
    /**
     * Sauvegarde un scénario dans un nouveau fichier.
     * 
     * @param scenario Le scénario à sauvegarder
     * @param nomFichier Le nom du fichier (sans extension)
     * @return true si la sauvegarde a réussi, false sinon
     */
    public static boolean sauvegarderScenario(Scenario scenario, String nomFichier) {
        try {
            // Créer le dossier scenarios s'il n'existe pas
            File dossierScenarios = new File(DOSSIER_SCENARIOS);
            if (!dossierScenarios.exists()) {
                dossierScenarios.mkdirs();
            }
            
            // Construire le chemin du fichier
            String nomComplet = nomFichier.endsWith(EXTENSION_SCENARIO) ? 
                               nomFichier : nomFichier + EXTENSION_SCENARIO;
            File fichierScenario = new File(dossierScenarios, nomComplet);
            
            // Écrire le contenu du scénario
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(fichierScenario, StandardCharsets.UTF_8))) {
                
                // Écrire un commentaire avec la date de création
                writer.write("# Scénario créé le " + 
                           LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                writer.newLine();
                writer.write("# Nom: " + scenario.getNom());
                writer.newLine();
                writer.write("# Nombre de ventes: " + scenario.getVentes().size());
                writer.newLine();
                writer.newLine();
                
                // Écrire chaque vente
                for (Vente vente : scenario.getVentes()) {
                    String ligne = vente.getVendeur().getPseudo() + " -> " + 
                                  vente.getAcheteur().getPseudo();
                    writer.write(ligne);
                    writer.newLine();
                }
            }
            
            System.out.println("Scénario sauvegardé avec succès : " + fichierScenario.getAbsolutePath());
            return true;
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du scénario : " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Génère automatiquement un nom de fichier unique pour un nouveau scénario.
     * 
     * @param prefixe Le préfixe du nom de fichier (par défaut "scenario_nouveau")
     * @return Un nom de fichier unique
     */
    public static String genererNomFichierUnique(String prefixe) {
        if (prefixe == null || prefixe.trim().isEmpty()) {
            prefixe = "scenario_nouveau";
        }
        
        File dossierScenarios = new File(DOSSIER_SCENARIOS);
        if (!dossierScenarios.exists()) {
            return prefixe + "_1";
        }
        
        // Trouver le prochain numéro disponible
        int numero = 1;
        String nomCandidat;
        File fichierCandidat;
        
        do {
            nomCandidat = prefixe + "_" + numero;
            fichierCandidat = new File(dossierScenarios, nomCandidat + EXTENSION_SCENARIO);
            numero++;
        } while (fichierCandidat.exists());
        
        return nomCandidat;
    }
    
    /**
     * Sauvegarde un scénario avec un nom généré automatiquement.
     * 
     * @param scenario Le scénario à sauvegarder
     * @return Le nom du fichier créé, ou null en cas d'erreur
     */
    public static String sauvegarderScenarioAuto(Scenario scenario) {
        String nomFichier = genererNomFichierUnique("scenario_" + 
                                                   scenario.getNom().replaceAll("[^a-zA-Z0-9]", "_"));
        
        if (sauvegarderScenario(scenario, nomFichier)) {
            return nomFichier;
        }
        return null;
    }
    
    /**
     * Crée un nouveau scénario à partir de listes de pseudos.
     * 
     * @param nomScenario Le nom du nouveau scénario
     * @param ventes Liste des ventes sous forme de chaînes "vendeur -> acheteur"
     * @param cheminFichierMembres Le chemin vers le fichier des membres
     * @return Le scénario créé, ou null en cas d'erreur
     */
    public static Scenario creerNouveauScenario(String nomScenario, List<String> ventes, 
                                               String cheminFichierMembres) {
        try {
            // Créer un fichier temporaire avec les ventes
            Path fichierTemp = Files.createTempFile("scenario_temp", EXTENSION_SCENARIO);
            
            try (BufferedWriter writer = Files.newBufferedWriter(fichierTemp, StandardCharsets.UTF_8)) {
                for (String vente : ventes) {
                    writer.write(vente);
                    writer.newLine();
                }
            }
            
            // Utiliser ScenarioParser pour créer le scénario
            Scenario scenario = ScenarioParser.lireFichierScenario(
                fichierTemp.toString(), cheminFichierMembres);
            
            // Modifier le nom du scénario
            scenario = new Scenario(nomScenario, scenario.getVentes());
            
            // Supprimer le fichier temporaire
            Files.deleteIfExists(fichierTemp);
            
            return scenario;
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du nouveau scénario : " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Valide le format d'une ligne de vente.
     * 
     * @param ligneVente La ligne à valider (format "vendeur -> acheteur")
     * @return true si le format est valide
     */
    public static boolean validerFormatVente(String ligneVente) {
        if (ligneVente == null || ligneVente.trim().isEmpty()) {
            return false;
        }
        
        String[] parts = ligneVente.split("->|→");
        return parts.length == 2 && 
               !parts[0].trim().isEmpty() && 
               !parts[1].trim().isEmpty();
    }
    
    /**
     * Liste tous les fichiers de scénarios existants.
     * 
     * @return Liste des noms de fichiers de scénarios (sans extension)
     */
    public static List<String> listerScenariosExistants() {
        File dossierScenarios = new File(DOSSIER_SCENARIOS);
        if (!dossierScenarios.exists() || !dossierScenarios.isDirectory()) {
            return List.of();
        }
        
        return List.of(dossierScenarios.listFiles())
                   .stream()
                   .filter(File::isFile)
                   .filter(f -> f.getName().endsWith(EXTENSION_SCENARIO))
                   .map(f -> f.getName().substring(0, f.getName().length() - EXTENSION_SCENARIO.length()))
                   .sorted()
                   .toList();
    }
    
    /**
     * Méthode de test pour démontrer la fonctionnalité de sauvegarde.
     */
    public static void main(String[] args) {
        try {
            // Charger un scénario existant
            File fichierMembres = new File("pokemon_appli_data", "membres_APPLI.txt");
            File fichierScenario = new File("scenario", "scenario_0.txt");
            
            if (fichierScenario.exists() && fichierMembres.exists()) {
                Scenario scenario = ScenarioParser.lireFichierScenario(
                    fichierScenario.getPath(), fichierMembres.getPath());
                
                // Créer une copie avec un nouveau nom
                Scenario nouveauScenario = new Scenario("Test_Sauvegarde", scenario.getVentes());
                
                // Sauvegarder avec nom automatique
                String nomFichier = sauvegarderScenarioAuto(nouveauScenario);
                System.out.println("Fichier créé : " + nomFichier);
                
                // Lister tous les scénarios
                System.out.println("Scénarios existants :");
                listerScenariosExistants().forEach(s -> System.out.println("  - " + s));
                
            } else {
                System.out.println("Fichiers de test non trouvés");
            }
            
        } catch (IOException e) {
            System.err.println("Erreur dans le test de sauvegarde : " + e.getMessage());
        }
    }
}
