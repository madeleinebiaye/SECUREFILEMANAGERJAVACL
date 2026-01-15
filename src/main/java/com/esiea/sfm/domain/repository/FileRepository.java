package com.esiea.sfm.domain.repository;

public interface FileRepository {

    // Création / suppression
    void create(String filename);
    void createRep(String filename);
    void delete(String filename);

    // Lecture / écriture en clair (utilisées AVANT chiffrement)
    String read(String filename);
    void update(String filename, String content);

    // Liste des fichiers
    void listFiles();

    // Gestion des empreintes (intégrité)
    void storeHash(String filename, String hash);
    String loadHash(String filename);
    void deleteHash(String filename);

    // Gestion du chiffrement (octets uniquement)
    void writeEncrypted(String filename, byte[] data, byte[] iv);
    byte[] readEncrypted(String filename);
    byte[] readIV(String filename);
}
