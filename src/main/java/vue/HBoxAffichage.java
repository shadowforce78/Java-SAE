package vue;

import javafx.scene.layout.HBox;

public class HBoxAffichage extends HBox {
    private static VBoxGauche vBoxGauche;
    private static VBoxDroite vBoxDroite;
    public HBoxAffichage(){
        super(30);
        vBoxGauche = new VBoxGauche();
        vBoxDroite = new VBoxDroite();

        this.getChildren().addAll(vBoxGauche, vBoxDroite);
    }

    public static VBoxGauche getvBoxGauche() {
        return vBoxGauche;
    }

    public static VBoxDroite getvBoxDroite() {
        return vBoxDroite;
    }
}
