module sae.velizy.javasae {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens sae.velizy.javasae to javafx.fxml;
    exports sae.velizy.javasae;
    exports vue;
    exports modele;
    exports controleur;
}