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
                    // Appelle le service pour créer le fichier
                    fileService.createFile(result.argument());
                }

                case DELETE -> {
                    // Appelle le service pour supprimer le fichier
                    fileService.deleteFile(result.argument());
                }

                case LS -> {
                    // Appelle le service pour lister les fichiers
                    fileService.listFiles();
                }

                case READ -> {
                    // Récupère le contenu via le service et l'affiche
                    String content = fileService.readFile(result.argument());
                    System.out.println(content);
                }

                case HELP -> {
                    // Le menu est déjà affiché par menuRenderer.display() au début de laa boucle
                }

                case EXIT -> {
                    running = false;
                    System.out.println("Fermeture de l'application...");
                }

                case UNKNOWN -> {
                    System.out.println("Commande inconnue. Tapez 'help' pour voir les commandes.");
                }
            }
        }
    }
}