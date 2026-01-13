package com.esiea.sfm.domain.repository;

import java.util.List;

/**
 * Interface définissant les opérations de stockage des fichiers.
 * Cette abstraction permet de respecter l'inversion de dépendance.
 */
public interface FileRepository {

    // Créer un fichier vide
    void create(String filename);

    // Lire le contenu d'un fichier
    String read(String filename);

    // Supprimer un fichier
    void delete(String filename);

    // Lister les fichiers présents (Nouvelle commande)
    void listFiles();

    void update(String filename, String content);
}