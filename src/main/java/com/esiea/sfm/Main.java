package com.esiea.sfm;

import com.esiea.sfm.cli.CommandLineInterface;
import com.esiea.sfm.application.FileService;
import com.esiea.sfm.infrastructure.filesystem.LocalFileRepository;
import com.esiea.sfm.infrastructure.security.HashService;

public class Main {
    public static void main(String[] args) {
        LocalFileRepository repository = new LocalFileRepository("user_storage");
        HashService hashService = new HashService();
        FileService fileService = new FileService(repository, hashService);
        CommandLineInterface cli = new CommandLineInterface(fileService);

        cli.start();
    }
}