package vue;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modele.ConstantesVues;

public class VBoxGauche extends VBox {
    private Label scenario;
    private static GridPaneStatistique gridStatParcours;
    private static GridPaneModification gridModif;
    public VBoxGauche(){
        super(20);
        scenario = new Label(new ConstantesVues().getItemsMenuScenarios().getFirst());
        scenario.getStyleClass().add("title");

        gridStatParcours = new GridPaneStatistique(VBoxRoot.getControleur());
        gridStatParcours.getStyleClass().add("grid-orange");
        gridModif = new GridPaneModification(VBoxRoot.getControleur());
        gridModif.getStyleClass().add("grid-orange");
        this.setMaxWidth(500);
        this.getChildren().addAll(scenario, gridStatParcours, gridModif);
    }

    public void updateScenario(String nouveauScenario){
        scenario.setText(nouveauScenario);
    }

    public String getScenario(){
        return scenario.getText();
    }

    public static GridPaneStatistique getGridStatParcours() {
        return gridStatParcours;
    }

    public static GridPaneModification getGridModif() {
        return gridModif;
    }
}
