package org.cdu.backend.service.impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.exception.ImageSavingException;
import org.cdu.backend.service.DropboxAuthService;
import org.cdu.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DropboxImageServiceImpl implements ImageService {
    @Value("${dropbox.news.folder}")
    private String newsFolderPath;

    private final DropboxAuthService dropboxAuthService;

    @Override
    public String save(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }

        String dropboxImagePath = newsFolderPath + "/" + image.getOriginalFilename();
        DbxClientV2 client = dropboxAuthService.getDbxClient();
        SharedLinkMetadata dropboxImageLink;

        try (InputStream inputStream = image.getInputStream()) {
            FileMetadata metadata = client.files()
                    .uploadBuilder(dropboxImagePath)
                    .uploadAndFinish(inputStream);
        } catch (IOException | DbxException e) {
            throw new ImageSavingException("Can`t save image " + image.getName() + " to dropbox",
                    e);
        }

        try {
            dropboxImageLink = client.sharing().createSharedLinkWithSettings(dropboxImagePath);
        } catch (DbxException e) {
            throw new ImageSavingException("Can`t share link of image " + image.getName(), e);
        }

        return dropboxImageLink.getUrl();
    }
}
