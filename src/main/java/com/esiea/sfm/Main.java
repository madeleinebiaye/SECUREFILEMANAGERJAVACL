package com.esiea.sfm;

import com.esiea.sfm.cli.CommandLineInterface;
import com.esiea.sfm.application.FileService;
import com.esiea.sfm.infrastructure.filesystem.LocalFileRepository;

public class Main {
    public static void main(String[] args) {
        LocalFileRepository repository = new LocalFileRepository();
        FileService fileService = new FileService(repository);
        CommandLineInterface cli = new CommandLineInterface(fileService);

        cli.start();
    }
}