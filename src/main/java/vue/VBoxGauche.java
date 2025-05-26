package vue;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VBoxGauche extends VBox {
    private int chNumScenario;
    private Label scenario;
    public VBoxGauche(){
        super(20);
        chNumScenario = 0;
        scenario = new Label("Scenario " + chNumScenario);
        scenario.getStyleClass().add("title");

        GridPaneStatistique gridStatParcours = new GridPaneStatistique();
        gridStatParcours.getStyleClass().add("grid-orange");
        GridPaneModification gridModif = new GridPaneModification();
        gridModif.getStyleClass().add("grid-orange");
        this.getChildren().addAll(scenario, gridStatParcours, gridModif);
    }
}
