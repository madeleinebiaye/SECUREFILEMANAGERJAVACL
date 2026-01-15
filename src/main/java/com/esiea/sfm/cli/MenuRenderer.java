package com.esiea.sfm.cli;

public class MenuRenderer {

    public void display() {
        System.out.println();
        System.out.println("=== Secure File Manager ===");
        System.out.println("Commandes disponibles :");
        System.out.println(" - create <nom_fichier> : créer un nouveau fichier");
        System.out.println(" - c_rep <nom_répertoire> : créer un nouveau répertoiee");
        System.out.println(" - read <nom_fichier>   : lire le contenu d'un fichier");
        System.out.println(" - update <nom_fichier> : écrire dans un fichier");
        System.out.println(" - delete <nom_fichier> : supprimer un fichier");
        System.out.println(" - ls                   : lister les fichiers");
        System.out.println(" - help                 : afficher ce menu");
        System.out.println(" - exit                 : quitter l'application");
        System.out.print("> ");
    }
}