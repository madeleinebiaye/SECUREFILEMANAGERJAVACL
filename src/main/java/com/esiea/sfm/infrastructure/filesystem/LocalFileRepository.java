package com.esiea.sfm.infrastructure.filesystem;

import com.esiea.sfm.domain.repository.FileRepository;
import com.esiea.sfm.domain.exception.FileAccessException;
import com.esiea.sfm.domain.exception.InvalidCommandException;
import com.esiea.sfm.infrastructure.logging.AppLogger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class LocalFileRepository implements FileRepository {

    private static final int IV_SIZE = 12;

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

    // Dans LocalFileRepository.java
    @Override
    public void createRep(String dirname) {
        try {
            AppLogger.info("Création du répertoire : " + dirname);
            File dir = new File(dirname);
            if (dir.exists()) {
                throw new InvalidCommandException("Le dossier existe déjà.");
            }
            if (!dir.mkdir()) {
                throw new FileAccessException("Échec de la création du dossier.");
            }
        } catch (Exception e) {
            AppLogger.error("Erreur création répertoire : " + dirname);
            throw new FileAccessException(e.getMessage());
        }
    }

    @Override
    public String read(String filename) {
        try {
            AppLogger.info("Lecture du fichier : " + filename);

            if (!Files.exists(Path.of(filename))) {
                throw new InvalidCommandException("Fichier introuvable.");
            }

            return Files.readString(Path.of(filename));

        } catch (IOException e) {
            AppLogger.error("Erreur lecture fichier : " + filename);
            throw new InvalidCommandException("Fichier introuvable.");
        }
    }

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

    @Override
    public void update(String filename, String content) {
        try {
            AppLogger.info("Mise à jour du fichier : " + filename);

            if (!Files.exists(Path.of(filename))) {
                throw new InvalidCommandException("Fichier introuvable.");
            }

            Files.writeString(Path.of(filename), content);

        } catch (IOException e) {
            AppLogger.error("Erreur écriture fichier : " + filename);
            throw new FileAccessException("Erreur d’écriture.");
        }
    }


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

    @Override
    public void writeEncrypted(String filename, byte[] data, byte[] iv) {
        try {
            AppLogger.info("Écriture chiffrée du fichier : " + filename);

            if (iv == null || iv.length != IV_SIZE) {
                throw new InvalidCommandException("IV invalide.");
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(iv);
            out.write(data);

            Files.write(Path.of(filename), out.toByteArray());

        } catch (IOException e) {
            AppLogger.error("Erreur écriture chiffrée : " + filename);
            throw new FileAccessException("Erreur écriture chiffrée.");
        }
    }

    @Override
    public byte[] readEncrypted(String filename) {
        try {
            AppLogger.info("Lecture chiffrée du fichier : " + filename);

            byte[] allBytes = Files.readAllBytes(Path.of(filename));

            if (allBytes.length <= IV_SIZE) {
                throw new InvalidCommandException("Fichier chiffré invalide.");
            }

            byte[] encrypted = new byte[allBytes.length - IV_SIZE];
            System.arraycopy(allBytes, IV_SIZE, encrypted, 0, encrypted.length);

            return encrypted;

        } catch (IOException e) {
            AppLogger.error("Erreur lecture chiffrée : " + filename);
            throw new FileAccessException("Erreur lecture chiffrée.");
        }
    }

    @Override
    public byte[] readIV(String filename) {
        try {
            AppLogger.info("Lecture IV du fichier : " + filename);

            byte[] allBytes = Files.readAllBytes(Path.of(filename));

            if (allBytes.length < IV_SIZE) {
                throw new InvalidCommandException("IV introuvable.");
            }

            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(allBytes, 0, iv, 0, IV_SIZE);

            return iv;

        } catch (IOException e) {
            AppLogger.error("Erreur lecture IV : " + filename);
            throw new FileAccessException("Erreur lecture IV.");
        }
    }

    @Override
    public void storeHash(String filename, String hash) {
        try {
            AppLogger.info("Sauvegarde hash pour : " + filename);
            Files.writeString(Path.of(filename + ".hash"), hash);

        } catch (IOException e) {
            AppLogger.error("Erreur sauvegarde hash : " + filename);
            throw new FileAccessException("Erreur sauvegarde hash.");
        }
    }

    @Override
    public String loadHash(String filename) {
        try {
            AppLogger.info("Lecture hash pour : " + filename);

            Path path = Path.of(filename + ".hash");
            if (!Files.exists(path)) {
                return null;
            }

            return Files.readString(path);

        } catch (IOException e) {
            AppLogger.error("Erreur lecture hash : " + filename);
            throw new FileAccessException("Erreur lecture hash.");
        }
    }

    @Override
    public void deleteHash(String filename) {
        try {
            AppLogger.info("Suppression hash pour : " + filename);
            Files.deleteIfExists(Path.of(filename + ".hash"));

        } catch (IOException e) {
            AppLogger.error("Erreur suppression hash : " + filename);
            throw new FileAccessException("Erreur suppression hash.");
        }
    }
}
