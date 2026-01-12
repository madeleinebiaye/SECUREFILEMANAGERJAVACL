package com.esiea.sfm.cli;

import java.util.Scanner;

public class CommandLineInterface {

    private final Scanner scanner = new Scanner(System.in);
    private final MenuRenderer menuRenderer = new MenuRenderer();
    private final CommandParser parser = new CommandParser();

    public void start() {
        boolean running = true;

        while (running) {
            menuRenderer.display();
            String input = scanner.nextLine();

            CommandParser.Command command = parser.parse(input);

            switch (command) {
                case HELP -> {
                    // le menu est déjà affiché
                }
                case EXIT -> {
                    running = false;
                    System.out.println("Fermeture de l'application.");
                }
                case UNKNOWN -> {
                    System.out.println("Commande inconnue.");
                }
            }
        }
    }
}
