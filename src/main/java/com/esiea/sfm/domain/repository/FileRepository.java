package com.esiea.sfm.domain.repository;

public interface FileRepository {

    //pour cud
    void create(String filename);
    void createRep(String filename);
    void update(String filename, String content);
    void delete(String filename);

    // pour chemins
    void changeDirectory(String dirname);
    String getCurrentPathName();
    void listFiles();

    //pour hash
    void storeHash(String filename, String hash);
    String loadHash(String filename);
    void deleteHash(String filename);

    //pour chiffrement
    void writeEncrypted(String filename, byte[] data, byte[] iv);
    byte[] readEncrypted(String filename);
    byte[] readIV(String filename);
}
