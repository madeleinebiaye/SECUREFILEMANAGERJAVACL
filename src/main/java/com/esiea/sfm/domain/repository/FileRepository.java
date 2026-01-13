package com.esiea.sfm.domain.repository;

import java.util.List;

public interface FileRepository {

    // Créer un fichier
    void create(String filename);

    // Lire un fichier
    String read(String filename);

    // Supprimer un fichier
    void delete(String filename);

    // Lister les fichiers
    void listFiles();

    // Editer un fichier
    void update(String filename, String content);
}