package vue;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Vente;

public class TableVente extends TableView<Vente>{
    public TableVente(){
        TableColumn<Vente, String> vendeurColumn = new TableColumn<>("Vendeur");
        TableColumn<Vente, String> acheteurColumn = new TableColumn<>("Acheteur");

        // Utilisation des nouvelles méthodes formatées pour afficher "Pseudo (Ville)"
        vendeurColumn.setCellValueFactory(new PropertyValueFactory<>("vendeurFormate"));
        acheteurColumn.setCellValueFactory(new PropertyValueFactory<>("acheteurFormate"));

        this.getColumns().add(vendeurColumn);
        this.getColumns().add(acheteurColumn);

        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }
}
