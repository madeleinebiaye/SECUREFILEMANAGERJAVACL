package com.esiea.sfm.infrastructure.filesystem;

import com.esiea.sfm.domain.repository.FileRepository;
import java.io.File;
import java.io.IOException;

public class LocalFileRepository implements FileRepository {

    @Override
    public void create(String filename) {
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("Succès : Fichier créé sur le disque.");
            } else {
                System.out.println("Erreur : Le fichier existe déjà.");
            }
        } catch (IOException e) {
            System.err.println("Erreur technique lors de la création : " + e.getMessage());
        }
    }

    @Override
    public String read(String filename) {
        // Pour l'instant, on simule la lecture ou on retourne un message
        File file = new File(filename);
        if (file.exists()) {
            return "Lecture du fichier " + filename + " (Contenu à implémenter)";
        }
        return "Erreur : Le fichier n'existe pas.";
    }

    @Override
    public void delete(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Succès : Fichier supprimé du disque.");
            } else {
                System.out.println("Erreur : Impossible de supprimer le fichier.");
            }
        } else {
            System.out.println("Erreur : Le fichier n'existe pas.");
        }
    }

    @Override
    public void listFiles() {
        // On regarde dans le répertoire courant (représenté par ".")
        File currentDir = new File(".");
        File[] filesList = currentDir.listFiles();

        System.out.println("--- Contenu du répertoire ---");
        if (filesList != null && filesList.length > 0) {
            for (File file : filesList) {
                String type = file.isDirectory() ? "[DOSSIER]" : "[FICHIER]";
                System.out.println(type + " " + file.getName());
            }
        } else {
            System.out.println("Le répertoire est vide.");
        }
        System.out.println("-----------------------------");
    }
}