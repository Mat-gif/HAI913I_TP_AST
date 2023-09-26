---
Authors:
Parcours: ICO
Title: Calcul statistique pour une application OO
---

# Utilisation de l'application

Une interface utilisateur sous forme d'exécutable (.exe) est disponible en téléchargement via ce lien. L'exécutable ne contient aucun programme malveillant et est développé dans le cadre de ce TP. Vous y trouverez toutes les fonctionnalités requises pour l'exercice.

1. **Sélection du Projet à Analyser**

   - Sélectionnez le projet que vous souhaitez analyser dans vos fichiers.

2. **Choix de l'Analyse**

   - **2.1 Analyse de Base :** Vous permet de spécifier ce que vous souhaitez obtenir en répondant aux questions de 1.1 à 7 du TP.
   - **2.2 Analyse Complémentaire :** Utilisez cette option pour les questions de 8 jusqu'à la fin, avec une saisie du nombre de méthodes pour la question 11.
   - **2.3 Le Graphe :** ...

3. **Résultats**

   - Les résultats de l'analyse choisie s'affichent dans la fenêtre des résultats. Pour revenir à la page d'accueil, cliquez sur le bouton "Terminer".

# Structure de la solution

Nous avons divisé la solution en 4 packages pour une organisation claire et modulaire :

- **parsers**
- **processor**
- **ui**
- **visitor**

## Ui

Dans ce package, vous trouverez les éléments liés à l'interface graphique de l'application ainsi que les composants d'interaction avec l'utilisateur.

- **controller**
  - LabelMap.java
  - SelectProjetController.java
- **parameter**
  - MyViewParameter.java
- **template**
  - CustomPanel :
    - CheckBoxPanelTemplate.java
    - FolderChooserTemplate.java
  - MainFrame.java

## Parser

Ce package est dédié à la logique du parsing. Il contient les composants responsables de l'extraction et de la transformation des données d'entrée, notamment le chemin du projet.

- **Parser**
  - EclipseJDTParser.java
  - Parser.java

## Processor



## Visitor


