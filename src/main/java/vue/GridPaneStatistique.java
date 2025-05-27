package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GridPaneStatistique extends GridPane {
    private Label labelNombreKilometre;
    private ComboBox<String> choixAlgo = new ComboBox<>();
    private ComboBox<Integer> kSolutions = new ComboBox<>();
    public GridPaneStatistique(Controleur controleur){
        this.setGridLinesVisible(true);

        Label labelKilometre = new Label("Kilomètres parcourus : ");
        labelNombreKilometre = new Label("");
        labelKilometre.getStyleClass().add("label-info");

        Label labelAlgo = new Label("_Algorithme : ");
        labelAlgo.getStyleClass().add("label-info");
        labelAlgo.setMnemonicParsing(true);
        choixAlgo.getItems().addAll("Cours", "Heuristique", "K Solutions");
        labelAlgo.setLabelFor(choixAlgo);
        choixAlgo.addEventHandler(ActionEvent.ACTION, controleur);
        choixAlgo.setValue(choixAlgo.getItems().getFirst());

        Label labelKSolutions = new Label("k meilleurs solutions : ");
        labelKSolutions.getStyleClass().add("label-info");
        kSolutions.setDisable(true);
        for (int i = 1; i < 11; i++){
            kSolutions.getItems().add(i);
        }
        labelKSolutions.setLabelFor(kSolutions);
        kSolutions.setValue(3);

        Button enregistrementAlgo = new Button("Selectionner l'algorithme");
        enregistrementAlgo.getStyleClass().add("button-green");
        enregistrementAlgo.setUserData("Stats");
        enregistrementAlgo.addEventHandler(ActionEvent.ACTION, controleur);

        this.add(new Label("Statistiques"), 1, 0, 2, 1);

        this.add(labelKilometre, 0, 1);
        this.add(labelNombreKilometre, 1, 1);

        this.add(labelAlgo, 0, 2);
        this.add(choixAlgo, 1, 2);

        this.add(labelKSolutions, 0, 3);
        this.add(kSolutions, 1, 3);

        this.add(enregistrementAlgo, 0, 4, 2, 1);
    }

    public void enableKSolutions(){
        kSolutions.setDisable(false);
    }

    public String getAlgorithme(){
        return choixAlgo.getValue();
    }

    public void updateKilometres(int parKilometres){
        labelNombreKilometre.setText(String.valueOf(parKilometres));
    }
}
