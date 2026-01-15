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
import java.nio.file.Paths;

public class LocalFileRepository implements FileRepository {

    private static final int IV_SIZE = 12;
    private final Path rootPath;
    private Path currentRelativePath;

    public LocalFileRepository(String rootDir) {
        this.rootPath = Paths.get(rootDir).toAbsolutePath().normalize();
        this.currentRelativePath = Paths.get("");
    }
    // pour chemins
    private Path resolveSafePath(String inputPath) {
        Path targetPath = rootPath.resolve(currentRelativePath).resolve(inputPath).normalize();

        if (!targetPath.startsWith(rootPath)) {
            AppLogger.error("Tentative d'évasion détectée !");
            throw new InvalidCommandException("Accès refusé : Interdiction de sortir du répertoire gestionnaire !!!!!!!!!!");
        }
        return targetPath;
    }

    @Override
    public void changeDirectory(String dirname) {
        if (dirname.equals("/")) {
            return;
        }

        Path newPath = resolveSafePath(dirname);

        if (Files.isDirectory(newPath)) {
            currentRelativePath = rootPath.relativize(newPath);
            AppLogger.info("Navigation : " + getCurrentPathName());
        } else {
            throw new InvalidCommandException("Le répertoire n'existe pas : " + dirname);
        }
    }

    @Override
    public String getCurrentPathName() {
        return "/" + currentRelativePath.toString().replace(File.separator, "/");
    }


    @Override
    public void listFiles() {
        Path currentDir = rootPath.resolve(currentRelativePath); //combiné les chemins
        File[] files = currentDir.toFile().listFiles();

        if (files == null) throw new FileAccessException("Erreur de lecture répertoire");

        for (File f : files) {
            if (!f.getName().endsWith(".hash")) {
                System.out.println((f.isDirectory() ? "Répertoire : " : "Fichier : ") + f.getName());
            }
        }
    }


    // cud
    @Override
    public void create(String filename) {
        try {
            Path path = resolveSafePath(filename);
            AppLogger.info("Création : " + filename);
            if (!Files.exists(path)) {
                Files.createFile(path);
            } else {
                throw new FileAccessException("Le fichier existe déjà.");
            }
        } catch (IOException e) {
            throw new FileAccessException("Erreur lors de la création.");
        }
    }

    @Override
    public void createRep(String dirname) {
        Path path = resolveSafePath(dirname);
        if (Files.exists(path)) {
            throw new InvalidCommandException("Le dossier existe déjà.");
        }
        try {
            Files.createDirectory(path);
            AppLogger.info("Répertoire créé : " + dirname);
        } catch (IOException e) {
            throw new FileAccessException("Échec de la création du dossier.");
        }
    }

    @Override
    public void update(String filename, String content) {
        try {
            Path path = resolveSafePath(filename);
            if (!Files.exists(path)) throw new InvalidCommandException("Fichier introuvable.");
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new FileAccessException("Erreur d'écriture.");
        }
    }

    @Override
    public void delete(String filename) {
        try {
            Path path = resolveSafePath(filename);
            if (!Files.deleteIfExists(path)) throw new InvalidCommandException("Fichier introuvable.");
            AppLogger.info("Supprimé : " + filename);
        } catch (IOException e) {
            throw new FileAccessException("Échec de la suppression.");
        }
    }


    //pour chiffrement
    @Override
    public void writeEncrypted(String filename, byte[] data, byte[] iv) {
        try {
            Path path = resolveSafePath(filename);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(iv);
            out.write(data);
            Files.write(path, out.toByteArray());
        } catch (IOException e) {
            throw new FileAccessException("Erreur écriture chiffrée.");
        }
    }

    @Override
    public byte[] readEncrypted(String filename) {
        try {
            Path path = resolveSafePath(filename);

            if (!Files.exists(path)) {
                throw new InvalidCommandException("Fichier introuvable : " + filename);
            }

            byte[] allBytes = Files.readAllBytes(path);

            if (allBytes.length < IV_SIZE) {
                throw new InvalidCommandException("Le fichier est corrompu ou trop court.");
            }

            byte[] encrypted = new byte[allBytes.length - IV_SIZE];
            System.arraycopy(allBytes, IV_SIZE, encrypted, 0, encrypted.length);

            return encrypted;

        } catch (IOException e) {
            throw new FileAccessException("Erreur technique lors de la lecture : " + e.getMessage());
        }
    }

    @Override
    public byte[] readIV(String filename) {
        try {
            Path path = resolveSafePath(filename);
            byte[] allBytes = Files.readAllBytes(path);
            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(allBytes, 0, iv, 0, IV_SIZE);
            return iv;
        } catch (IOException e) {
            throw new FileAccessException("Erreur lecture IV.");
        }
    }


    //pour hash
    @Override
    public void storeHash(String filename, String hash) {
        try {
            Path path = resolveSafePath(filename + ".hash");
            Files.writeString(path, hash);
        } catch (IOException e) {
            throw new FileAccessException("Erreur sauvegarde hash.");
        }
    }

    @Override
    public String loadHash(String filename) {
        try {
            Path path = resolveSafePath(filename + ".hash");
            return Files.exists(path) ? Files.readString(path) : null;
        } catch (IOException e) {
            throw new FileAccessException("Erreur lecture hash.");
        }
    }

    @Override
    public void deleteHash(String filename) {
        try {
            Path path = resolveSafePath(filename + ".hash");
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileAccessException("Erreur suppression hash.");
        }
    }
}