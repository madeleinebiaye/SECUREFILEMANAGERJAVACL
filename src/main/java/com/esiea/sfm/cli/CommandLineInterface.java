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

            try {
                switch (result.command()) {
                    case CREATE -> { fileService.createFile(result.argument()); System.out.println("Fichier créé !"); }
                    case READ   -> System.out.println(fileService.readFile(result.argument()));
                    case UPDATE -> {
                        System.out.print("Contenu : ");
                        fileService.updateFile(result.argument(), scanner.nextLine());
                    }
                    case DELETE -> { fileService.deleteFile(result.argument()); System.out.println("Supprimé !"); }
                    case LS     -> fileService.listFiles();
                    case EXIT   -> running = false;
                    default     -> System.out.println("Commande inconnue.");
                }
            } catch (RuntimeException e) {
                // C'est ici que toutes nos exceptions personnalisées arrivent !
                System.out.println("--- ERREUR : " + e.getMessage() + " ---");
            }
        }
    }
}