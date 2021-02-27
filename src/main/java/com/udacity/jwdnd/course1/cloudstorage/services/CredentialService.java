package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.DecryptedCredential;
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
    public List<DecryptedCredential> getAllUserCredentials(Integer userID) {
        List<DecryptedCredential> decryptedCredentials = new ArrayList<>();

        for(Credential credential : this.credentialMapper.getCredentialsByUsername(userID)){
            DecryptedCredential decryptedCredential = new DecryptedCredential(
                    credential.getCredentialid(),
                    credential.getUrl(),
                    credential.getUsername(),
                    credential.getKey(),
                    credential.getPassword(),
                    encryptionService.decryptValue(credential.getPassword(), credential.getKey()),
                    credential.getUserid()
            );

            decryptedCredentials.add(decryptedCredential);
        }

        return decryptedCredentials;
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
