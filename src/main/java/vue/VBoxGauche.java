package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
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
        gridStatParcours.setMaxWidth(Region.USE_PREF_SIZE);
        gridModif = new GridPaneModification(VBoxRoot.getControleur());
        gridModif.getStyleClass().add("grid-orange");
        gridModif.setMaxWidth(600);
        gridModif.setAlignment(Pos.CENTER);

        this.setAlignment(Pos.CENTER);
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
