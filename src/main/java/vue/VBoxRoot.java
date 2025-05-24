package vue;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBoxRoot extends VBox {
    public VBoxRoot() {
        super(10);
        MenuBar menuBar = new MenuBar();
        Menu menuScenario = new Menu("Scenario");
        Menu menuQuitter = new Menu("Quitter");
        menuBar.getMenus().addAll(menuScenario, menuQuitter);

        HBox hBoxScenario = new HBoxAffichage();

        this.getChildren().addAll(menuBar, hBoxScenario);
    }
}
