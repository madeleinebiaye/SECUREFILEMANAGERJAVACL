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
                case CREATE -> fileService.createFile(result.argument());

                case READ -> {
                    String content = fileService.readFile(result.argument());
                    System.out.println("Contenu : " + content);
                }

                case DELETE -> fileService.deleteFile(result.argument());

                case EXIT -> {
                    running = false;
                    System.out.println("Fermeture...");
                }

                case LS -> fileService.listFiles();

                case HELP -> {
                }

                case UNKNOWN -> System.out.println("Commande inconnue.");
            }
        }
    }
}