package com.esiea.sfm.infrastructure.filesystem;

import com.esiea.sfm.domain.repository.FileRepository;
import com.esiea.sfm.domain.exception.FileAccessException;
import com.esiea.sfm.domain.exception.InvalidCommandException;
import com.esiea.sfm.infrastructure.logging.AppLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implémentation locale du repository de fichiers.
 */
public class LocalFileRepository implements FileRepository {

    // Création d'un fichier vide
    @Override
    public void create(String filename) {
        try {
            AppLogger.info("Création du fichier : " + filename);

            File file = new File(filename);
            if (!file.createNewFile()) {
                throw new FileAccessException("Le fichier existe déjà.");
            }

        } catch (IOException e) {
            AppLogger.error("Erreur création fichier : " + filename);
            throw new FileAccessException("Impossible de créer le fichier.");
        }
    }

    // Lecture du contenu d'un fichier
    @Override
    public String read(String filename) {
        try {
            AppLogger.info("Lecture du fichier : " + filename);
            return Files.readString(Path.of(filename));

        } catch (IOException e) {
            AppLogger.error("Erreur lecture fichier : " + filename);
            throw new InvalidCommandException("Fichier introuvable.");
        }
    }

    // Suppression d'un fichier
    @Override
    public void delete(String filename) {
        AppLogger.info("Suppression du fichier : " + filename);

        File file = new File(filename);
        if (!file.exists()) {
            throw new InvalidCommandException("Fichier introuvable.");
        }

        if (!file.delete()) {
            throw new FileAccessException("Échec de la suppression.");
        }
    }

    // Mise à jour du contenu d'un fichier
    @Override
    public void update(String filename, String content) {
        try {
            AppLogger.info("Mise à jour du fichier : " + filename);

            if (!new File(filename).exists()) {
                throw new InvalidCommandException("Fichier introuvable.");
            }

            Files.writeString(Path.of(filename), content);

        } catch (IOException e) {
            AppLogger.error("Erreur écriture fichier : " + filename);
            throw new FileAccessException("Erreur d'écriture.");
        }
    }

    // Liste les fichiers du répertoire courant
    @Override
    public void listFiles() {
        AppLogger.info("Listing des fichiers");

        File[] files = new File(".").listFiles();
        if (files == null) {
            throw new FileAccessException("Impossible de lire le répertoire.");
        }

        for (File f : files) {
            System.out.println((f.isDirectory() ? "[DIR] " : "[FILE] ") + f.getName());
        }
    }

    public void storeHash(String filename, String hash) {
        try {
            Files.writeString(Path.of(filename + ".hash"), hash);
        } catch (IOException e) {
            AppLogger.error("Impossible de sauvegarder le hash pour : " + filename);
        }
    }

    public String loadHash(String filename) {
        try {
            Path path = Path.of(filename + ".hash");
            if (Files.exists(path)) {
                return Files.readString(path);
            }
        } catch (IOException e) {
            AppLogger.error("Erreur lecture hash pour : " + filename);
        }
        return null;
    }
}
