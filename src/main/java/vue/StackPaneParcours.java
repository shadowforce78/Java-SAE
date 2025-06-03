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
    private javafx.scene.control.Label indiceSolution; // Ajout d'un Label pour afficher l'indice de la solution
    private int solutionActuelle = 0; // Indice de la solution actuellement affichée

    public StackPaneParcours() {

        stackPaneScenario = new StackPane();

        tableParcours = new TableVente();
        stackPaneScenario.getChildren().add(tableParcours);

        liste = stackPaneScenario.getChildren();

        this.getChildren().add(stackPaneScenario);

        Button premiereSolution = new Button("<<");
        Button solutionPrecedente = new Button("<");
        Button solutionSuivante = new Button(">");
        Button derniereSolution = new Button(">>");

        // Création du Label pour afficher l'indice de la solution actuelle
        indiceSolution = new javafx.scene.control.Label("Solution 1/1");
        indiceSolution.setStyle("-fx-font-weight: bold;");

        alignementBoutons = new HBox(10);
        alignementBoutons.getChildren().addAll(premiereSolution, solutionPrecedente,
                indiceSolution, solutionSuivante, derniereSolution);
        this.getChildren().add(alignementBoutons);
        alignementBoutons.setDisable(true);
        alignementBoutons.setAlignment(Pos.CENTER);

        // Méthode utilitaire pour mettre à jour l'affichage de l'indice
        Runnable mettreAJourIndice = () -> {
            if (!liste.isEmpty()) {
                int total = liste.size();
                indiceSolution.setText("Solution " + (solutionActuelle + 1) + "/" + total);
            } else {
                indiceSolution.setText("Solution 0/0");
            }
        };

        // Bouton ">" : avance d'une solution (incrémente l'indice)
        solutionSuivante.setOnAction(e -> {
            if (!liste.isEmpty() && solutionActuelle < liste.size() - 1) {
                solutionActuelle++;  // On incrémente l'indice pour aller à la solution suivante
                liste.get(solutionActuelle).toFront();
                mettreAJourIndice.run();
            }
        });

        // Bouton "<" : recule d'une solution (décrémente l'indice)
        solutionPrecedente.setOnAction(e -> {
            if (!liste.isEmpty() && solutionActuelle > 0) {
                solutionActuelle--;  // On décrémente l'indice pour aller à la solution précédente
                liste.get(solutionActuelle).toFront();
                mettreAJourIndice.run();
            }
        });

        // Bouton ">>" : va directement à la dernière solution
        derniereSolution.setOnAction(e -> {
            if (!liste.isEmpty()) {
                solutionActuelle = liste.size() - 1;  // Aller à la dernière solution
                liste.get(solutionActuelle).toFront();
                mettreAJourIndice.run();
            }
        });

        // Bouton "<<" : va directement à la première solution
        premiereSolution.setOnAction(e -> {
            if (!liste.isEmpty()) {
                solutionActuelle = 0;  // Aller à la première solution
                liste.get(0).toFront();
                mettreAJourIndice.run();
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
        solutionActuelle = 0; // Mise à jour de l'indice de la solution actuelle
        indiceSolution.setText("Solution " + (solutionActuelle + 1) + "/" + liste.size()); // Mise à jour du texte
        alignementBoutons.setDisable(false);
    }

}
