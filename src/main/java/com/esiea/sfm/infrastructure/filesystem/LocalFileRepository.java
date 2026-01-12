package com.esiea.sfm.infrastructure.filesystem;

import com.esiea.sfm.domain.repository.FileRepository;
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
