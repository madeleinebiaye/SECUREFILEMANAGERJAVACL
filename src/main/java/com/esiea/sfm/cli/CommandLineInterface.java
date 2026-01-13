package com.esiea.sfm.cli;

import com.esiea.sfm.application.FileService;

import java.util.Scanner;

public class CommandLineInterface {

    private final Scanner scanner = new Scanner(System.in);
    private final MenuRenderer menuRenderer = new MenuRenderer();
    private final CommandParser parser = new CommandParser();
    private final FileService fileService;

    public CommandLineInterface(FileService fileService) {
        this.fileService = fileService;
    }

    public void start() {
        boolean running = true;

        while (running) {
            menuRenderer.display();
            String input = scanner.nextLine();

            CommandParser.ParseResult result = parser.parse(input);

            switch (result.command()) {

                case CREATE -> {
                    try {
                        fileService.createFile(result.argument());
                        System.out.println("Fichier créé : " + result.argument());
                    } catch (RuntimeException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                }

                case HELP -> {
                    // menu déjà affiché
                }

                case EXIT -> {
                    running = false;
                    System.out.println("Fermeture...");
                }

                case READ, DELETE, LS -> {
                    System.out.println("Commande non implémentée pour l’instant.");
                }

                case UNKNOWN -> {
                    System.out.println("Commande inconnue.");
                }
            }
        }
    }
}
