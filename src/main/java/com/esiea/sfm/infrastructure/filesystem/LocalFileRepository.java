package com.esiea.sfm.infrastructure.filesystem;

import com.esiea.sfm.domain.repository.FileRepository;
import com.esiea.sfm.domain.exception.FileAccessException;
import com.esiea.sfm.domain.exception.InvalidCommandException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFileRepository implements FileRepository {

    @Override
    public void create(String filename) {
        try {
            File file = new File(filename);
            if (!file.createNewFile()) {
                throw new FileAccessException("Le fichier existe déjà.");
            }
        } catch (IOException e) {
            throw new FileAccessException("Impossible de créer le fichier.");
        }
    }

    @Override
    public String read(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            throw new InvalidCommandException("Le fichier est introuvable.");
        }
    }

    @Override
    public void delete(String filename) {
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
            Files.writeString(Path.of(filename), content);
        } catch (IOException e) {
            throw new FileAccessException("Erreur d’écriture.");
        }
    }

    @Override
    public void listFiles() {
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
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(iv);
            out.write(data);
            Files.write(Path.of(filename), out.toByteArray());
        } catch (IOException e) {
            throw new FileAccessException("Erreur écriture chiffrée.");
        }
    }

    @Override
    public byte[] readEncrypted(String filename) {
        try {
            byte[] allBytes = Files.readAllBytes(Path.of(filename));
            byte[] encrypted = new byte[allBytes.length - 12];
            System.arraycopy(allBytes, 12, encrypted, 0, encrypted.length);
            return encrypted;
        } catch (IOException e) {
            throw new FileAccessException("Erreur lecture chiffrée.");
        }
    }

    @Override
    public byte[] readIV(String filename) {
        try {
            byte[] allBytes = Files.readAllBytes(Path.of(filename));
            byte[] iv = new byte[12];
            System.arraycopy(allBytes, 0, iv, 0, 12);
            return iv;
        } catch (IOException e) {
            throw new FileAccessException("Erreur lecture IV.");
        }
    }

    @Override
    public void storeHash(String filename, String hash) {
        try {
            Files.writeString(Path.of(filename + ".hash"), hash);
        } catch (IOException e) {
            throw new FileAccessException("Erreur sauvegarde hash.");
        }
    }

    @Override
    public String loadHash(String filename) {
        try {
            Path path = Path.of(filename + ".hash");
            if (!Files.exists(path)) return null;
            return Files.readString(path);
        } catch (IOException e) {
            throw new FileAccessException("Erreur lecture hash.");
        }
    }

    @Override
    public void deleteHash(String filename) {
        try {
            Files.deleteIfExists(Path.of(filename + ".hash"));
        } catch (IOException e) {
            throw new FileAccessException("Erreur suppression hash.");
        }
    }
}
