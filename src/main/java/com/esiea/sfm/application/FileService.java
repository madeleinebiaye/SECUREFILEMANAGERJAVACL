package com.esiea.sfm.application;

import com.esiea.sfm.domain.repository.FileRepository;

public class FileService {
    private final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public void createFile(String filename) {
        repository.create(filename);
    }

    public String readFile(String filename) {
        return repository.read(filename);
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