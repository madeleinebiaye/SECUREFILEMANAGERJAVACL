# Secure File Manager – Java (CLI)

## Contexte du projet

Ce projet a pour objectif de développer un **gestionnaire de fichiers sécurisé en ligne de commande**, en Java, sans interface graphique.
Il est réalisé en **binôme** dans un cadre pédagogique.

Le projet suit une **approche itérative**, avec une attention particulière portée à l’architecture du code et à la séparation des responsabilités.
L’idée est de construire une base solide permettant d’ajouter progressivement des mécanismes de sécurité, en tenant compte des notions de **disponibilité**, **intégrité** et **confidentialité** des données.

---

## Architecture du projet

L’application est organisée selon une **architecture en couches**, adaptée à une application CLI :

```
CLI
 ↓
Application
 ↓
Domain
 ↓
Infrastructure
```

Cette organisation permet de mieux structurer le projet, de faciliter la compréhension du code et de préparer l’ajout de fonctionnalités de sécurité sans avoir à tout refactorer.

---

## Arborescence principale

```
src/main/java/com/esiea/sfm
├── Main.java
├── cli
│   ├── CommandLineInterface.java
│   ├── CommandParser.java
│   └── MenuRenderer.java
├── application
│   └── FileService.java
├── domain
│   ├── model
│   ├── repository
│   └── exception
├── infrastructure
│   ├── filesystem
│   ├── logging
│   └── security
└── util

target/
└── (généré automatiquement par Maven)
```

---

## Rôle des différentes couches

### CLI

* Interaction avec l’utilisateur via le terminal
* Affichage des menus
* Lecture et interprétation des commandes saisies

### Application

* Gestion des cas d’usage
* Point d’entrée des fonctionnalités
* Lien entre le domaine et l’infrastructure

### Domain

* Règles métier
* Modèles
* Exceptions métier

### Infrastructure

* Accès au système de fichiers
* Implémentations techniques
* Services liés à la journalisation et à la sécurité

---

## État actuel du projet

### Itération 1 – Mise en place des bases

* Projet Maven fonctionnel
* Architecture en couches en place
* Point d’entrée unique (`Main`)
* Interface CLI structurée
* Premières commandes fonctionnelles (création, lecture, listing)
* Gestion des erreurs via des exceptions métier
* Dépôt GitLab organisé avec des commits réguliers

### Itération 2 – Intégrité et traçabilité

* Mise en place d’un système de journalisation technique
* Calcul d’empreintes de hachage (SHA-256) lors de la création des fichiers
* Vérification de l’intégrité des fichiers à la lecture
* Détection des modifications effectuées en dehors de l’application
* Centralisation des logs dans la couche infrastructure

---

## Technologies utilisées

* Java 17
* Maven
* Git / GitLab
* IntelliJ IDEA
* Application en ligne de commande (CLI)

---

## Évolutions prévues

### Itération 3

* Chiffrement des fichiers
* Gestion sécurisée des clés
* Renforcement de la confidentialité des données

---

## Organisation du travail

Le travail est réparti entre les deux membres du binôme :

* Conception de l’architecture
* Implémentation des différentes couches
* Développement de l’interface CLI
* Tests et validation des fonctionnalités

---

## Lancer le projet

Le projet peut être exécuté depuis **IntelliJ IDEA** ou via **Maven**.
L’exécution démarre à partir de la classe `Main`.

