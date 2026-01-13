package com.esiea.sfm.infrastructure.filesystem;

import com.esiea.sfm.domain.repository.FileRepository;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

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
        if (filename == null || filename.isBlank()) {
            return "Erreur : Nom de fichier manquant.";
        }

        File file = new File(filename);
        if (file.exists()) {
            try {
                // Lit le contenu réel du fichier
                return Files.readString(Path.of(filename));
            } catch (IOException e) {
                return "Erreur technique lors de la lecture : " + e.getMessage();
            }
        }
        return "Erreur : Le fichier '" + filename + "' n'existe pas.";
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
        // On regarde dans le répertoire courant (.)
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

    @Override
    public void update(String filename, String content) {
        File file = new File(filename);
        if (file.exists()) {
            try {
                java.nio.file.Files.writeString(java.nio.file.Path.of(filename), content);
                System.out.println("Succès : Le fichier a été mis à jour.");
            } catch (IOException e) {
                System.err.println("Erreur lors de la mise à jour : " + e.getMessage());
            }
        } else {
            System.out.println("Erreur : Le fichier n'existe pas. Utilisez 'create' d'abord.");
        }
    }
}