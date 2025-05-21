package vue;

import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class HBoxAffichage extends HBox {
    public HBoxAffichage(){
        super(10);
        GridPaneStatistique gridStatParcours = new GridPaneStatistique();
        VBoxAffichageParcours tableParcours = new VBoxAffichageParcours();

        this.getChildren().addAll(gridStatParcours, tableParcours);
    }
}
