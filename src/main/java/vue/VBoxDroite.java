package vue;

import javafx.scene.layout.VBox;


public class VBoxDroite extends VBox {
    public VBoxDroite(){
        super(20);
        TableParcours table = new TableParcours();
        GridPaneCreation createScenario = new GridPaneCreation();
        createScenario.getStyleClass().add("grid-orange");

        this.getChildren().addAll(table, createScenario);
    }
}
