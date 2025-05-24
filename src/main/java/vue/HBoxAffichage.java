package vue;

import javafx.scene.layout.HBox;

public class HBoxAffichage extends HBox {
    public HBoxAffichage(){
        super(10);
        VBoxStatCreation panesStatsCreation = new VBoxStatCreation();
        VBoxAffichageParcours tableParcours = new VBoxAffichageParcours();

        this.getChildren().addAll(panesStatsCreation, tableParcours);
    }
}
