## 🗓️ ROADMAP DÉTAILLÉE JOUR PAR JOUR

---

### 🗓️ Lundi 19 mai — *Initialisation*

**Adam** :

* Création projet Maven
* Création package modèle (`model`), lecture du sujet
* Définition architecture (MVC)

**Nourane** :

* Récupération des fichiers (membres, distances, scénarios)
* Maquette IHM sur papier ou Figma
* Prise de notes sur les interactions utilisateurs

---

### 🗓️ Mardi 20 mai — *Modèle & Base*

**Adam** :

* Implémentation : `Ville`, `Membre`, `Vente`, `Scenario`
* Système de lecture du fichier `distances.txt` en `Map<Pair<Ville,Ville>, Integer>`

**Nourane** :

* Lecture fichier membres + scénarios (`BufferedReader`)
* Construction fenêtre IHM principale (JavaFX ou Swing)

---

### 🗓️ Mercredi 21 mai — *Algo 1 simple + IHM scénarios*

**Adam** :

* Algo 1 : parcours simple (ordre vendeur → acheteur)
* Génération d’un itinéraire valide
* Affichage console

**Nourane** :

* Interface graphique : écran de sélection scénario
* Bouton “charger scénario” + affichage liste de ventes

---

### 🗓️ Jeudi 22 mai — *Algo heuristique + scénarios IHM*

**Adam** :

* Implémentation Algo 2 : heuristique glouton (ex : ville la plus proche en respectant contrainte vendeur → acheteur)
* Comparaison avec Algo 1

**Nourane** :

* Création / modification de scénario dans l’IHM
* Sauvegarde d’un scénario dans un nouveau fichier

---

### 🗓️ Vendredi 23 mai — *Algo avancé + affichage résultats*

**Adam** :

* Implémentation Algo 3 : k meilleures solutions (via backtracking ou recherche partielle)
* Tests performance scénario 1 et 2

**Nourane** :

* Écran résultats :

  * Affichage de la liste des villes
  * Affichage kilométrage total
  * Choix de l’algo via menu déroulant

---

### 🗓️ Samedi 24 mai — *Tests unitaires + intégration*

**Adam** :

* Tests JUnit :

  * Lecture des données
  * Algorithmes (résultats attendus scénario 0)

**Nourane** :

* Tests sur la partie IHM (validation entrées, interactions)
* Ajout d’icônes, design simple, ergonomie

---

### 🗓️ Dimanche 25 mai — *Connexion IHM ↔ backend*

**Adam** :

* Liaison IHM ↔ Algo ↔ Modèle
* Exécution en temps réel des algos à partir des scénarios IHM
* Création d'une interface `IAlgorithme` pour uniformiser les différents algorithmes
* Développement d'un contrôleur principal pour connecter l'IHM aux algorithmes
* Implémentation d'une méthode de calcul de distance totale pour les itinéraires générés
* Tests d'intégration entre les composants du MVC

**Nourane** :

* Ajout messages utilisateurs, confirmation, erreurs

---

### 🗓️ Lundi 26 mai — *Début du rapport*

**Adam** :

* Rapport : structure + algorithmes détaillés + complexité
* Capture console, temps de calcul
* Rapport : section structure du projet (présentation architecture MVC)
* Documentation des algorithmes avec analyse de complexité
* Capture des résultats de console pour les différents scénarios
* Mise en place d'un système de benchmark pour comparer les temps d'exécution des algorithmes

**Nourane** :

* Rapport : design de l’interface + logique utilisateur
* Prise de captures d’écran des IHM

---

### 🗓️ Mardi 27 mai — *Fichiers & polish*

**Adam** :

* Sauvegarde de nouveaux scénarios
* Sélection automatique des algos les plus efficaces selon taille scénario

**Nourane** :

* Amélioration IHM (meilleurs résultats triés, interface plus fluide)
* Nettoyage visuel + polish

---

### 🗓️ Mercredi 28 mai — *Intégration finale*

**Adam** :

* Tests lourds (scénario 2), correction bugs
* Comparaison performances entre les 3 algos

**Nourane** :

* Ajout outil de test utilisateur dans l’IHM (affichage console/logs internes)
* Complétion rapport (modèle + IHM)

---

### 🗓️ Jeudi 29 mai — *Rapport terminé*

**Adam** :

* Finalisation doc technique (structure code + algo)
* Vérification javadoc

**Nourane** :

* Finalisation doc utilisateur (captures, structure IHM)
* Récapitulatif des tâches dans le rapport

---

### 🗓️ Vendredi 30 mai – Dimanche 1 juin — *Tests finaux + Soutenance*

**Adam** :

* Relecture code
* Tests projet sur machine département
* Préparation réponses techniques (algo, structures)

**Nourane** :

* Slides ou fiche support de soutenance
* Répétition orale : démonstration + parcours utilisateur

---

### 🗓️ Lundi 2 juin soir

* Double vérification : archive `.zip` + dépôt Git à jour

---
