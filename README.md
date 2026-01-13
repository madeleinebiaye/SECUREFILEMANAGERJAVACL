# Secure File Manager – Java (CLI)

Contexte du projet
Ce projet consiste à développer un gestionnaire de fichiers sécurisé en ligne de commande, en Java, sans interface graphique.
Il est réalisé en binôme dans un cadre pédagogique et suit une approche itérative et architecturée.

L’objectif principal est de mettre en œuvre :
- une architecture logicielle propre et maintenable,
- une séparation claire des responsabilités,
- des fondations solides pour l’ajout progressif de mécanismes de sécurité tout en prenant en compte la disponibilite l'integrite et egalement la confidentialite 

## 🧱 Architecture du projet

Le projet adopte une architecture en couches, adaptée à une application CLI :

CLI
↓
Application
↓
Domain
↓
Infrastructure

Cette organisation permet :
- une forte lisibilité du code,
- une évolutivité ,
- une intégration progressive de la sécurité (hachage, chiffrement).

## 📂 Arborescence principale

src/main/java/com/esiea/sfm
├── Main.java
├── cli
│ ├── CommandLineInterface.java
│ ├── CommandParser.java
│ └── MenuRenderer.java
├── application
│ └── FileService.java
├── domain
│ ├── model
│ ├── repository
│ └── exception
├── infrastructure
│ ├── filesystem
│ ├── logging
│ └── security
└── util 
target/
└── (généré automatiquement par Maven – non versionné)
## Rôle des couches

### 🔹 CLI
- Interaction avec l’utilisateur via le terminal
- Affichage des menus
- Lecture et interprétation des commandes

### 🔹 Application
- Orchestration des cas d’usage
- Point d’entrée des fonctionnalités 
- Coordination entre domaine et infrastructure

### 🔹 Domain
- Règles 
- Modèles
- Exceptions

### 🔹 Infrastructure
- Accès au système de fichiers
- Implémentations concrètes
- Services techniques (préparés pour sécurité et journalisation)

---

##  État actuel du projet (Itération 1)

###  Réalisé
- Structure Maven fonctionnelle
- Architecture en couches complète
- Point d’entrée `Main` 
- CLI structurée (parser, renderer, interface)
- Premier cas d’usage : **création de fichier**
- Gestion des erreurs via exceptions métier
- Dépôt GitLab propre avec commits 

### En cours
- Finalisation de la boucle CLI
- Amélioration de la gestion des commandes utilisateur

---

## Technologies utilisées

- Java 17
- Maven
- Git / GitLab
- IntelliJ IDEA
- Application en ligne de commande (CLI)


## 🔜 Évolutions prévues

### Itération 2
- Journalisation technique
- Contrôle d’intégrité (hachage)
- Détection de modifications non autorisées

### Itération 3
- Chiffrement des fichiers
- Gestion sécurisée des clés
- Protection avancée des données

---

##  Organisation du travail

Le travail est réparti entre les deux membres du binôme :
- Conception de l’architecture
- Implémentation des couches métier et techniques
- Développement de l’interface CLI
- Tests et validation fonctionnelle

---

## Lancer le projet

Le projet est destiné à être exécuté depuis un IDE (IntelliJ IDEA) ou via Maven.
L’exécution démarre depuis la classe `Main`.

