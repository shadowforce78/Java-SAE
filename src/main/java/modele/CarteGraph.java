package modele;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarteGraph {
    private Map<Pair<Ville, Ville>, Integer> distances;

    public CarteGraph(Map<Pair<Ville, Ville>, Integer> distances) {
        this.distances = distances;
    }

    public int getDistance(Ville a, Ville b) {
        return distances.getOrDefault(new Pair<>(a, b), Integer.MAX_VALUE); // ou -1
    }

    public Set<Ville> getToutesLesVilles() {
        return distances.keySet().stream()
                .flatMap(p -> Stream.of(p.getFirst(), p.getSecond()))
                .collect(Collectors.toSet());
    }
}
