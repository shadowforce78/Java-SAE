package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioMenuItem;
import modele.*;
import vue.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Controleur implements EventHandler {

    @Override
    public void handle(Event event) {
        VBoxGauche vBoxGauche = HBoxAffichage.getvBoxGauche();
        GridPaneModification modification = VBoxGauche.getGridModif();
        GridPaneStatistique statistique = VBoxGauche.getGridStatParcours();
        TableParcours tableParcours = VBoxDroite.getTable();
        if(event.getSource() instanceof Button){
            if (((Button) event.getSource()).getUserData().equals("Modification")){
                System.out.println("Modification");
            }
            else if (((Button) event.getSource()).getUserData().equals("Création")){
                System.out.println("Création");
            }

            else if(((Button) event.getSource()).getUserData().equals("Stats")){
                System.out.println("Stats");
                try {
                    File dossierData = new File("pokemon_appli_data");
                    File dossierScenario = new File("scenario");
                    File fichierDistances = new File(dossierData, "distances.txt");
                    File fichierScenario = new File(dossierScenario, vBoxGauche.getScenario());
                    File fichierMembres = new File(dossierData, "membres_APPLI.txt");

                    Map<Pair<Ville, Ville>, Integer> distances = DistanceParser.lireFichierDistances(fichierDistances.getPath());
                    CarteGraph carte = new CarteGraph(distances);

                    Scenario scenario = ScenarioParser.lireFichierScenario(fichierScenario.getPath(), fichierMembres.getPath());
                    List<Ville> itineraire = List.of();
                    if (statistique.getAlgorithme().equals("Cours")) {
                        ParcoursSimple parcoursSimple = new ParcoursSimple(carte);
                        itineraire = parcoursSimple.genererItineraire(scenario);
                        statistique.updateKilometres(parcoursSimple.calculerDistanceTotale(itineraire));
                    }
                    else if (statistique.getAlgorithme().equals("Heuristique")) {
                        ParcoursHeuristique parcoursHeuristique = new ParcoursHeuristique(carte);
                        itineraire = parcoursHeuristique.genererItineraire(scenario);
                        statistique.updateKilometres(parcoursHeuristique.calculerDistanceTotale(itineraire));
                    }

                    tableParcours.ajoutTable(itineraire);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(event.getSource() instanceof RadioMenuItem nouveauScenario){
            String stringScenario = nouveauScenario.getText();
            vBoxGauche.updateScenario(stringScenario);
        }

        if(event.getSource() instanceof ComboBox<?> comboBox){
            if (comboBox.getValue().equals("K Solutions")){
                statistique.enableKSolutions();
            }
        }
    }
}
