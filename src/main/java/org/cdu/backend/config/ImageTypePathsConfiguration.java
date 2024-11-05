package org.cdu.backend.config;

import jakarta.annotation.PostConstruct;
import org.cdu.backend.service.impl.DropboxImageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageTypePathsConfiguration {
    @Value("${dropbox.news.folder}")
    private String newsFolderPath;
    @Value("${dropbox.team.member.folder}")
    private String teamMemberFolderPath;
    @Value("${dropbox.fundraising.folder}")
    private String fundraisingFolderPath;

    @PostConstruct
    public void init() {
        DropboxImageServiceImpl.ImageType.NEWS_IMAGE.setPath(newsFolderPath);
        DropboxImageServiceImpl.ImageType.TEAM_MEMBER_IMAGE.setPath(teamMemberFolderPath);
        DropboxImageServiceImpl.ImageType.FUNDRAISING_IMAGE.setPath(fundraisingFolderPath);
    }
}
