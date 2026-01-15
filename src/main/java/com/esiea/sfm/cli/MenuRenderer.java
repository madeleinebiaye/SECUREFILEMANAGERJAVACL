package com.esiea.sfm.cli;

public class MenuRenderer {

    public void display() {
        System.out.println();
        System.out.println("---- Le Gestionnaire de fichiers ----");
        System.out.println("Les commandes disponibles :");
        System.out.println(" - cf <nom_fichier>     : créer un fichier");
        System.out.println(" - cr <nom_rép>         : créer un répertoiee");
        System.out.println(" - cd <nom_répertoire>  : se déplacer");
        System.out.println(" - lire <nom_fichier>   : lire un fichier");
        System.out.println(" - edit <nom_fichier>   : écrire dans un fichier");
        System.out.println(" - supp <nom_fichier>   : supprimer un fichier");
        System.out.println(" - ls                   : lister les fichiers");
        System.out.println(" - aide                 : afficher ce menu");
        System.out.println(" - exit                 : quitter le gestionnaire");
        System.out.print("> ");
    }
}