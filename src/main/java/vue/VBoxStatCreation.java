package vue;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VBoxStatCreation extends VBox {
    private int chNumScenario;
    private Label scenario;
    public VBoxStatCreation(){
        super(20);
        chNumScenario = 0;
        scenario = new Label("Scenario " + chNumScenario);
        scenario.getStyleClass().add("title");

        GridPaneStatistique gridStatParcours = new GridPaneStatistique();
        gridStatParcours.getStyleClass().add("grid-orange");
        GridPaneCreationModification gridCreateModif = new GridPaneCreationModification();
        gridCreateModif.getStyleClass().add("grid-orange");
        this.getChildren().addAll(scenario, gridStatParcours, gridCreateModif);
    }
}
