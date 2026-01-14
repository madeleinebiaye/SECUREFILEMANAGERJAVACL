package com.esiea.sfm.infrastructure.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;

/**
 * Service de chiffrement symétrique basé sur AES-GCM.
 */
public class CryptoService {

    private static final int KEY_SIZE = 128;
    private static final int IV_SIZE = 12;      // recommandé pour GCM
    private static final int TAG_SIZE = 128;    // taille du tag d'authentification

    private final SecretKey secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    public CryptoService() {
        this.secretKey = generateKey();
    }

    // Génération d'une clé AES
    private SecretKey generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(KEY_SIZE);
            return keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de la clé.");
        }
    }

    // Chiffrement des données
    public EncryptedData encrypt(byte[] data) {
        try {
            byte[] iv = new byte[IV_SIZE];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_SIZE, iv));

            byte[] encrypted = cipher.doFinal(data);
            return new EncryptedData(encrypted, iv);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement.");
        }
    }

    // Déchiffrement des données
    public byte[] decrypt(byte[] encryptedData, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_SIZE, iv));

            return cipher.doFinal(encryptedData);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement.");
        }
    }

    // Classe interne pour stocker données chiffrées + IV
    public record EncryptedData(byte[] data, byte[] iv) {}
}
