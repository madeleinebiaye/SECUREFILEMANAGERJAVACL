package com.esiea.sfm.application;

import com.esiea.sfm.domain.repository.FileRepository;
import com.esiea.sfm.infrastructure.security.HashService;
import com.esiea.sfm.infrastructure.security.CryptoService;

public class FileService {

    private final FileRepository repository;
    private final HashService hashService;
    private final CryptoService cryptoService;

    public FileService(FileRepository repository, HashService hashService) {
        this.repository = repository;
        this.hashService = hashService;
        this.cryptoService = new CryptoService();
    }

    public void createFile(String filename) {
        createFile(filename, "");
    }

    public void createFile(String filename, String content) {
        CryptoService.EncryptedData encrypted =cryptoService.encrypt(content.getBytes());

        repository.writeEncrypted(filename, encrypted.data(), encrypted.iv());

        String hash = hashService.calculateHash(content);
        repository.storeHash(filename, hash);
    }

    public void createRep(String dirname) {
        repository.createRep(dirname);
    }

    public String readFile(String filename) {
        byte[] encryptedData = repository.readEncrypted(filename);
        byte[] iv = repository.readIV(filename);

        byte[] decrypted = cryptoService.decrypt(encryptedData, iv);
        String content = new String(decrypted);

        String currentHash = hashService.calculateHash(content);
        String storedHash = repository.loadHash(filename);

        if (storedHash != null && !storedHash.equals(currentHash)) {
            return " Le fichier a été modifié hors du gestionnaire !!";
        }

        return content;
    }

    public void changeDirectory(String dirname) {
        repository.changeDirectory(dirname);
    }

    public String getCurrentPath() {
        return repository.getCurrentPathName();
    }

    public void updateFile(String filename, String content) {
        createFile(filename, content);
    }

    public void deleteFile(String filename) {
        repository.delete(filename);
        repository.deleteHash(filename);
    }

    public void listFiles() {
        repository.listFiles();
    }
}

