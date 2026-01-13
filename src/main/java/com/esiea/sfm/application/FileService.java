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
        // On calcule le hash du contenu lu
        String hash = hashService.calculateHash(content);

        return "--- Contenu du fichier ---\n" + content +
                "\n--- Empreinte (SHA-256) ---\n" + hash;
    }
/// /////////////////////////////////////////

    public void createFile(String filename) {
        repository.create(filename);
    }

    public void updateFile(String filename, String content) {
        repository.update(filename, content);
    }

    public void deleteFile(String filename) {
        repository.delete(filename);
    }

    public void listFiles() {
        repository.listFiles();
    }

}