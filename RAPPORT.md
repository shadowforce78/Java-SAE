# Rapport de Projet SAÉ 2.01/2.02 - Application d'Optimisation d'Itinéraires Commerciaux

## Introduction

Cette application a été développée dans le cadre des SAÉ 2.01 (Développement d'une application) et 2.02 (Exploration algorithmique d'un problème). Elle résout un problème concret d'optimisation logistique : **la planification d'itinéraires pour des commerciaux devant effectuer des transactions entre vendeurs et acheteurs répartis dans différentes villes**.

### Objectifs de l'application

L'objectif principal est de calculer des parcours optimaux minimisant la distance totale parcourue, tout en respectant une contrainte fondamentale : pour chaque transaction, le commercial doit impérativement visiter le vendeur avant l'acheteur. De plus, tous les itinéraires doivent commencer et se terminer à Vélizy, ville de départ de l'entreprise.

### Problématique métier

Dans un contexte commercial réel, l'optimisation des déplacements représente un enjeu économique majeur. Réduire les distances parcourues permet de :
- Diminuer les coûts de transport (carburant, usure des véhicules)  
- Augmenter la productivité en libérant du temps pour plus de transactions
- Réduire l'impact environnemental des déplacements

L'application propose trois approches algorithmiques différentes, chacune adaptée à des contextes spécifiques selon la taille du problème et les contraintes de temps de calcul.

### Technologies utilisées

- **Langage** : Java 17
- **Framework de build** : Maven  
- **Interface utilisateur** : JavaFX
- **Architecture** : Modèle-Vue-Contrôleur (MVC)
- **Tests** : JUnit 5
- **Structure de données** : Collections Java (HashMap, ArrayList, PriorityQueue)

## 2. Conception

### 2.1. Architecture générale - Modèle-Vue-Contrôleur (MVC)

L'application suit rigoureusement le patron de conception **Modèle-Vue-Contrôleur (MVC)**, garantissant une séparation claire des responsabilités et une maintenabilité optimale du code.

#### 2.1.1. Vue d'ensemble de l'architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      VUE        │    │   CONTRÔLEUR    │    │     MODÈLE      │
│                 │    │                 │    │                 │
│ - Interface     │◄──►│ - Gestion       │◄──►│ - Logique       │
│   utilisateur   │    │   événements    │    │   métier        │
│ - Affichage     │    │ - Coordination  │    │ - Algorithmes   │
│ - Interactions  │    │   MVC           │    │ - Données       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 2.2. Composant Modèle (`package modele`)

Le modèle encapsule toute la logique métier de l'application. Il est structuré autour de plusieurs sous-systèmes :

#### 2.2.1. Classes de données métier

**`Ville.java`**
- **Rôle** : Représente une ville avec ses coordonnées géographiques
- **Attributs** :
  - `String nom` : Nom de la ville
  - `double x, y` : Coordonnées géographiques
- **Justification** : Structure simple mais essentielle pour le calcul des distances

**`Membre.java`**
- **Rôle** : Représente un vendeur ou acheteur
- **Attributs** :
  - `String pseudo` : Identifiant unique du membre
  - `Ville ville` : Ville de résidence
- **Structure de données** : Utilisation d'un lien direct vers l'objet `Ville` pour éviter les recherches répétées

**`Vente.java`**
- **Rôle** : Représente une transaction entre un vendeur et un acheteur
- **Attributs** :
  - `Membre vendeur` : Le vendeur de la transaction
  - `Membre acheteur` : L'acheteur de la transaction
- **Contrainte métier** : Assure la contrainte fondamentale vendeur → acheteur

**`Scenario.java`**
- **Rôle** : Conteneur pour un ensemble de ventes à traiter
- **Attributs** :
  - `String nom` : Nom du scénario
  - `List<Vente> ventes` : Liste ordonnée des ventes
- **Choix de structure** : `ArrayList` pour garantir l'ordre et permettre l'accès indexé

#### 2.2.2. Système de graphe et distances

**`CarteGraph.java`**
- **Rôle** : Gestionnaire du graphe des villes et des distances
- **Structure de données principale** :
```java
private Map<Pair<Ville, Ville>, Integer> distances;
```
- **Justification du choix** :
  - `HashMap` pour un accès en O(1) aux distances
  - `Pair<Ville, Ville>` comme clé pour représenter les arêtes du graphe
  - Gestion de la symétrie automatique (distance A→B = distance B→A)

