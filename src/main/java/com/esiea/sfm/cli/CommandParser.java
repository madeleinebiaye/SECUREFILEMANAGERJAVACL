package com.esiea.sfm.cli;

public class CommandParser {

    public record ParseResult(Command command, String argument) {}

    public enum Command {
        CREATE, READ, DELETE, HELP, LS, UPDATE, EXIT, UNKNOWN
    }

    public ParseResult parse(String input) {
        if (input == null || input.isBlank()) {
            return new ParseResult(Command.UNKNOWN, null);
        }

        String[] parts = input.trim().split("\\s+", 2);
        String cmdPart = parts[0].toLowerCase();
        String argPart = (parts.length > 1) ? parts[1] : null;

        Command command = switch (cmdPart) {
            case "create" -> Command.CREATE;
            case "read"   -> Command.READ;
            case "delete" -> Command.DELETE;
            case "ls"     -> Command.LS;
            case "update" -> Command.UPDATE;
            case "help"   -> Command.HELP;
            case "exit"   -> Command.EXIT;
            default       -> Command.UNKNOWN;
        };

        return new ParseResult(command, argPart);
    }
}