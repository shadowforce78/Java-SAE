package vue;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import modele.Membre;

public class GridPaneModification extends GridPane {
    private static ComboBox<Membre> comboVendeur;
    private static ComboBox<Membre> comboClient;
    private static ComboBox<Membre> comboNewVendeur;
    private static ComboBox<Membre> comboNewClient;
    private static Button boutonModifier;

    public GridPaneModification(Controleur controleur) {
        this.setGridLinesVisible(true);

        ToggleGroup toggleChoix = new ToggleGroup();
        RadioButton choixSupAjout = new RadioButton("Supprimer / Ajout");
        choixSupAjout.setSelected(true);
        choixSupAjout.setToggleGroup(toggleChoix);
        choixSupAjout.setUserData("toggleSupAjout");
        choixSupAjout.addEventHandler(ActionEvent.ACTION, controleur);
        RadioButton choixModification = new RadioButton("Modification");
        choixModification.setToggleGroup(toggleChoix);
        choixModification.setUserData("toggleModif");
        choixModification.addEventHandler(ActionEvent.ACTION, controleur); // Récupération de la liste des membres
        List<Membre> membres = controleur.getMembres();

        // Tri des membres par ordre alphabétique de leur pseudo
        membres.sort(Comparator.comparing(Membre::getPseudo));

        // Définition du convertisseur pour afficher le pseudo et la ville
        StringConverter<Membre> membreConverter = new StringConverter<>() {
            @Override
            public String toString(Membre membre) {
                if (membre == null)
                    return "";
                return membre.getPseudo() + " (" + membre.getVille().getNom() + ")";
            }

            @Override
            public Membre fromString(String string) {
                return null; // Non utilisé car les combobox ne sont pas éditables
            }
        };

        Label labelVendeur = new Label("Vendeur");
        comboVendeur = new ComboBox<>();
        comboVendeur.setPromptText("Sélectionner un vendeur");
        comboVendeur.getItems().addAll(membres);
        comboVendeur.setConverter(membreConverter);
        comboVendeur.setEditable(false);
        labelVendeur.setLabelFor(comboVendeur);

        Label labelClient = new Label("Client");
        comboClient = new ComboBox<>();
        comboClient.setPromptText("Sélectionner un client");
        comboClient.getItems().addAll(membres);
        comboClient.setConverter(membreConverter);
        comboClient.setEditable(false);
        labelClient.setLabelFor(comboClient);

        Label labelNewVendeur = new Label("New");
        comboNewVendeur = new ComboBox<>();
        comboNewVendeur.setPromptText("Sélectionner un nouveau vendeur");
        comboNewVendeur.getItems().addAll(membres);
        comboNewVendeur.setConverter(membreConverter);
        comboNewVendeur.setDisable(true);
        comboNewVendeur.setEditable(false);
        labelNewVendeur.setLabelFor(comboNewVendeur);

        Label labelNewClient = new Label("New");
        comboNewClient = new ComboBox<>();
        comboNewClient.setPromptText("Sélectionner un nouveau client");
        comboNewClient.getItems().addAll(membres);
        comboNewClient.setConverter(membreConverter);
        comboNewClient.setDisable(true);
        comboNewClient.setEditable(false);
        labelNewClient.setLabelFor(comboNewClient);

        Button boutonSuppresion = new Button("Supprimer la vente");
        boutonSuppresion.getStyleClass().add("button-important");
        boutonSuppresion.setUserData("Suppression");
        boutonSuppresion.addEventHandler(ActionEvent.ACTION, controleur);

        boutonModifier = new Button("Modifier la vente");
        boutonModifier.getStyleClass().add("button-important");
        boutonModifier.setUserData("Modification");
        boutonModifier.setDisable(true);
        boutonModifier.addEventHandler(ActionEvent.ACTION, controleur);

        Button boutonAjout = new Button("Ajouter une vente");
        boutonAjout.getStyleClass().add("button-important");
        boutonAjout.setUserData("Ajout");
        boutonAjout.addEventHandler(ActionEvent.ACTION, controleur);

        this.add(new Label("Mise à jour d'une transaction"), 2, 0, 5, 1);
        this.add(new Label("Type : "), 0, 1);
        this.add(choixSupAjout, 1, 1, 2, 1);
        this.add(choixModification, 5, 1);
        this.add(labelVendeur, 0, 2);
        this.add(comboVendeur, 1, 2, 2, 1);
        this.add(labelNewVendeur, 3, 2);
        this.add(comboNewVendeur, 4, 2, 2, 1);
        this.add(labelClient, 0, 3);
        this.add(comboClient, 1, 3, 2, 1);
        this.add(labelNewClient, 3, 3);
        this.add(comboNewClient, 4, 3, 2, 1);
        this.add(boutonSuppresion, 0, 4, 2, 1);
        this.add(boutonAjout, 2, 4);
        this.add(boutonModifier, 3, 4, 3, 1);
    }

    public String getVendeur() {
        Membre vendeur = comboVendeur.getValue();
        return vendeur != null ? vendeur.getPseudo() : null;
    }

    public String getClient() {
        Membre client = comboClient.getValue();
        return client != null ? client.getPseudo() : null;
    }

    public String getNewVendeur() {
        Membre newVendeur = comboNewVendeur.getValue();
        return newVendeur != null ? newVendeur.getPseudo() : null;
    }

    public String getNewClient() {
        Membre newClient = comboNewClient.getValue();
        return newClient != null ? newClient.getPseudo() : null;
    }

    public boolean isVendeurAndClientVides() {
        return comboClient.getValue() == null && comboVendeur.getValue() == null;
    }

    public void toggleNewFields(boolean state) {
        comboNewVendeur.setDisable(!state);
        comboNewClient.setDisable(!state);
    }

    public void enableNewTextField() {
        comboNewVendeur.setDisable(false);
        comboNewClient.setDisable(false);
        boutonModifier.setDisable(false);
        boutonModifier.setDisable(false);

    }

    public void disableNewTextField() {
        comboNewVendeur.setDisable(true);
        comboNewClient.setDisable(true);
        boutonModifier.setDisable(true);
        boutonModifier.setDisable(true);
    }

    public void alertModification(int code) {
        Alert alert;
        if (code == 0) {
            alert = new Alert(Alert.AlertType.ERROR, "Cette transaction n'existe pas. Veuillez réessayer",
                    ButtonType.OK);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de suppression / modification");
            Optional<ButtonType> option = alert.showAndWait();
        } else if (code == 1) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Transaction modifiée avec succès !", ButtonType.OK);
            alert.setTitle("Succès");
            alert.setHeaderText("Modification terminée");
            Optional<ButtonType> option = alert.showAndWait();
        }
        else if (code == 2) {
            alert = new Alert(Alert.AlertType.ERROR, "Valeurs nulles ou transaction déjà existante. Veuillez réessayer", ButtonType.OK);
            alert.setTitle("Erreur");
            alert.setHeaderText("Ajout impossible");
            Optional<ButtonType> option = alert.showAndWait();
        }
    }
}