**`Pair.java`**
- **Rôle** : Classe utilitaire pour représenter une paire ordonnée
- **Implémentation** : Override des méthodes `equals()` et `hashCode()` pour utilisation comme clé de HashMap
- **Avantage** : Permet d'indexer efficacement les distances entre villes

#### 2.2.3. Parseurs et gestion des fichiers

**`DistanceParser.java`**
- **Rôle** : Lecture et parsing du fichier des distances
- **Méthode principale** :
```java
public static Map<Pair<Ville, Ville>, Integer> lireFichierDistances(String cheminFichier)
```
- **Traitement** : Parsing ligne par ligne avec gestion des erreurs et validation des données

**`ScenarioParser.java`**
- **Rôle** : Lecture et création des scénarios depuis les fichiers
- **Fonctionnalités** :
  - Parsing des fichiers de scénarios (format "Vendeur -> Acheteur")
  - Résolution des références vers les membres
  - Validation de la cohérence des données

#### 2.2.4. Interface et algorithmes

**`IAlgorithme.java`** (Interface)
- **Rôle** : Contrat uniforme pour tous les algorithmes de parcours
- **Méthodes** :
```java
List<Ville> genererItineraire(Scenario scenario);
int calculerDistanceTotale(List<Ville> itineraire);
String getNom();
```
- **Avantage** : Polymorphisme permettant l'interchangeabilité des algorithmes

### 2.3. Composant Vue (`package vue`)

Le composant Vue gère entièrement l'interface utilisateur avec JavaFX.

#### 2.3.1. Architecture de l'interface

**`HelloApplication.java`**
- **Rôle** : Point d'entrée de l'application JavaFX
- **Responsabilité** : Initialisation de la fenêtre principale et du système de vues

**`fenetrePrincipale.java`**
- **Rôle** : Fenêtre principale de l'application
- **Structure** : Organisation en panneaux spécialisés

#### 2.3.2. Panneaux spécialisés

**`HBoxAffichage.java`**
- **Rôle** : Conteneur principal organisant la vue en zones gauche et droite
- **Layout** : HBox pour une disposition horizontale

**`VBoxGauche.java`** et **`VBoxDroite.java`**
- **Rôle** : Panneaux spécialisés pour différentes fonctionnalités
- **Organisation** : VBox pour un empilement vertical des composants

**`GridPaneModification.java`**, **`GridPaneStatistique.java`**, **`GridPaneCreation.java`**
- **Rôle** : Interfaces spécialisées pour :
  - Modification des scénarios existants
  - Affichage des statistiques et résultats
  - Création de nouveaux scénarios
- **Layout** : GridPane pour une disposition tabulaire

#### 2.3.3. Composants d'affichage

**`TableVente.java`**
- **Rôle** : Tableau d'affichage des ventes d'un scénario
- **Structure** : TableView JavaFX avec colonnes configurées

**`StackPaneParcours.java`**
- **Rôle** : Zone d'affichage des résultats de parcours
- **Fonctionnalités** : Affichage des itinéraires et distances

### 2.4. Composant Contrôleur (`package controleur`)

#### 2.4.1. Contrôleur principal

**`Controleur.java`**
- **Rôle** : Gestionnaire principal des événements de l'interface
- **Pattern** : Implementation d'`EventHandler<Event>`
- **Responsabilités** :
  - Capture des événements boutons et menus
  - Coordination entre Vue et Modèle
  - Gestion des changements d'état de l'application

#### 2.4.2. Contrôleur spécialisé

**`AlgorithmeController.java`**
- **Rôle** : Contrôleur dédié à la gestion des algorithmes
- **Pattern** : Singleton pour garantir une instance unique
- **Fonctionnalités** :
  - Enregistrement et gestion des algorithmes disponibles
  - Exécution des algorithmes avec paramètres
  - Comparaison des performances
  - Recommandation automatique d'algorithmes

**Structure interne** :
```java
private static AlgorithmeController instance;
private Map<String, IAlgorithme> algorithmes;
private CarteGraph carte;
```

### 2.5. Utilitaires et extensions

#### 2.5.1. Système de benchmark

**`BenchmarkAlgorithmes.java`**
- **Rôle** : Évaluation comparative des performances des algorithmes
- **Métriques** :
  - Temps d'exécution (précision nanoseconde)
  - Qualité de la solution (distance totale)
  - Ratio efficacité/performance

