package com.esiea.sfm.cli;

public class CommandParser {

    public Command parse(String input) {
        return switch (input.toLowerCase()) {
            case "exit" -> Command.EXIT;
            case "help" -> Command.HELP;
            default -> Command.UNKNOWN;
        };
    }

    public enum Command {
        HELP,
        EXIT,
        UNKNOWN
    }
}
