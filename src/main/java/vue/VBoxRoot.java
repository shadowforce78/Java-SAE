package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import modele.ConstantesVues;

public class VBoxRoot extends VBox {
    private static Menu menuScenario;
    private static HBoxAffichage hBoxScenario;
    private static Controleur controleur;
    private static ToggleGroup groupeScenario;
    public VBoxRoot() {
        super(10);
        controleur = new Controleur();
        MenuBar menuBar = new MenuBar();
        menuScenario = new Menu("Scenario");
        groupeScenario = new ToggleGroup();
        for (String item : new ConstantesVues().getItemsMenuScenarios()){
            RadioMenuItem menuItem = new RadioMenuItem(item);
            menuItem.setUserData(item);
            menuScenario.getItems().add(menuItem);
            menuItem.setToggleGroup(groupeScenario);
            if(item.equals(new ConstantesVues().getItemsMenuScenarios().getFirst())){
                menuItem.setSelected(true);
            }
            menuItem.addEventHandler(ActionEvent.ACTION, controleur);
        }
        Menu menuQuitter = new Menu("Quitter");
        RadioMenuItem quitterItem = new RadioMenuItem("Quitter l'application ?");
        quitterItem.setUserData("Quitter");
        menuQuitter.getItems().add(quitterItem);
        quitterItem.addEventHandler(ActionEvent.ACTION, controleur);
        menuBar.getMenus().addAll(menuScenario, menuQuitter);

        hBoxScenario = new HBoxAffichage();

        this.getChildren().addAll(menuBar, hBoxScenario);
    }

    public static Menu getMenuScenario(){
        return menuScenario;
    }

    public static ToggleGroup getGroupeScenario(){
        return groupeScenario;
    }

    public static HBoxAffichage gethBoxScenario(){
        return hBoxScenario;
    }

    public static Controleur getControleur() {
        return controleur;
    }
}
