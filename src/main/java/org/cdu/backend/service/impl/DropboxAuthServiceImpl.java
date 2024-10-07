package org.cdu.backend.service.impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.oauth.DbxRefreshResult;
import com.dropbox.core.v2.DbxClientV2;
import jakarta.annotation.PostConstruct;
import org.cdu.backend.exception.ExternalAuthenticationException;
import org.cdu.backend.service.DropboxAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DropboxAuthServiceImpl implements DropboxAuthService {
    @Value("${dropbox.app.name}")
    private String appName;
    @Value("${dropbox.app.key}")
    private String appKey;
    @Value("${dropbox.app.secret}")
    private String appSecret;
    @Value("${dropbox.refresh.token}")
    private String refreshToken;

    private DbxRequestConfig requestConfig;

    @PostConstruct
    public void init() {
        requestConfig = DbxRequestConfig.newBuilder(appName).build();
    }

    public DbxClientV2 getDbxClient() {
        String accessToken = getAccessToken();
        return new DbxClientV2(requestConfig, accessToken);
    }

    private String getAccessToken() {
        DbxCredential credential = new DbxCredential("", -1L,
                refreshToken, appKey, appSecret);
        DbxRefreshResult refreshResult = null;
        try {
            refreshResult = credential.refresh(requestConfig);
        } catch (DbxException e) {
            throw new ExternalAuthenticationException("Can`t authenticate and refresh access "
                    + "token for dropbox", e);
        }
        return refreshResult.getAccessToken();
    }
}
