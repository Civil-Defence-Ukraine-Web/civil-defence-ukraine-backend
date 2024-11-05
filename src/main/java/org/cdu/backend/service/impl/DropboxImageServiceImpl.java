package org.cdu.backend.service.impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.exception.ImageSavingException;
import org.cdu.backend.service.DropboxAuthService;
import org.cdu.backend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DropboxImageServiceImpl implements ImageService {
    private static final String DEFAULT_VIEW_FORMAT = "&dl=0";
    private static final String SOURCE_VIEW_FORMAT = "&raw=1";

    private final DropboxAuthService dropboxAuthService;

    @Override
    public String save(MultipartFile image, ImageType type) {
        if (image.isEmpty()) {
            return null;
        }

        String dropboxImagePath = "/" + type.getPath() + "/" + image.getOriginalFilename();

        DbxClientV2 client = dropboxAuthService.getDbxClient();
        SharedLinkMetadata dropboxImageLink;

        try (InputStream inputStream = image.getInputStream()) {
            client.files()
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
        return dropboxImageLink.getUrl().replace(DEFAULT_VIEW_FORMAT, SOURCE_VIEW_FORMAT);
    }

    @Getter
    public enum ImageType {
        NEWS_IMAGE,
        TEAM_MEMBER_IMAGE,
        FUNDRAISING_IMAGE;

        private String path;

        public void setPath(String path) {
            this.path = path;
        }
    }
}
