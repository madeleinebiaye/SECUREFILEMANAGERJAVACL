Secure File Manager – Java (CLI)
Présentation du projet

Secure File Manager est une application Java en ligne de commande (CLI) permettant de gérer des fichiers de manière sécurisée, sans interface graphique.
Le projet est réalisé en binôme, dans un cadre pédagogique, avec pour objectif principal de concevoir une architecture logicielle claire, maintenable et orientée sécurité, tout en garantissant le bon fonctionnement de l’application.

 Objectifs du projet
Développer un gestionnaire de fichiers en Java
Mettre en place une architecture en couches
Implémenter des mécanismes de sécurité des données
Assurer la confidentialité, l’intégrité et la disponibilité des fichiers
Gérer proprement les erreurs et les entrées utilisateur
Travailler en binôme avec Git

Architecture du projet
L’application est organisée selon une architecture en couches, adaptée à une application CLI :

CLI
↓
Application
↓
Domain
↓
Infrastructure


Cette organisation permet :
une bonne séparation des responsabilités
une meilleure lisibilité du code
une évolution progressive des mécanismes de sécurité
une architecture stable et extensible

Arborescence principale
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

 Rôle des couches
🔹 CLI
Interaction avec l’utilisateur via le terminal
Affichage des menus et messages
Lecture et interprétation des commandes
🔹 Application
Gestion des cas d’usage
Coordination entre la CLI et l’infrastructure
Application des règles de sécurité métier
🔹 Domain
Modèles et règles métier
Exceptions spécifiques au domaine
🔹 Infrastructure
Accès au système de fichiers
Implémentations techniques
Services de journalisation et de sécurité
Sécurité mise en œuvre
Chiffrement des données
Chiffrement symétrique des fichiers basé sur AES-256 en mode GCM
Chiffrement authentifié, garantissant à la fois :
la confidentialité
l’intégrité des données
Génération d’un vecteur d’initialisation (IV) aléatoire pour chaque chiffrement 
 Gestion de la clé
La clé de chiffrement est dérivée via PBKDF2 (HmacSHA256) à partir d’un mot de passe global
Ce choix permet :
la persistance des fichiers chiffrés entre les sessions
la stabilité du projet sans modifier l’architecture existante 
 Dans cette version, le mot de passe est global et interne à l’application.
Une gestion par mot de passe utilisateur constitue une évolution identifiée.
Intégrité des fichiers
Calcul d’une empreinte cryptographique (SHA-256) lors de la création ou modification des fichiers
Vérification de l’intégrité à la lecture
Détection des modifications effectuées en dehors de l’application 
 Technologies utilisées
Java 17
Maven
Java Cryptography Architecture (JCA)
AES-GCM / PBKDF2
Git / GitLab
IntelliJ IDEA
Application CLI
État d’avancement
Itération 1 – Fondations

Architecture en couches fonctionnelle

Interface CLI opérationnelle

Commandes de base (création, lecture, listing, suppression)

Gestion des erreurs via exceptions

 Itération 2 – Intégrité et traçabilité

Mise en place d’un système de journalisation

Calcul et vérification des empreintes SHA-256

Détection des modifications non autorisées
 Itération 3 – Chiffrement (version actuelle)

Chiffrement des fichiers avec AES-GCM

IV aléatoire pour chaque opération

Évolutions prévues

Gestion d’un mot de passe par utilisateur ou par fichier

Stockage sécurisé des clés
Clé dérivée via PBKDF2 à partir d’un mot de passe global

Persistance des fichiers chiffrés entre les sessions
Suppression sécurisée des fichiers en clair

Renforcement de la politique de sécurité

Organisation du travail

Le projet est réalisé en binôme, avec une collaboration sur :

la conception de l’architecture

l’implémentation des fonctionnalités

l’intégration des mécanismes de sécurité

les tests et la validation

 Lancer le projet
Prérequis
Java 17+
Maven
Exécution
Le projet peut être lancé :
depuis IntelliJ IDEA
ou via Maven
L’exécution démarre à partir de la classe :

Main.java
ce projet est realise par Madeleine Biaye et Enzo Jousse 
