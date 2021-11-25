package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;

    public int updateCredentials(Credentials credentials) {
        return credentialsMapper.updateCredentials(credentials);
    }

    public int deleteCredentials(Credentials credentials) {
        return credentialsMapper.deleteCredentials(credentials);
    }

    public CredentialsService(CredentialsMapper credentialsMapper) {
        this.credentialsMapper = credentialsMapper;
    }

    public int addCredentials(Credentials credentials) {
        return credentialsMapper.insertCredentials(credentials);
    }

    public Credentials getCredentials(Integer credentialsId) {
        return credentialsMapper.getCredentials(credentialsId);
    }

    public List<Credentials> getAllCredentials() {
        return credentialsMapper.getAllCredentials();
    }
}
