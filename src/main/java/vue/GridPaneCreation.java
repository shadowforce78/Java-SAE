package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GridPaneCreation extends GridPane {
    private static TextField textNom;
    public GridPaneCreation(Controleur controleur){
        this.setGridLinesVisible(true);

        Label labelTitre = new Label("Création d'un scénario");
        Label labelNom = new Label("_Nom du scénario");
        textNom = new TextField();
        textNom.setPromptText("Entrez le nom du nouveau scénario...");
        labelNom.setLabelFor(textNom);
        labelNom.setMnemonicParsing(true);

        Button boutonCreation = new Button("_Créer un scénario");
        boutonCreation.setMnemonicParsing(true);
        boutonCreation.setUserData("Création");
        boutonCreation.getStyleClass().add("button-important");
        boutonCreation.addEventHandler(ActionEvent.ACTION, controleur);

        this.add(labelTitre, 1, 0, 3, 1);
        this.add(labelNom, 0, 1);
        this.add(textNom, 1, 1, 4, 1);
        this.add(boutonCreation,2, 2, 2, 1);
    }

    public TextField getTextNom(){
        return textNom;
    }
}
