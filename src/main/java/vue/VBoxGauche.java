package vue;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VBoxGauche extends VBox {
    private int chNumScenario;
    private Label scenario;
    private static GridPaneStatistique gridStatParcours;
    private static GridPaneModification gridModif;
    public VBoxGauche(){
        super(20);
        chNumScenario = 0;
        scenario = new Label("Scenario " + chNumScenario);
        scenario.getStyleClass().add("title");

        gridStatParcours = new GridPaneStatistique();
        gridStatParcours.getStyleClass().add("grid-orange");
        gridModif = new GridPaneModification(VBoxRoot.getControleur());
        gridModif.getStyleClass().add("grid-orange");
        this.getChildren().addAll(scenario, gridStatParcours, gridModif);
    }

    public static GridPaneStatistique getGridStatParcours() {
        return gridStatParcours;
    }

    public static GridPaneModification getGridModif() {
        return gridModif;
    }
}
