package org.cdu.backend.service;

import com.dropbox.core.v2.DbxClientV2;

public interface DropboxAuthService {
    DbxClientV2 getDbxClient();
}
