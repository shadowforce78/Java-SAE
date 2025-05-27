package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GridPaneStatistique extends GridPane {
    private int kilometres = 0; // Il faudra mettre la valeur de l'algo 1 du scénario 1
    private ComboBox<String> choixAlgo = new ComboBox<>();
    private ComboBox<Integer> kSolutions = new ComboBox<>();
    public GridPaneStatistique(Controleur controleur){
        this.setGridLinesVisible(true);

        Label labelKilometre = new Label("Kilomètres parcourus : ");
        labelKilometre.getStyleClass().add("label-info");

        Label labelVilles = new Label("Villes parcourus : ");
        labelVilles.getStyleClass().add("label-info");

        Label labelAlgo = new Label("_Algorithme : ");
        labelAlgo.getStyleClass().add("label-info");
        labelAlgo.setMnemonicParsing(true);
        choixAlgo.getItems().addAll("Cours", "Heuristique", "K Solutions");
        labelAlgo.setLabelFor(choixAlgo);

        Label labelKSolutions = new Label("k meilleurs solutions : ");
        labelKSolutions.getStyleClass().add("label-info");
        kSolutions.setDisable(true);
        for (int i = 1; i < 11; i++){
            kSolutions.getItems().add(i);
        }
        labelKSolutions.setLabelFor(kSolutions);

        Button enregistrementAlgo = new Button("Selectionner l'algorithme");
        enregistrementAlgo.getStyleClass().add("button-green");
        enregistrementAlgo.setUserData("Stats");
        enregistrementAlgo.addEventHandler(ActionEvent.ACTION, controleur);

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
