package com.esiea.sfm.infrastructure.filesystem;

import com.esiea.sfm.domain.repository.FileRepository;
import com.esiea.sfm.domain.exception.FileAccessException;
import com.esiea.sfm.domain.exception.InvalidCommandException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFileRepository implements FileRepository {

    @Override
    public void create(String filename) {
        try {
            File file = new File(filename);
            if (!file.createNewFile()) throw new FileAccessException("Le fichier existe déjà.");
        } catch (IOException e) {
            throw new FileAccessException("Impossible de créer le fichier : " + e.getMessage());
        }
    }

    @Override
    public String read(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            throw new InvalidCommandException("Le fichier '" + filename + "' est introuvable.");
        }
    }

    @Override
    public void delete(String filename) {
        File file = new File(filename);
        if (!file.exists()) throw new InvalidCommandException("Fichier introuvable.");
        if (!file.delete()) throw new FileAccessException("Échec de la suppression.");
    }

    @Override
    public void update(String filename, String content) {
        try {
            if (!new File(filename).exists()) throw new InvalidCommandException("Fichier introuvable.");
            Files.writeString(Path.of(filename), content);
        } catch (IOException e) {
            throw new FileAccessException("Erreur d'écriture : " + e.getMessage());
        }
    }

    @Override
    public void listFiles() {
        File[] files = new File(".").listFiles();
        if (files == null) throw new FileAccessException("Impossible de lire le répertoire.");
        for (File f : files) {
            System.out.println((f.isDirectory() ? "[DIR] " : "[FILE] ") + f.getName());
        }
    }
}