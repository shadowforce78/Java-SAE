package vue;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class GridPaneCreationModification extends GridPane {
    public GridPaneCreationModification(){
        this.setGridLinesVisible(true);

        Label labelType = new Label("Type");
        ToggleGroup toggleChoix = new ToggleGroup();
        RadioButton radioModification = new RadioButton("_Modification");
        radioModification.setMnemonicParsing(true);
        radioModification.setSelected(true);
        radioModification.setToggleGroup(toggleChoix);
        RadioButton radioCreation = new RadioButton("_Creation");
        radioCreation.setMnemonicParsing(true);
        radioCreation.setToggleGroup(toggleChoix);

        Label labelNom = new Label("_Nom");
        labelNom.setMnemonicParsing(true);
        TextField textNom =  new TextField();
        textNom.setPromptText("Entrez le nom d'un scénario...");
        labelNom.setLabelFor(textNom);

        Button boutonModification =  new Button("Modifier les scripts");
        boutonModification.getStyleClass().add("button-green");


        this.add(new Label("Création ou modification d'un Scénario"), 0, 0, 4, 1);
        this.add(labelType, 0, 1);
        this.add(radioModification, 1, 1);
        this.add(radioCreation, 2, 1);
        this.add(labelNom, 0, 2);
        this.add(textNom, 1, 2, 3, 1);
        this.add(boutonModification, 1, 3, 3, 1);

    }
}
