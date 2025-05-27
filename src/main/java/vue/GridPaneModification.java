package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class GridPaneModification extends GridPane {
    public GridPaneModification(Controleur controleur){
        this.setGridLinesVisible(true);

        Label labelVendeur = new Label("Vendeur");
        TextField textVendeur =  new TextField();
        textVendeur.setEditable(false);
        textVendeur.setPromptText("Cliquez sur une ligne du tableau...");
        labelVendeur.setLabelFor(textVendeur);

        Label labelClient = new Label("Client");
        TextField textClient =  new TextField();
        textClient.setEditable(false);
        textClient.setPromptText("Cliquez sur une ligne du tableau...");
        labelClient.setLabelFor(textVendeur);

        Button boutonModification =  new Button("Modifier la ligne");
        boutonModification.getStyleClass().add("button-green");
        boutonModification.setUserData("Modification");
        boutonModification.addEventHandler(ActionEvent.ACTION, controleur);

        this.add(new Label("Modification d'un Scénario"), 0, 0, 4, 1);
        this.add(labelVendeur, 0, 1);
        this.add(textVendeur, 1, 1, 4, 1);
        this.add(labelClient, 0, 2);
        this.add(textClient, 1, 2, 4, 1);
        this.add(boutonModification, 1, 3, 2, 1);

    }
}
