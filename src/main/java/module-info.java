module sae.velizy.javasae {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens sae.velizy.javasae to javafx.fxml;

    exports vue;
    exports modele;
    exports controleur;
    opens controleur to javafx.fxml;
    opens vue to javafx.fxml;
}