package vue;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import modele.Membre;
import modele.Vente;

public class TableParcours extends VBox {
    private TableView<Vente> tableParcours;
    public TableParcours(){

        tableParcours = new TableView<>();
        TableColumn<Vente, Membre> vendeurColumn = new TableColumn<>("Vendeur");
        TableColumn<Vente, Membre> acheteurColumn = new TableColumn<>("Acheteur");

        vendeurColumn.setCellValueFactory(new PropertyValueFactory<>("vendeur"));
        acheteurColumn.setCellValueFactory(new PropertyValueFactory<>("acheteur"));

        tableParcours.getColumns().add(vendeurColumn);
        tableParcours.getColumns().add(acheteurColumn);

        this.getChildren().add(tableParcours);
    }
}
