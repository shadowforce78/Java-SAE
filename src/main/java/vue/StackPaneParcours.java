package vue;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modele.Vente;
import modele.Ville;

import java.util.List;

public class StackPaneParcours extends VBox {
    private TableVente tableParcours;
    private HBox alignementBoutons;
    private List<Node> liste;
    private StackPane stackPaneScenario;
    public StackPaneParcours(){

        stackPaneScenario = new StackPane();

        tableParcours = new TableVente();
        stackPaneScenario.getChildren().add(tableParcours);

        liste = stackPaneScenario.getChildren();
        final int dernierIndice = liste.size()-1;
        Node premierListe = liste.getFirst();
        Node dernierListe = liste.get(dernierIndice);

        this.getChildren().add(stackPaneScenario);

        Button premiereSolution = new Button("<<");
        Button solutionPrecedente = new Button("<");
        Button solutionSuivante = new Button(">");
        Button derniereSolution = new Button(">>");

        alignementBoutons = new HBox(10);
        alignementBoutons.getChildren().addAll(premiereSolution, solutionPrecedente, solutionSuivante, derniereSolution);
        this.getChildren().add(alignementBoutons);
        alignementBoutons.setDisable(true);

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

    public void clearAll(){
        tableParcours.getItems().clear();
        System.out.println("cleared !");
    }

    public HBox getAlignementBoutons(){
        return alignementBoutons;
    }

    public void ajoutTable(List<Ville> itineraire){
        for (int i = 0; i < itineraire.size() - 1; i++) {
            tableParcours.getItems().add(new Vente(itineraire.get(i).getNom(), itineraire.get(i + 1).getNom()));
        }
    }

    public void ajouterSolution(List<Ville> itineraire){
        TableVente nouvelleTable = new TableVente();
        for (int i = 0; i < itineraire.size() - 1; i++) {
            nouvelleTable.getItems().add(new Vente(itineraire.get(i).getNom(), itineraire.get(i + 1).getNom()));
        }
        stackPaneScenario.getChildren().add(nouvelleTable);
        liste = stackPaneScenario.getChildren();
        nouvelleTable.toFront();
        tableParcours = nouvelleTable;
        alignementBoutons.setDisable(false);
    }

}
