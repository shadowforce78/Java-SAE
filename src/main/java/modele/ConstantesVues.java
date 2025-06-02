package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConstantesVues {
    private static final List<String> ITEMS_MENU_SCENARIO = importScenarios();
    private static final String[] ITEMS_MODIFICATION_ERREURS = {
            "Suppresion impossible : le vendeur ET/OU le client est manquant ET/OU n'existe pas",
            "Modification impossible : le vendeur ET/OU le client est manquant ET/OU n'existe pas",
            "Ajout impossible : Vendeur ET/OU client manquant OU La transaction existe déjà"};

    public static List<String> importScenarios(){
        List<String> scenarios = new ArrayList<>();

        for(File fichierScenario : Objects.requireNonNull(new File("scenario").listFiles())){
            scenarios.add(fichierScenario.getName());
        }
        return scenarios;
    }

    public List<String> getItemsMenuScenarios(){
        return ITEMS_MENU_SCENARIO;
    }

    public String[] getItemsModificationErreurs(){
        return ITEMS_MODIFICATION_ERREURS;
    }
}
