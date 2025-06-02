package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import modele.ConstantesVues;

import java.util.Optional;

public class GridPaneModification extends GridPane {
    private static TextField textVendeur;
    private static TextField textClient;
    private static TextField textNewVendeur;
    private static TextField textNewClient;
    public GridPaneModification(Controleur controleur){
        this.setGridLinesVisible(true);

        ToggleGroup toggleChoix = new ToggleGroup();
        RadioButton choixSupression = new RadioButton("Supprimer / Ajout");
        choixSupression.setToggleGroup(toggleChoix);
        choixSupression.setUserData("toggleSupAjout");
        choixSupression.addEventHandler(ActionEvent.ACTION, controleur);
        RadioButton choixModification = new RadioButton("Modification");
        choixModification.setToggleGroup(toggleChoix);
        choixModification.setUserData("toggleModif");
        choixModification.addEventHandler(ActionEvent.ACTION, controleur);

        Label labelVendeur = new Label("Vendeur");
        textVendeur = new TextField();
        textVendeur.setPromptText("Nom du vendeur");
        labelVendeur.setLabelFor(textVendeur);

        Label labelClient = new Label("Client");
        textClient = new TextField();
        textClient.setPromptText("Nom du client");
        labelClient.setLabelFor(textClient);

        Label labelNewVendeur = new Label("New");
        textNewVendeur = new TextField();
        textNewVendeur.setPromptText("Nom du nouveau vendeur");
        textNewVendeur.setDisable(true);
        labelNewVendeur.setLabelFor(textNewVendeur);

        Label labelNewClient = new Label("New");
        textNewClient = new TextField();
        textNewClient.setPromptText("Nom du nouveau client");
        textNewClient.setDisable(true);
        labelNewClient.setLabelFor(textNewClient);

        Button boutonSuppresion =  new Button("Supprimer la vente");
        boutonSuppresion.getStyleClass().add("button-green");
        boutonSuppresion.setUserData("Suppression");
        boutonSuppresion.addEventHandler(ActionEvent.ACTION, controleur);

        Button boutonModifier =  new Button("Modifier la vente");
        boutonModifier.getStyleClass().add("button-green");
        boutonModifier.setUserData("Modification");
        boutonModifier.addEventHandler(ActionEvent.ACTION, controleur);

        Button boutonAjout =  new Button("Ajouter une vente");
        boutonAjout.getStyleClass().add("button-green");
        boutonAjout.setUserData("Ajout");
        boutonAjout.addEventHandler(ActionEvent.ACTION, controleur);

        this.add(new Label("Mise à jour d'une transaction"), 2, 0, 5, 1);
        this.add(new Label("Type : "), 0, 1);
        this.add(choixSupression, 1, 1, 2, 1);
        this.add(choixModification, 5, 1);
        this.add(labelVendeur, 0, 2);
        this.add(textVendeur, 1, 2, 2, 1);
        this.add(labelNewVendeur, 3, 2);
        this.add(textNewVendeur, 4, 2, 2, 1);
        this.add(labelClient, 0, 3);
        this.add(textClient, 1, 3, 2, 1);
        this.add(labelNewClient, 3, 3);
        this.add(textNewClient, 4, 3, 2, 1);
        this.add(boutonSuppresion, 0, 4, 2, 1);
        this.add(boutonAjout, 2, 4);
        this.add(boutonModifier, 3, 4, 3, 1);
    }

    public String getVendeur(){
        return textVendeur.getText();
    }

    public String getClient(){
        return textClient.getText();
    }

    public String getNewVendeur(){
        return textNewVendeur.getText();
    }

    public String getNewClient(){
        return textNewClient.getText();
    }

    public boolean isVendeurAndClientVides(){
        return textClient.getText().isEmpty() && textVendeur.getText().isEmpty();
    }

    public void alertModification(int numMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(new ConstantesVues().getItemsModificationErreurs()[numMessage]);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.CLOSE){
            alert.close();
        }
    }

    public void enableNewTextField(){
        textNewVendeur.setDisable(false);
        textNewClient.setDisable(false);
    }

    public void disableNewTextField(){
        textNewVendeur.setDisable(true);
        textNewClient.setDisable(true);
    }
}
