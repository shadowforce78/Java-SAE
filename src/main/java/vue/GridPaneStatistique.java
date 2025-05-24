package vue;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GridPaneStatistique extends GridPane {
    private int kilometres = 0; // Il faudra mettre la valeur de l'algo 1 du scénario 1
    private ComboBox<String> choixAlgo = new ComboBox<>();
    private ComboBox<Integer> kSolutions = new ComboBox<>();
    public GridPaneStatistique(){
        this.setGridLinesVisible(true);

        Label labelKilometre = new Label("Kilomètres parcourus : ");
        labelKilometre.getStyleClass().add("label-info");

        Label labelVilles = new Label("Villes parcourus : ");
        labelVilles.getStyleClass().add("label-info");

        Label labelAlgo = new Label("Algorithme : ");
        labelAlgo.getStyleClass().add("label-info");
        choixAlgo.getItems().addAll("Algorithme 1", "Algorithme 2", "Algorithme 3");
        for (int i = 1; i < 11; i++){
            kSolutions.getItems().add(i);
        }

        Label labelKSolutions = new Label("k meilleurs solutions : ");
        labelKSolutions.getStyleClass().add("label-info");
        kSolutions.setDisable(true);

        Button enregistrementAlgo = new Button("Selectionner l'algorithme");
        enregistrementAlgo.getStyleClass().add("button-green");

        this.add(new Label("Statistiques"), 1, 0, 2, 1);

        this.add(labelKilometre, 0, 1);
        this.add(new Label(String.valueOf(kilometres)), 1, 1);

        this.add(labelVilles, 0, 2);
        // Ajouter un objet pour afficher les villes ?

        this.add(labelAlgo, 0, 3);
        this.add(choixAlgo, 1, 3);

        this.add(labelKSolutions, 0, 4);
        this.add(kSolutions, 1, 4);

        this.add(enregistrementAlgo, 0, 5, 2, 1);
    }
}
