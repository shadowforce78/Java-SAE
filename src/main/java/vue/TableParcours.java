package vue;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Membre;
import modele.Vente;

public class TableParcours extends TableView<Vente>{
    public TableParcours(){

        TableColumn<Vente, Membre> vendeurColumn = new TableColumn<>("Vendeur");
        TableColumn<Vente, Membre> acheteurColumn = new TableColumn<>("Acheteur");

        vendeurColumn.setCellValueFactory(new PropertyValueFactory<>("vendeur"));
        acheteurColumn.setCellValueFactory(new PropertyValueFactory<>("acheteur"));

        this.getColumns().add(vendeurColumn);
        this.getColumns().add(acheteurColumn);
    }
}
