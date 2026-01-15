package com.esiea.sfm.cli;

import com.esiea.sfm.application.FileService;
import java.util.Scanner;
import com.esiea.sfm.infrastructure.logging.AppLogger;

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

            try {
                switch (result.command()) {

                    case CREATE -> {
                        fileService.createFile(result.argument());

                        // Log technique
                        AppLogger.info("Création du fichier : " + result.argument());

                        // Message utilisateur
                        System.out.println("Fichier créé : " + result.argument());
                    }

                    case C_REP -> {
                        fileService.createRep(result.argument());
                        System.out.println("Répertoire créé : " + result.argument());
                    }

                    case READ -> {
                        System.out.println(fileService.readFile(result.argument()));
                    }

                    case UPDATE -> {
                        System.out.print("Contenu : ");
                        String content = scanner.nextLine();
                        fileService.updateFile(result.argument(), content);
                        System.out.println("Fichier mis à jour.");
                    }

                    case DELETE -> {
                        fileService.deleteFile(result.argument());
                        System.out.println("Element supprimé.");
                    }

                    case LS -> {
                        fileService.listFiles();
                    }

                    case HELP -> {
                        // Le menu est déjà affiché au début de la boucle
                    }

                    case EXIT -> {
                        running = false;
                        System.out.println("Fermeture...");
                    }

                    case UNKNOWN -> {
                        System.out.println("Commande inconnue.");
                    }
                }
            } catch (RuntimeException e) {
                // Toutes les exceptions métier arrivent ici
                System.out.println("--- ERREUR : " + e.getMessage() + " ---");
            }
        }
    }
}