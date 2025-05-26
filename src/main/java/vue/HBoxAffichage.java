package vue;

import javafx.scene.layout.HBox;

public class HBoxAffichage extends HBox {
    public HBoxAffichage(){
        super(30);
        VBoxGauche panesStatsCreation = new VBoxGauche();
        VBoxDroite tableParcours = new VBoxDroite();

        this.getChildren().addAll(panesStatsCreation, tableParcours);
    }
}
