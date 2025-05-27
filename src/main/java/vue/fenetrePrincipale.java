package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class fenetrePrincipale extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBoxRoot();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();

        File fileCss = new File("css" + File.separator + "style.css");
        scene.getStylesheets().add(fileCss.toURI().toString());
    }
}
