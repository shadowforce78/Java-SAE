package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class TransactionFinder {
    private final String nomScenario;
    public TransactionFinder(String parScenario){
        nomScenario = parScenario;
    }

    public boolean containsTransaction(String buyer, String seller) {
        try {
            FileReader fileReader = new FileReader("scenario" + File.separator + nomScenario);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("->");

                if (parts.length == 2) {
                    String foundBuyer = parts[0].trim();
                    String foundSeller = parts[1].trim();

                    if (foundBuyer.equalsIgnoreCase(buyer) && foundSeller.equalsIgnoreCase(seller)) {
                        return true;
                    }
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        return false;
    }

    public void removeTransaction(String buyer, String seller) {
        File file = new File("scenario" + File.separator + nomScenario);
        StringBuilder newContent = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("->");

                if (parts.length == 2) {
                    String foundBuyer = parts[0].trim();
                    String foundSeller = parts[1].trim();

                    // Si c'est la ligne à supprimer, on passe (on ne l'ajoute pas)
                    if (foundBuyer.equalsIgnoreCase(buyer) && foundSeller.equalsIgnoreCase(seller)) {
                        continue;
                    }
                }
                newContent.append(line).append(System.lineSeparator());
            }

            // Réécriture du fichier sans la ligne ignorée
            Files.write(file.toPath(), newContent.toString().getBytes());

        } catch (IOException e) {
            System.out.println("Erreur lors de la suppression de la transaction : " + e.getMessage());
        }
    }
}

