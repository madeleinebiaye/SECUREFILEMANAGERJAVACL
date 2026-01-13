package com.esiea.sfm.application;

import com.esiea.sfm.domain.repository.FileRepository;
import com.esiea.sfm.infrastructure.security.HashService;

public class FileService {
    private final FileRepository repository;
    private final HashService hashService;


    public FileService(FileRepository repository, HashService hashService) {
        this.repository = repository;
        this.hashService = hashService;
    }
/// /////////////////////////////////////////
public String readFile(String filename) {
    String content = repository.read(filename);
    String currentHash = hashService.calculateHash(content);
    String storedHash = repository.loadHash(filename);

    StringBuilder output = new StringBuilder();
    output.append("--- Contenu du fichier ---\n").append(content);

    if (storedHash != null) {
        if (storedHash.equals(currentHash)) {
            output.append("\n\nINTÉGRITÉ VÉRIFIÉE : Le fichier est sain.");
        } else {
            output.append("\n\nALERTE SÉCURITÉ : Le fichier a été modifié à l'extérieur de l'application !");
            output.append("\nHash attendu : ").append(storedHash);
            output.append("\nHash actuel  : ").append(currentHash);
        }
    } else {
        output.append("\n\nATTENTION : Aucune empreinte disponible pour ce fichier.");
    }

    return output.toString();
}

    public void updateFile(String filename, String content) {
        repository.update(filename, content); // On sauvegarde le texte
        String newHash = hashService.calculateHash(content); // On calcule la nouvelle empreinte
        repository.storeHash(filename, newHash); // On la sauvegarde
    }
/// /////////////////////////////////////////

    public void createFile(String filename) {
        repository.create(filename);
    }

    public void deleteFile(String filename) {
        repository.delete(filename);
    }

    public void listFiles() {
        repository.listFiles();
    }

}