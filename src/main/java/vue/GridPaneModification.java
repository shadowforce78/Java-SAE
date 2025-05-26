package vue;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class GridPaneModification extends GridPane {
    public GridPaneModification(){
        this.setGridLinesVisible(true);

        Label labelVendeur = new Label("_Vendeur");
        labelVendeur.setMnemonicParsing(true);
        TextField textVendeur =  new TextField();
        textVendeur.setPromptText("Cliquez sur une ligne du tableau...");
        labelVendeur.setLabelFor(textVendeur);

        Label labelClient = new Label("_Client");
        labelClient.setMnemonicParsing(true);
        TextField textClient =  new TextField();
        textClient.setPromptText("Cliquez sur une ligne du tableau...");
        labelClient.setLabelFor(textVendeur);

        Label labelKilometre = new Label("_Nombre de kilomètres");
        labelKilometre.setMnemonicParsing(true);
        TextField textKilometre =  new TextField();
        textKilometre.setPromptText("Cliquez sur une ligne du tableau...");
        labelKilometre.setLabelFor(textKilometre);

        Button boutonModification =  new Button("Modifier la ligne");
        boutonModification.getStyleClass().add("button-green");


        this.add(new Label("Modification d'un Scénario"), 0, 0, 4, 1);
        this.add(labelVendeur, 0, 1);
        this.add(textVendeur, 1, 1, 4, 1);
        this.add(labelClient, 0, 2);
        this.add(textClient, 1, 2, 4, 1);
        this.add(labelKilometre, 0, 3);
        this.add(textKilometre, 1, 3, 4, 1);
        this.add(boutonModification, 1, 4, 2, 1);

    }
}
