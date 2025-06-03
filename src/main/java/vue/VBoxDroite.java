package vue;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class VBoxDroite extends VBox {
    private static StackPaneParcours table;
    private static GridPaneCreation createScenario;
    public VBoxDroite(){
        super(20);
        table = new StackPaneParcours();
        table.setMaxWidth(400);
        createScenario = new GridPaneCreation(VBoxRoot.getControleur());
        createScenario.getStyleClass().add("grid-orange");
        createScenario.setMaxWidth(400);
        createScenario.setAlignment(Pos.CENTER);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(table, createScenario);
    }

    public static StackPaneParcours getTable() {
        return table;
    }

    public static GridPaneCreation getCreateScenario() {
        return createScenario;
    }
}
