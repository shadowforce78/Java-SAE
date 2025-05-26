package vue;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.ConstantesVues;

public class VBoxRoot extends VBox {
    public VBoxRoot() {
        super(10);
        MenuBar menuBar = new MenuBar();
        Menu menuScenario = new Menu("Scenario");
        ToggleGroup groupeScenario = new ToggleGroup();
        for (String item : new ConstantesVues().getItemsMenuScenarios()){
            RadioMenuItem menuItem = new RadioMenuItem(item);
            menuItem.setUserData(item);
            menuScenario.getItems().add(menuItem);
            menuItem.setToggleGroup(groupeScenario);
        }
        Menu menuQuitter = new Menu("Quitter");
        menuBar.getMenus().addAll(menuScenario, menuQuitter);

        HBox hBoxScenario = new HBoxAffichage();

        this.getChildren().addAll(menuBar, hBoxScenario);
    }
}
