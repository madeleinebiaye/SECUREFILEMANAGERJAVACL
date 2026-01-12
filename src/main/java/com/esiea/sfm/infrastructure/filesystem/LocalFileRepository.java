package com.esiea.sfm.infrastructure.filesystem;

import com.esiea.sfm.domain.repository.FileRepository;
<<<<<<< HEAD
import com.esiea.sfm.domain.exception.FileAccessException;

import java.io.File;
import java.io.IOException;

public class LocalFileRepository implements FileRepository {

    @Override
    public void create(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new FileAccessException("Nom de fichier invalide.");
        }

        try {
            File file = new File(filename);

            if (file.exists()) {
                throw new FileAccessException("Le fichier existe déjà : " + filename);
            }

            if (!file.createNewFile()) {
                throw new FileAccessException("Impossible de créer le fichier : " + filename);
            }

        } catch (IOException e) {
            throw new FileAccessException(
                    "Erreur lors de la création du fichier : " + filename
            );
        }
    }
}
=======
import java.io.File;

public class LocalFileRepository implements FileRepository {
    @Override
    public void create(String filename) {
        try {
            new File(filename).createNewFile();
            System.out.println("Fichier créé sur le disque.");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    @Override
    public String read(String filename) {
        return "Contenu du fichier " + filename;
    }

    @Override
    public void delete(String filename) {
        new File(filename).delete();
        System.out.println("Fichier supprimé.");
    }
}
>>>>>>> e76d202 (Itération 1 : fondations - création de fichier)
