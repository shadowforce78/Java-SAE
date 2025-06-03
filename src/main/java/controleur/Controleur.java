package controleur;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import modele.CarteGraph;
import modele.DistanceParser;
import modele.KMeilleuresSolutions;
import modele.Membre;
import modele.Pair;
import modele.ParcoursHeuristique;
import modele.ParcoursSimple;
import modele.SauvegardeScenario;
import modele.Scenario;
import modele.ScenarioParser;
import modele.TransactionFinder;
import modele.Ville;
import vue.GridPaneCreation;
import vue.GridPaneModification;
import vue.GridPaneStatistique;
import vue.HBoxAffichage;
import vue.StackPaneParcours;
import vue.VBoxDroite;
import vue.VBoxGauche;
import vue.VBoxRoot;

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

        if (event.getSource() instanceof Button) {
            if (((Button) event.getSource()).getUserData().equals("Suppression")) {
                System.out.println("Suppression");
                TransactionFinder transactions = new TransactionFinder(vBoxGauche.getScenario());
                if (transactions.containsTransaction(modification.getVendeur(), modification.getClient())) {
                    transactions.removeTransaction(modification.getVendeur(), modification.getClient());
                } else {
                    modification.alertModification(0);
                }
            } else if (((Button) event.getSource()).getUserData().equals("Modification")) {
                System.out.println("Modification");
                TransactionFinder transactions = new TransactionFinder(vBoxGauche.getScenario());
                if (transactions.containsTransaction(modification.getVendeur(), modification.getClient())) {
                    transactions.modifyTransaction(modification.getClient(), modification.getVendeur(),
                            modification.getNewClient(), modification.getNewVendeur());
                } else {
                    modification.alertModification(1);
                }
            } else if (((Button) event.getSource()).getUserData().equals("Ajout")) {
                System.out.println("Ajout");
                TransactionFinder transactions = new TransactionFinder(vBoxGauche.getScenario());
                if (!transactions.containsTransaction(modification.getVendeur(), modification.getClient())
                        && !modification.isVendeurAndClientVides()) {
                    transactions.addTransaction(modification.getClient(), modification.getVendeur());
                } else {
                    modification.alertModification(2);
                }
            } else if (((Button) event.getSource()).getUserData().equals("Création")) {
                System.out.println("Création");
                String nom;
                if (creation.getTextNom().getText().isEmpty()) {
                    nom = SauvegardeScenario.sauvegarderScenarioAuto(scenario);
                } else {
                    nom = creation.getTextNom().getText();
                    SauvegardeScenario.sauvegarderScenario(scenario, nom);
                }
                MenuItem nvItem = new MenuItem(nom + ".txt");
                nvItem.setUserData(nom);
                VBoxRoot.getMenuScenario().getItems().add(nvItem);
                nvItem.setUserData(nom);
            }

            else if (((Button) event.getSource()).getUserData().equals("Stats")) {
                System.out.println("Stats");
                tableParcours.clearAll();

                List<Ville> itineraire;
                tableParcours.getAlignementBoutons().setDisable(true);
                if (GridPaneStatistique.getAlgorithme().equals("Cours")) {
                    ParcoursSimple parcoursSimple = new ParcoursSimple(carte);
                    itineraire = parcoursSimple.genererItineraire(scenario);
                    statistique.updateKilometres(parcoursSimple.calculerDistanceTotale(itineraire));
                    tableParcours.ajoutTable(itineraire, scenario);
                } else if (GridPaneStatistique.getAlgorithme().equals("Heuristique")) {
                    ParcoursHeuristique parcoursHeuristique = new ParcoursHeuristique(carte);
                    itineraire = parcoursHeuristique.genererItineraire(scenario);
                    statistique.updateKilometres(parcoursHeuristique.calculerDistanceTotale(itineraire));
                    tableParcours.ajoutTable(itineraire, scenario);
                } else if (GridPaneStatistique.getAlgorithme().equals("K Solutions")) {
                    KMeilleuresSolutions kSolutions = new KMeilleuresSolutions(carte, GridPaneStatistique.getKValue());
                    Map<Integer, List<Ville>> mapItineraires = kSolutions.genererPlusieursItineraires(scenario);
                    tableParcours.getListe().clear();
                    for (List<Ville> itineraireFromMap : mapItineraires.values()) {
                        tableParcours.ajouterSolution(itineraireFromMap, scenario);
                    }
                    statistique.updateKilometres(
                            kSolutions.calculerDistanceTotale(kSolutions.genererItineraire(scenario)));
                }
            }
        }

        if (event.getSource() instanceof RadioMenuItem menuItem) {
            if (menuItem.getUserData().equals("Quitter")) {
                VBoxRoot.quitter();
            } else {
                String stringScenario = menuItem.getText();
                vBoxGauche.updateScenario(stringScenario);
            }
        }

        if (event.getSource() instanceof ComboBox<?> comboBox) {
            if (comboBox.getValue().equals("K Solutions")) {
                statistique.enableKSolutions();
            } else {
                statistique.disableKSolutions();
            }
        }

        if (event.getSource() instanceof RadioButton selection) {
            if (selection.getUserData().equals("toggleModif")) {
                modification.enableNewTextField();
            } else {
                modification.disableNewTextField();
            }
        }
    }

    /**
     * Récupère la liste des pseudos de tous les membres disponibles
     * 
     * @return Liste des pseudos des membres
     */
    public List<String> getMembresPseudos() {
        File dossierData = new File("pokemon_appli_data");
        File fichierMembres = new File(dossierData, "membres_APPLI.txt");

        try {
            List<Membre> membres = ScenarioParser.lireFichierMembres(fichierMembres.getPath());
            return membres.stream()
                    .map(Membre::getPseudo)
                    .toList();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier membres : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }

    /**
     * Récupère la liste des membres disponibles
     * 
     * @return Liste des membres
     */
    public List<Membre> getMembres() {
        File dossierData = new File("pokemon_appli_data");
        File fichierMembres = new File(dossierData, "membres_APPLI.txt");

        try {
            return ScenarioParser.lireFichierMembres(fichierMembres.getPath());
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier membres : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }
}
