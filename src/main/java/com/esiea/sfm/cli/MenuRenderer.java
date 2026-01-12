package com.esiea.sfm.cli;

public class MenuRenderer {

    public void display() {
        System.out.println();
        System.out.println("=== Secure File Manager ===");
        System.out.println("Commandes disponibles :");
        System.out.println(" - help : afficher ce menu");
        System.out.println(" - exit : quitter");
        System.out.print("> ");
    }
}
