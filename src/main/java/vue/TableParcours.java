package vue;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import modele.Vente;
import modele.Ville;

import java.util.List;

public class TableParcours extends VBox {
    private static TableView<Vente> tableParcours;
    public TableParcours(){

        tableParcours = new TableView<>();
        TableColumn<Vente, String> vendeurColumn = new TableColumn<>("Vendeur");
        TableColumn<Vente, String> acheteurColumn = new TableColumn<>("Acheteur");

        vendeurColumn.setCellValueFactory(new PropertyValueFactory<>("villeVendeur"));
        acheteurColumn.setCellValueFactory(new PropertyValueFactory<>("villeAcheteur"));

        tableParcours.getColumns().add(vendeurColumn);
        tableParcours.getColumns().add(acheteurColumn);

        tableParcours.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);


        this.getChildren().add(tableParcours);
    }

    public static TableView<Vente> getTableParcours() {
        return tableParcours;
    }

    public void ajoutTable(List<Ville> itineraire){
        tableParcours.getItems().clear();
        for (int i = 0; i < itineraire.size() - 1; i++) {
            tableParcours.getItems().add(new Vente(itineraire.get(i).getNom(), itineraire.get(i + 1).getNom()));
        }
    }
}
