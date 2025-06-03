package vue;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modele.Scenario;
import modele.Vente;
import modele.Ville;

public class StackPaneParcours extends VBox {
    private TableVente tableParcours;
    private HBox alignementBoutons;
    private List<Node> liste;
    private StackPane stackPaneScenario;

    public StackPaneParcours() {

        stackPaneScenario = new StackPane();

        tableParcours = new TableVente();
        stackPaneScenario.getChildren().add(tableParcours);

        liste = stackPaneScenario.getChildren();
        final int dernierIndice = liste.size() - 1;
        Node premierListe = liste.getFirst();
        Node dernierListe = liste.get(dernierIndice);

        this.getChildren().add(stackPaneScenario);

        Button premiereSolution = new Button("<<");
        Button solutionPrecedente = new Button("<");
        Button solutionSuivante = new Button(">");
        Button derniereSolution = new Button(">>");

        alignementBoutons = new HBox(10);
        alignementBoutons.getChildren().addAll(premiereSolution, solutionPrecedente, solutionSuivante,
                derniereSolution);
        this.getChildren().add(alignementBoutons);
        alignementBoutons.setDisable(true);
        alignementBoutons.setAlignment(Pos.CENTER);

        solutionSuivante.setOnAction(e -> liste.getFirst().toFront());

        solutionPrecedente.setOnAction(e -> liste.getLast().toBack());
        derniereSolution.setOnAction(e -> {
            while (liste.getLast() != dernierListe) {
                liste.getLast().toBack();
            }

        });

        premiereSolution.setOnAction(e -> {
            while (liste.getLast() != premierListe) {
                liste.getFirst().toFront();
            }
        });
    }

    public List<Node> getListe() {
        return liste;
    }

    public void clearAll() {
        tableParcours.getItems().clear();
        System.out.println("cleared !");
    }

    /**
     * Trouve la vente correspondante entre deux villes dans le scénario
     * 
     * @param scenario     Le scénario contenant toutes les ventes
     * @param villeDepart  La ville de départ
     * @param villeArrivee La ville d'arrivée
     * @return La vente correspondante ou une vente avec seulement les noms des
     *         villes si non trouvée
     */
    private Vente trouverVente(Scenario scenario, Ville villeDepart, Ville villeArrivee) {
        for (Vente vente : scenario.getVentes()) {
            if (vente.getVendeur().getVille().equals(villeDepart) &&
                    vente.getAcheteur().getVille().equals(villeArrivee)) {
                return vente;
            }
        }
        // Si aucune vente exacte n'est trouvée, créer une vente avec juste les noms des
        // villes
        // (cas de fallback, ne devrait normalement pas arriver avec un algorithme
        // correct)
        return new Vente(villeDepart.getNom(), villeArrivee.getNom());
    }

    public HBox getAlignementBoutons() {
        return alignementBoutons;
    }

    public void ajoutTable(List<Ville> itineraire, Scenario scenario) {
        for (int i = 0; i < itineraire.size() - 1; i++) {
            Vente venteReelle = trouverVente(scenario, itineraire.get(i), itineraire.get(i + 1));
            tableParcours.getItems().add(venteReelle);
        }
    }

    public void ajouterSolution(List<Ville> itineraire, Scenario scenario) {
        TableVente nouvelleTable = new TableVente();
        for (int i = 0; i < itineraire.size() - 1; i++) {
            Vente venteReelle = trouverVente(scenario, itineraire.get(i), itineraire.get(i + 1));
            nouvelleTable.getItems().add(venteReelle);
        }
        stackPaneScenario.getChildren().add(nouvelleTable);
        liste = stackPaneScenario.getChildren();
        nouvelleTable.toFront();
        tableParcours = nouvelleTable;
        alignementBoutons.setDisable(false);
    }

}
