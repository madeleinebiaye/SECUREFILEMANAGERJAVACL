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
        // Chiffrement
        CryptoService.EncryptedData encrypted =
                cryptoService.encrypt(content.getBytes());

        // Écriture chiffrée
        repository.writeEncrypted(filename, encrypted.data(), encrypted.iv());

        // Stockage de l’empreinte
        String hash = hashService.calculateHash(content);
        repository.storeHash(filename, hash);
    }

    public String readFile(String filename) {
        // Lecture brute
        byte[] encryptedData = repository.readEncrypted(filename);
        byte[] iv = repository.readIV(filename);

        // Déchiffrement
        byte[] decrypted = cryptoService.decrypt(encryptedData, iv);
        String content = new String(decrypted);

        // Vérification d’intégrité
        String currentHash = hashService.calculateHash(content);
        String storedHash = repository.loadHash(filename);

        if (storedHash != null && !storedHash.equals(currentHash)) {
            return " ALERTE SÉCURITÉ : Le fichier a été modifié hors de l’application.";
        }

        return content;
    }

    public void updateFile(String filename, String content) {
        // Même logique que create
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

