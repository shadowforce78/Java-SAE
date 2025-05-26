package vue;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GridPaneCreation extends GridPane {
    TextField textNom;
    public GridPaneCreation(){
        this.setGridLinesVisible(true);
        Label labelTitre = new Label("Création d'un scénario");
        Label labelNom = new Label("Nom du scénario");
        textNom = new TextField();
        textNom.setPromptText("Entrez le nom du nouveau scénario...");
        labelNom.setLabelFor(textNom);
        Button boutonCreation = new Button("Créer un scénario");
        boutonCreation.getStyleClass().add("button-green");

        this.add(labelTitre, 1, 0, 3, 1);
        this.add(labelNom, 0, 1);
        this.add(textNom, 1, 1, 4, 1);
        this.add(boutonCreation,2, 2, 2, 1);

    }
}
