package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.springframework.stereotype.Service;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CredentialService {

    private final CredentialMapper credentialsMapper;

    @Autowired
    private EncryptionService encriptionService;

    public int updateCredential(Credential credential) {
        credential.setKey(encriptionService.generateKey());
        credential.setPassword(encriptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credentialsMapper.updateCredential(credential);
    }

    public int deleteCredentials(Credential credential) {
        return credentialsMapper.deleteCredential(credential);
    }

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialsMapper = credentialMapper;
    }

    public int addCredential(Credential credential) {
        credential.setKey(encriptionService.generateKey());
        credential.setPassword(encriptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credentialsMapper.insertCredential(credential);
    }

    public Credential getCredential(Integer credentialId) {
        Credential credential = credentialsMapper.getCredential(credentialId);
        credential.setPassword(encriptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public String getCredentialPasswordPlain(Integer credentialId) {
        Credential credential = credentialsMapper.getCredential(credentialId);
        return encriptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

    public List<Credential> getAllCredentialsByUser(Integer userId) {
        return credentialsMapper.getAllCredentialsByUser(userId);
    }

    public List<Credential> getAllCredentials() {
        return credentialsMapper.getAllCredentials();
    }
}
