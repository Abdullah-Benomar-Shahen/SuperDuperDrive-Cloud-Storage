package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }


    /**
     * Gets credentials from Database by username..
     *
     * @param userID the user id
     * @return the all user credentials
     */
    public List<Credential> getAllUserCredentials(Integer userID) {

        List<Credential> savedUserCredentials = this.credentialMapper.getCredentialsByUsername(userID); // This is what we get from the DB
        List<Credential> userCredentials = new ArrayList<>(); // This is what we return

        for (Credential singleCredential : savedUserCredentials){
            String encodedKey = singleCredential.getKey();
            String encryptedPassword = singleCredential.getPassword();
            String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

            // Set Password of current credential to plain text Password
            singleCredential.setPassword(decryptedPassword);

            userCredentials.add(singleCredential);
        }

        return userCredentials;
    }


    /**
     * Insert or update credential.
     *
     * @param credential the credential
     * @param userID     the user id
     * @return the boolean
     */
    public Boolean insertOrUpdateCredential(Credential credential, Integer userID) {

        String password = credential.getPassword();

        // Hashing Password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        Integer credentialId = credential.getCredentialid();

        // Inserting or Updating a Credential
        if (credentialId == null && userID != null) {
            credential.setKey(encodedKey);
            credential.setPassword(encryptedPassword);
            credential.setUserid(userID);
            this.credentialMapper.insert(credential);
        } else {
            this.credentialMapper.update(credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credentialId);
        }

        return true;
    }

    /**
     * Delete credential from Database.
     *
     * @param credentialId the credential id
     * @return the boolean
     */
    public Boolean deleteCredential(Integer credentialId) {

        this.credentialMapper.delete(credentialId);

        return true;
    }
}
