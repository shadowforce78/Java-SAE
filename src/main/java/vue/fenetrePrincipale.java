package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class fenetrePrincipale extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBoxRoot();
        Scene scene = new Scene(root, 1200, 700);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/logo/pokeball_logo.png")));
        stage.getIcons().add(icon);
        stage.setTitle("APPLI");
        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(650);
        stage.setMinWidth(1000);
        stage.setMaximized(true);

        File fileCss = new File("css" + File.separator + "style.css");
        scene.getStylesheets().add(fileCss.toURI().toString());
    }
}
