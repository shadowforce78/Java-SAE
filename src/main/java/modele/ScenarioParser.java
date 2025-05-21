package modele;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioParser {

    /**
     * Lit un fichier de scénario et les membres
     * @param cheminFichierScenario Chemin vers le fichier de scénario
     * @param cheminFichierMembres Chemin vers le fichier des membres
     * @return Un objet Scenario contenant les ventes décrites dans le fichier
     * @throws IOException En cas d'erreur de lecture des fichiers
     */
    public static Scenario lireFichierScenario(String cheminFichierScenario, String cheminFichierMembres) throws IOException {
        // Charger les membres
        Map<String, Membre> membres = chargerMembres(cheminFichierMembres);

        // Utilisation de File pour assurer la compatibilité multi-OS
        File fichierScenario = new File(cheminFichierScenario);
        List<String> lignes = Files.readAllLines(fichierScenario.toPath(), StandardCharsets.UTF_8);
        List<Vente> ventes = new ArrayList<>();

        // Pour chaque ligne, créer une vente
        for (String ligne : lignes) {
            ligne = ligne.trim();
            if (ligne.isEmpty()) {
                continue;
            }

            String[] parts = ligne.split("->|→");
            if (parts.length != 2) {
                System.err.println("Format de ligne invalide: " + ligne);
                continue;
            }

            String nomVendeur = parts[0].trim();
            String nomAcheteur = parts[1].trim();

            Membre vendeur = membres.get(nomVendeur);
            Membre acheteur = membres.get(nomAcheteur);

            if (vendeur == null) {
                System.err.println("Membre vendeur non trouvé: " + nomVendeur);
                continue;
            }

            if (acheteur == null) {
                System.err.println("Membre acheteur non trouvé: " + nomAcheteur);
                continue;
            }

            ventes.add(new Vente(vendeur, acheteur));
        }

        // Extraire le nom du fichier pour l'utiliser comme nom du scénario
        String nomScenario = fichierScenario.getName();

        return new Scenario(nomScenario, ventes);
    }

    /**
     * Charge les membres depuis un fichier
     * @param cheminFichierMembres Chemin vers le fichier des membres
     * @return Une map associant le pseudo d'un membre à son objet Membre
     * @throws IOException En cas d'erreur de lecture du fichier
     */
    private static Map<String, Membre> chargerMembres(String cheminFichierMembres) throws IOException {
        // Utilisation de File pour assurer la compatibilité multi-OS
        File fichierMembres = new File(cheminFichierMembres);
        List<String> lignes = Files.readAllLines(fichierMembres.toPath(), StandardCharsets.UTF_8);
        Map<String, Membre> membres = new HashMap<>();

        for (String ligne : lignes) {
            ligne = ligne.trim();
            if (ligne.isEmpty()) {
                continue;
            }

            String[] parts = ligne.split("\\s+", 2);
            if (parts.length != 2) {
                System.err.println("Format de ligne membre invalide: " + ligne);
                continue;
            }

            String pseudo = parts[0];
            String nomVille = parts[1];

            Ville ville = new Ville(nomVille);
            Membre membre = new Membre(pseudo, ville);

            membres.put(pseudo, membre);
        }

        return membres;
    }
}
