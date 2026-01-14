package com.esiea.sfm.infrastructure.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;

public class CryptoService {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private final SecretKey key;

    public CryptoService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            this.key = keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Erreur initialisation clé AES", e);
        }
    }

    public record EncryptedData(byte[] data, byte[] iv) {}

    public EncryptedData encrypt(byte[] plaintext) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));

            byte[] encrypted = cipher.doFinal(plaintext);
            return new EncryptedData(encrypted, iv);

        } catch (Exception e) {
            throw new RuntimeException("Erreur chiffrement", e);
        }
    }

    public byte[] decrypt(byte[] encryptedData, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));

            return cipher.doFinal(encryptedData);

        } catch (Exception e) {
            throw new RuntimeException("Erreur déchiffrement", e);
        }
    }
}
