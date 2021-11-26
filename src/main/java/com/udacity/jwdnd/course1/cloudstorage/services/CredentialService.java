package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.springframework.stereotype.Service;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;

@Service
public class CredentialService {

    private final CredentialMapper credentialsMapper;

    public int updateCredential(Credential credential) {
        return credentialsMapper.updateCredential(credential);
    }

    public int deleteCredentials(Credential credential) {
        return credentialsMapper.deleteCredential(credential);
    }

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialsMapper = credentialMapper;
    }

    public int addCredential(Credential credential) {
        return credentialsMapper.insertCredential(credential);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialsMapper.getCredential(credentialId);
    }

    public List<Credential> getAllCredentials() {
        return credentialsMapper.getAllCredentials();
    }
}
