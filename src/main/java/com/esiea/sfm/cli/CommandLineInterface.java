package com.esiea.sfm.cli;

import java.util.Scanner;

public class CommandLineInterface {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            String command = scanner.nextLine();

            if ("exit".equalsIgnoreCase(command)) {
                running = false;
                System.out.println("Fermeture de l'application.");
            } else {
                System.out.println("Commande inconnue : " + command);
            }
        }
    }

    private void displayMenu() {
        System.out.println();
        System.out.println("=== Secure File Manager ===");
        System.out.println("Commandes disponibles :");
        System.out.println(" - exit : quitter");
        System.out.print("> ");
    }
}
