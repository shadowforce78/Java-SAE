package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConstantesVues {
    private static final List<String> ITEMS_MENU_SCENARIO = importScenarios();

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
}
