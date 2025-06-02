package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class GridPaneModification extends GridPane {
    private static TextField textVendeur;
    private static TextField textClient;
    public GridPaneModification(Controleur controleur){
        this.setGridLinesVisible(true);

        Label labelVendeur = new Label("Vendeur");
        textVendeur =  new TextField();
        textVendeur.setPromptText("Cliquez sur une ligne du tableau...");
        labelVendeur.setLabelFor(textVendeur);

        Label labelClient = new Label("Client");
        textClient =  new TextField();
        textClient.setPromptText("Cliquez sur une ligne du tableau...");
        labelClient.setLabelFor(textVendeur);

        Button boutonSuppresion =  new Button("Supprimer la vente");
        boutonSuppresion.getStyleClass().add("button-green");
        boutonSuppresion.setUserData("Suppression");
        boutonSuppresion.addEventHandler(ActionEvent.ACTION, controleur);

        this.add(new Label("Modification d'un Scénario"), 0, 0, 4, 1);
        this.add(labelVendeur, 0, 1);
        this.add(textVendeur, 1, 1, 4, 1);
        this.add(labelClient, 0, 2);
        this.add(textClient, 1, 2, 4, 1);
        this.add(boutonSuppresion, 0, 3, 2, 1);

    }

    public String getVendeur(){
        return textVendeur.getText();
    }

    public String getClient(){
        return textClient.getText();
    }

    public void alertModification(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Vendeur ET/OU Client inexistant(s) ET/OU manquant(s) !");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.CLOSE){
            alert.close();
        }
    }
}
