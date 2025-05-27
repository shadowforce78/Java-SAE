package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.HBox;
import vue.*;

public class Controleur implements EventHandler {

    @Override
    public void handle(Event event) {
        VBoxGauche vBoxGauche = HBoxAffichage.getvBoxGauche();
        GridPaneModification modification = VBoxGauche.getGridModif();
        GridPaneStatistique statistique = VBoxGauche.getGridStatParcours();
        if(event.getSource() instanceof Button){
            if (((Button) event.getSource()).getUserData().equals("Modification")){
                System.out.println("Modification");
            }
            else if (((Button) event.getSource()).getUserData().equals("Création")){
                System.out.println("Création");
            }

            else if(((Button) event.getSource()).getUserData().equals("Stats")){
                System.out.println("Stats");
            }
        }

        if(event.getSource() instanceof RadioMenuItem nouveauScenario){
            String stringScenario = nouveauScenario.getText();
            vBoxGauche.updateScenario(stringScenario);
        }
    }
}
