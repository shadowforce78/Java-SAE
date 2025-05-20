package modele;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DistanceParser {
    public static Map<Pair<Ville, Ville>, Integer> lireFichierDistances(String path) throws IOException {
        Map<Pair<Ville, Ville>, Integer> distances = new HashMap<>();
        List<Ville> villes = new ArrayList<>();
        List<String> lignes = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

        for (String ligne : lignes) {
            String[] tokens = ligne.trim().split("\\s+");
            villes.add(new Ville(tokens[0]));
        }

        int n = villes.size();
        for (int i = 0; i < n; i++) {
            String[] tokens = lignes.get(i).trim().split("\\s+");
            Ville ville1 = villes.get(i);
            for (int j = 1; j <= n; j++) {
                Ville ville2 = villes.get(j - 1);
                int distance = Integer.parseInt(tokens[j]);
                distances.put(new Pair<>(ville1, ville2), distance);
            }
        }

        return distances;
    }
}