#### 2.5.2. Sauvegarde et persistance

**`SauvegardeScenario.java`**
- **Rôle** : Gestion de la sauvegarde de nouveaux scénarios
- **Fonctionnalités** :
  - Génération automatique de noms de fichiers uniques
  - Validation du format des données
  - Écriture dans le format standard de l'application

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
### 2.2. Algorithme de Parcours Heuristique (`ParcoursHeuristique.java`)

**Description :**

Cet algorithme implémente une approche gloutonne pour optimiser l'itinéraire. À chaque étape, il choisit la destination la plus proche parmi les destinations valides, tout en respectant la contrainte fondamentale que le vendeur doit être visité avant l'acheteur pour chaque vente.

L'algorithme fonctionne selon la logique suivante :
1. Démarrage à la ville du vendeur de la première vente.
2. À chaque étape, identification des destinations possibles :
   - Acheteurs dont le vendeur a déjà été visité (priorité absolue)
   - Vendeurs pas encore visités
3. Sélection de la destination la plus proche selon la distance euclidienne.
4. Mise à jour des états des ventes (vendeur visité, vente complète).

**Analyse de Complexité :**

Soit *N* le nombre de ventes et *V* le nombre total de villes à visiter.

*   **`genererItineraire(Scenario scenario)`**:
    *   À chaque itération de la boucle principale, l'algorithme parcourt toutes les ventes pour trouver la destination la plus proche (O(N)).
    *   Il y a au maximum V itérations (nombre de villes dans l'itinéraire final).
    *   La complexité temporelle est donc **O(V × N)**, où V ≤ 2N dans le pire cas, donnant O(N²).
    *   La complexité spatiale est **O(N)** pour les structures de données auxiliaires (HashSet).

*   **`calculerDistanceTotale(List<Ville> itineraire)`**: Identique à `ParcoursSimple` - **O(V)**.

**Nom de l'algorithme retourné par `getNom()`**: "Heuristique Glouton"

---
### 2.3. Algorithme des K Meilleures Solutions (`KMeilleuresSolutions.java`)

**Description :**

Cet algorithme utilise une approche de backtracking pour explorer exhaustivement l'espace des solutions possibles et identifier les k meilleures selon la distance totale. Il maintient une file de priorité (PriorityQueue) des k meilleures solutions trouvées jusqu'à présent.

Le processus de backtracking :
1. État initial : premier vendeur visité, états des ventes initialisés.
2. À chaque niveau de récursion :
   - Génération de toutes les destinations possibles valides
   - Pour chaque destination : exploration récursive avec mise à jour d'état
   - Élagage des branches dont la distance partielle dépasse déjà la k-ème meilleure solution
3. Condition d'arrêt : toutes les ventes sont complètes.
4. Sauvegarde de la solution si elle fait partie des k meilleures.

**Analyse de Complexité :**

*   **`genererKMeilleuresSolutions(Scenario scenario)`**:
    *   Dans le pire cas, l'algorithme explore toutes les permutations possibles des destinations.
    *   Le nombre d'états possibles peut être exponentiel : **O(V!)** où V est le nombre de villes.
    *   Cependant, l'élagage réduit significativement cet espace dans la pratique.
    *   La complexité spatiale inclut la pile de récursion : **O(V)** plus la PriorityQueue : **O(k)**.

*   **`genererItineraire(Scenario scenario)`**: Appelle `genererKMeilleuresSolutions()` et retourne la meilleure - même complexité temporelle.

*   **Optimisations implementées** :
    *   Élagage par borne supérieure (branch and bound)
    *   Évitement des états redondants
    *   File de priorité pour maintenir efficacement les k meilleures solutions

**Nom de l'algorithme retourné par `getNom()`**: "K Meilleures Solutions"

## 3. Système de Benchmark et Analyse Comparative

### 3.1. Architecture du Système de Benchmark (`BenchmarkAlgorithmes.java`)

Le système de benchmark a été conçu pour évaluer objectivement les performances des trois algorithmes selon deux critères principaux :

1. **Qualité de la solution** : Distance totale de l'itinéraire généré
2. **Performance temporelle** : Temps d'exécution de l'algorithme

**Fonctionnalités principales :**

*   **Mesure précise du temps** : Utilisation de `System.nanoTime()` pour une précision au niveau nanoseconde
*   **Comparaison multi-scénarios** : Tests automatisés sur plusieurs scénarios de complexité variable
*   **Recommandation automatique** : Sélection intelligente de l'algorithme optimal selon la taille du problème
*   **Rapports détaillés** : Classements par qualité et par vitesse, statistiques aggregées

### 3.2. Stratégie de Recommandation Automatique

Le système implémente une stratégie de recommandation basée sur la taille du scénario :

```java
public String recommanderAlgorithme(Scenario scenario) {
    int nbVentes = scenario.getVentes().size();
    
    if (nbVentes <= 5) {
        return "K Meilleures Solutions"; // Exhaustivité pour petits problèmes
    } else if (nbVentes <= 15) {
        return "Heuristique Glouton"; // Compromis qualité/vitesse
    } else {
        return "Parcours Simple"; // Vitesse pour gros problèmes
    }
}
```

Cette stratégie reflète le compromis fondamental entre qualité de solution et temps de calcul.

### 3.3. Adaptation des algorithmes pour commencer et terminer à Vélizy

Une exigence importante du projet était que tous les itinéraires commencent et se terminent à la ville de Vélizy. Les trois algorithmes ont été adaptés pour respecter cette contrainte :

#### 3.3.1. Modification de l'algorithme de Parcours Simple

L'algorithme de parcours simple a été modifié pour :
1. Commencer l'itinéraire à Vélizy plutôt qu'à la ville du premier vendeur
2. Ajouter un retour à Vélizy à la fin de l'itinéraire si nécessaire

```java
// Identification de la ville de Vélizy
Ville velizy = null;
for (Ville ville : carte.getToutesLesVilles()) {
    if (ville.getNom().equalsIgnoreCase("Velizy")) {
        velizy = ville;
        break;
    }
}

// Début à Vélizy
Ville villeActuelle = velizy;
itineraire.add(villeActuelle);

// Parcours des ventes...

// Retour à Vélizy à la fin si nécessaire
if (!villeActuelle.equals(velizy)) {
    itineraire.add(velizy);
}
```

#### 3.3.2. Modification de l'algorithme Heuristique

L'algorithme heuristique a également été adapté pour commencer et terminer à Vélizy, tout en conservant sa logique d'optimisation gloutonne. Cette adaptation permet de respecter la contrainte du point de départ/arrivée tout en minimisant la distance totale parcourue.

#### 3.3.3. Modification de l'algorithme K Meilleures Solutions

L'algorithme des K meilleures solutions a nécessité une adaptation plus complexe, notamment dans la fonction de backtracking :

```java
// Commencer le backtracking à partir de Vélizy
itineraireActuel.add(velizy);

// Lorsque toutes les ventes sont complètes, ajouter le retour à Vélizy
if (ventesCompletes.size() == ventes.size()) {
    // Ajouter le retour à Vélizy si nécessaire
    Ville derniereVille = itineraireActuel.get(itineraireActuel.size() - 1);
    int distanceFinale = distanceActuelle;
    
    List<Ville> itineraireComplet = new ArrayList<>(itineraireActuel);
    if (!derniereVille.equals(velizy)) {
        int distanceRetour = carte.getDistance(derniereVille, velizy);
        if (distanceRetour != Integer.MAX_VALUE) {
            itineraireComplet.add(velizy);
            distanceFinale += distanceRetour;
        } else {
            return; // Impossible de retourner à Vélizy, solution invalide
        }
    }
    
    // Ajouter cette solution si elle fait partie des k meilleures
    // ...
}
```

Cette adaptation garantit que toutes les solutions explorées par l'algorithme commencent et terminent à Vélizy, permettant une comparaison équitable entre les différents algorithmes.

## 4. Tests et Validation

### 4.1. Stratégie de Tests Unitaires

Pour assurer la qualité et la fiabilité du code, une stratégie complète de tests unitaires a été mise en place pour les composants clés du modèle, en particulier les algorithmes de parcours. Trois classes de tests ont été créées :

1. **ParcoursSimpleTest** : Tests de l'algorithme de parcours simple
2. **ParcoursHeuristiqueTest** : Tests de l'algorithme heuristique
3. **KMeilleuresSolutionsTest** : Tests de l'algorithme des k meilleures solutions

Ces tests vérifient plusieurs aspects critiques :

#### 4.1.1. Validité des itinéraires générés

Tous les itinéraires générés doivent respecter les contraintes fondamentales :

```java
@Test
public void testGenererItineraire() {
    List<Ville> itineraire = parcours.genererItineraire(scenario);
    
    // Vérifier que l'itinéraire n'est pas vide
    assertFalse(itineraire.isEmpty(), "L'itinéraire ne devrait pas être vide");
    
    // Vérifier que l'itinéraire commence et se termine à Velizy
    assertEquals("Velizy", itineraire.get(0).getNom(), "L'itinéraire doit commencer à Velizy");
    assertEquals("Velizy", itineraire.get(itineraire.size() - 1).getNom(), 
            "L'itinéraire doit terminer à Velizy");
    
    // Vérifier que l'itinéraire contient toutes les ventes
    assertTrue(verifierToutesVentesCouvertes(itineraire, scenario),
            "L'itinéraire doit couvrir toutes les ventes du scénario");
}
```

#### 4.1.2. Respect de la contrainte vendeur → acheteur

```java
@Test
public void testContrainteVendeurAcheteur() {
    List<Ville> itineraire = parcours.genererItineraire(scenario);
    
    // Vérifier que pour chaque vente, le vendeur est visité avant l'acheteur
    for (Vente vente : scenario.getVentes()) {
        Ville villeVendeur = vente.getVendeur().getVille();
        Ville villeAcheteur = vente.getAcheteur().getVille();
        
        int indexVendeur = trouverPremiereOccurrence(itineraire, villeVendeur);
        int indexAcheteur = trouverDerniereOccurrence(itineraire, villeAcheteur);
        
        // Vérifications...
        assertTrue(indexVendeur < indexAcheteur, 
                "Le vendeur doit être visité avant l'acheteur pour chaque vente");
    }
}
```

#### 4.1.3. Précision du calcul de distance

```java
@Test
public void testCalculerDistanceTotale() {
    List<Ville> itineraire = parcours.genererItineraire(scenario);
    int distanceTotale = parcours.calculerDistanceTotale(itineraire);
    
    // Vérifier que la distance est positive
    assertTrue(distanceTotale > 0, "La distance totale doit être positive");
    
    // Recalculer manuellement la distance pour vérification
    int distanceManuelle = 0;
    for (int i = 0; i < itineraire.size() - 1; i++) {
        int distance = carte.getDistance(itineraire.get(i), itineraire.get(i + 1));
        distanceManuelle += distance;
    }
    
    // Vérifier que les deux calculs correspondent
    assertEquals(distanceManuelle, distanceTotale, 
            "La distance calculée automatiquement doit correspondre au calcul manuel");
}
```

### 4.2. Benchmarking et Comparaison des Performances

En plus des tests unitaires qui valident la correction des algorithmes, des tests de performance ont été implémentés pour comparer objectivement les trois approches :

```java
@Test
public void testComparaisonPerformance() {
    // Charger un scénario
    Scenario petitScenario = ScenarioParser.lireFichierScenario(fichierScenario.getPath(), fichierMembres.getPath());
    
    // Génération de l'itinéraire avec les trois algorithmes
    ParcoursSimple parcoursSimple = new ParcoursSimple(carte);
    ParcoursHeuristique parcoursHeuristique = new ParcoursHeuristique(carte);
    KMeilleuresSolutions parcoursK = new KMeilleuresSolutions(carte, 3);
    
    // Mesure du temps d'exécution et des résultats
    // ...
    
    System.out.println("=== COMPARAISON DES PERFORMANCES ===");
    System.out.println("Temps parcours simple: " + (finSimple - debutSimple) + " ms");
    System.out.println("Temps parcours heuristique: " + (finHeuristique - debutHeuristique) + " ms");
    System.out.println("Temps K meilleures solutions: " + (finK - debutK) + " ms");
    System.out.println("Distance parcours simple: " + distanceSimple + " km");
    System.out.println("Distance parcours heuristique: " + distanceHeuristique + " km");
    System.out.println("Distance K meilleures solutions: " + distanceK + " km");
}
```

Les résultats de ces tests confirment le compromis entre qualité de solution et temps de calcul :
- L'algorithme K Meilleures Solutions trouve généralement les itinéraires les plus courts mais devient prohibitif pour les grands scénarios
- L'algorithme Heuristique offre un bon compromis qualité/temps pour les scénarios de taille moyenne
- L'algorithme de Parcours Simple est le plus rapide mais produit des itinéraires plus longs