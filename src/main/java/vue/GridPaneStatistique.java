package vue;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GridPaneStatistique extends GridPane {
    private Label labelScenario;
    public GridPaneStatistique(){

        GridPane statistiques = new GridPane();
        statistiques.setGridLinesVisible(true);

        this.getChildren().add(statistiques);
    }
}
