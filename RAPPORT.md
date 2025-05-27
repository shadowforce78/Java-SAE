# Rapport d'Analyse et de Performance

## 1. Introduction et Architecture du Projet

Ce projet met en œuvre une application permettant de calculer des itinéraires optimisés pour des commerciaux devant visiter des clients. L'architecture logicielle suit le patron de conception **Modèle-Vue-Contrôleur (MVC)** :

*   **Modèle (`modele`)**: Contient la logique métier de l'application.
    *   Les classes comme `Ville`, `Membre`, `Vente`, `Scenario` représentent les données manipulées.
    *   `CarteGraph` gère le graphe des villes et les distances.
    *   Les algorithmes de calcul d'itinéraire (implémentant `IAlgorithme` comme `ParcoursSimple`) se trouvent également dans ce package.
    *   `DistanceParser` et `ScenarioParser` s'occupent de la lecture des données depuis les fichiers.
*   **Vue (`vue`)**: Responsable de l'interface utilisateur (IHM).
    *   Des classes comme `fenetrePrincipale`, `GridPaneCreation`, `TableParcours` (probablement utilisant JavaFX ou Swing, basé sur `HelloApplication.java` et `hello-view.fxml`) construisent et affichent les informations à l'utilisateur.
*   **Contrôleur (`controleur`)**: Fait le lien entre le Modèle et la Vue.
    *   `Controleur` (et potentiellement `AlgorithmeController`, `HelloController`) reçoit les actions de l'utilisateur depuis la Vue, interagit avec le Modèle pour traiter ces actions (par exemple, charger un scénario, lancer un algorithme), et met à jour la Vue avec les résultats.

Cette séparation des préoccupations permet une meilleure maintenabilité et testabilité du code.

## 2. Description des Algorithmes et Analyse de Complexité

### 2.1. Algorithme de Parcours Simple (`ParcoursSimple.java`)

**Description :**

Cet algorithme, implémentant l'interface `IAlgorithme`, génère un itinéraire en suivant l'ordre séquentiel des ventes définies dans un scénario. Pour chaque vente, il s'assure que le commercial passe d'abord par la ville du vendeur, puis par la ville de l'acheteur.

L'itinéraire commence à la ville du vendeur de la première vente. Ensuite, pour chaque vente :
1.  Si le commercial n'est pas déjà dans la ville du vendeur, cette ville est ajoutée à l'itinéraire.
2.  La ville de l'acheteur est ensuite ajoutée à l'itinéraire.

**Analyse de Complexité :**

Soit *N* le nombre de ventes dans un scénario.

*   **`genererItineraire(Scenario scenario)`**:
    *   La méthode parcourt la liste des ventes une fois. Pour chaque vente, elle effectue un nombre constant d'opérations (accès aux villes, ajout à la liste d'itinéraire).
    *   La complexité temporelle est donc **O(N)**, où N est le nombre de ventes.
    *   La complexité spatiale est **O(V_itineraire)**, où V_itineraire est le nombre de villes dans l'itinéraire généré (au maximum 2N + 1).

*   **`calculerDistanceTotale(List<Ville> itineraire)`**:
    *   La méthode parcourt la liste des villes de l'itinéraire une fois. Pour chaque paire de villes consécutives, elle récupère la distance (supposons que `carte.getDistance()` soit en O(1) si les distances sont stockées dans une structure de données efficace comme une HashMap de paires de villes).
    *   Soit *M* le nombre de villes dans l'itinéraire. La complexité temporelle est **O(M)**.
    *   La complexité spatiale est **O(1)** (quelques variables locales).

**Nom de l'algorithme retourné par `getNom()`**: "Parcours Simple"

---
*(Les sections pour d'autres algorithmes, le benchmark, les captures console et les temps de calcul seront ajoutées ici au fur et à mesure de leur développement.)*

