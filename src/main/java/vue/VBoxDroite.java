package vue;

import javafx.scene.layout.VBox;


public class VBoxDroite extends VBox {
    private static StackPaneParcours table;
    private static GridPaneCreation createScenario;
    public VBoxDroite(){
        super(20);
        table = new StackPaneParcours();
        createScenario = new GridPaneCreation(VBoxRoot.getControleur());
        createScenario.getStyleClass().add("grid-orange");
        this.setMaxWidth(350);
        this.getChildren().addAll(table, createScenario);
    }

    public static StackPaneParcours getTable() {
        return table;
    }

    public static GridPaneCreation getCreateScenario() {
        return createScenario;
    }
}
