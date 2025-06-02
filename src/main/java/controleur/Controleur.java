package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import modele.*;
import vue.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Controleur implements EventHandler {

    @Override
    public void handle(Event event) {
        VBoxGauche vBoxGauche = HBoxAffichage.getvBoxGauche();
        GridPaneModification modification = VBoxGauche.getGridModif();
        GridPaneCreation creation = VBoxDroite.getCreateScenario();
        GridPaneStatistique statistique = VBoxGauche.getGridStatParcours();
        StackPaneParcours tableParcours = VBoxDroite.getTable();

        File dossierData = new File("pokemon_appli_data");
        File dossierScenario = new File("scenario");
        File fichierDistances = new File(dossierData, "distances.txt");
        File fichierScenario = new File(dossierScenario, vBoxGauche.getScenario());
        File fichierMembres = new File(dossierData, "membres_APPLI.txt");

        Map<Pair<Ville, Ville>, Integer> distances;
        try {
            distances = DistanceParser.lireFichierDistances(fichierDistances.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CarteGraph carte = new CarteGraph(distances);

        Scenario scenario;
        try {
            scenario = ScenarioParser.lireFichierScenario(fichierScenario.getPath(), fichierMembres.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(event.getSource() instanceof Button){
            if (((Button) event.getSource()).getUserData().equals("Modification")){
                System.out.println("Modification");
            }
            else if (((Button) event.getSource()).getUserData().equals("Création")){
                System.out.println("Création");
                String nom;
                if (creation.getTextNom().getText().isEmpty()) {
                    nom = SauvegardeScenario.sauvegarderScenarioAuto(scenario);
                }
                else{
                    nom = creation.getTextNom().getText();
                    SauvegardeScenario.sauvegarderScenario(scenario, nom);
                }
                MenuItem nvItem = new MenuItem(nom + ".txt");
                nvItem.setUserData(nom);
                VBoxRoot.getMenuScenario().getItems().add(nvItem);
                nvItem.setUserData(nom);
            }

            else if(((Button) event.getSource()).getUserData().equals("Stats")){
                System.out.println("Stats");
                tableParcours.clearAll();

                List<Ville> itineraire;
                tableParcours.getAlignementBoutons().setDisable(true);
                if (GridPaneStatistique.getAlgorithme().equals("Cours")) {
                    ParcoursSimple parcoursSimple = new ParcoursSimple(carte);
                    itineraire = parcoursSimple.genererItineraire(scenario);
                    statistique.updateKilometres(parcoursSimple.calculerDistanceTotale(itineraire));
                    tableParcours.ajoutTable(itineraire);
                }
                else if (GridPaneStatistique.getAlgorithme().equals("Heuristique")) {
                    ParcoursHeuristique parcoursHeuristique = new ParcoursHeuristique(carte);
                    itineraire = parcoursHeuristique.genererItineraire(scenario);
                    statistique.updateKilometres(parcoursHeuristique.calculerDistanceTotale(itineraire));
                    tableParcours.ajoutTable(itineraire);
                }
                else if (GridPaneStatistique.getAlgorithme().equals("K Solutions")) {
                    KMeilleuresSolutions kSolutions = new KMeilleuresSolutions(carte, GridPaneStatistique.getKValue());
                    Map<Integer, List<Ville>> mapItineraires = kSolutions.genererPlusieursItineraires(scenario);
                    tableParcours.getListe().clear();
                    for (List<Ville> itineraireFromMap : mapItineraires.values()) {
                        tableParcours.ajouterSolution(itineraireFromMap);
                    }
                    statistique.updateKilometres(kSolutions.calculerDistanceTotale(kSolutions.genererItineraire(scenario)));
                }
            }
        }

        if(event.getSource() instanceof RadioMenuItem menuItem){
            if (menuItem.getUserData().equals("Quitter")){
                VBoxRoot.quitter();
            }
            else {
                String stringScenario = menuItem.getText();
                vBoxGauche.updateScenario(stringScenario);
            }
        }

        if(event.getSource() instanceof ComboBox<?> comboBox){
            if (comboBox.getValue().equals("K Solutions")){
                statistique.enableKSolutions();
            }
            else{
                statistique.disableKSolutions();
            }
        }
    }
}
