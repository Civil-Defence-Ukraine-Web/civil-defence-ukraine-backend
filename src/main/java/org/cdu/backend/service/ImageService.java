package org.cdu.backend.service;

import org.cdu.backend.service.impl.DropboxImageServiceImpl.ImageType;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String save(MultipartFile image, ImageType type);
}
