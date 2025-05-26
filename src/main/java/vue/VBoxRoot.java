package vue;

import controleur.Controleur;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.ConstantesVues;

public class VBoxRoot extends VBox {
    private static MenuBar menuBar;
    private static HBoxAffichage hBoxScenario;
    private static Controleur controleur;
    public VBoxRoot() {
        super(10);
        controleur = new Controleur();
        menuBar = new MenuBar();
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

        hBoxScenario = new HBoxAffichage();

        this.getChildren().addAll(menuBar, hBoxScenario);
    }

    public static MenuBar getMenuBar(){
        return menuBar;
    }

    public static HBoxAffichage gethBoxScenario(){
        return hBoxScenario;
    }

    public static Controleur getControleur() {
        return controleur;
    }
}
