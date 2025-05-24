package vue;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import modele.Membre;
import modele.Vente;

public class VBoxAffichageParcours extends VBox {
    private TableView<Vente> tableVentes;
    public VBoxAffichageParcours(){
        tableVentes = new TableView<>();

        TableColumn<Vente, Membre> vendeurColumn = new TableColumn<>("Vendeur");
        TableColumn<Vente, Membre> acheteurColumn = new TableColumn<>("Acheteur");

        vendeurColumn.setCellValueFactory(new PropertyValueFactory<>("vendeur"));
        acheteurColumn.setCellValueFactory(new PropertyValueFactory<>("acheteur"));

        tableVentes.getColumns().add(vendeurColumn);
        tableVentes.getColumns().add(acheteurColumn);
        for (TableColumn<Vente, ?> table : tableVentes.getColumns()) {
            table.setResizable(false);
        }

        this.getChildren().add(tableVentes);
    }
}
