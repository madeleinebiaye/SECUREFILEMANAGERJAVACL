package com.esiea.sfm.cli;

public class CommandParser {

    public record ParseResult(Commande commande, String argument) {}

    public enum Commande {
        CREATE, C_REP, CD, READ, DELETE, HELP, LS, UPDATE, EXIT, UNKNOWN
    }

    public ParseResult parse(String input) {
        if (input == null || input.isBlank()) {
            return new ParseResult(Commande.UNKNOWN, null);
        }

        String[] parts = input.trim().split("\\s+", 2);
        String comm = parts[0].toLowerCase(); // c pour éviter les erreurs de frappe
        String argu;
        if (parts.length > 1) {
            argu = parts[1];
        } else {
            argu = null;
        }


        Commande commande = switch (comm) {
            case "cf"     -> Commande.CREATE;
            case "cr"     -> Commande.C_REP;
            case "cd"     -> Commande.CD;
            case "lire"   -> Commande.READ;
            case "supp"   -> Commande.DELETE;
            case "ls"     -> Commande.LS;
            case "edit"   -> Commande.UPDATE;
            case "aide"   -> Commande.HELP;
            case "exit"   -> Commande.EXIT;
            default       -> Commande.UNKNOWN;
        };

        return new ParseResult(commande, argu);
    }
}