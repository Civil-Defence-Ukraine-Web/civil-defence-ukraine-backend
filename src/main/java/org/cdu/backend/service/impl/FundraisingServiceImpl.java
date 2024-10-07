package org.cdu.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.fundraising.FundraisingResponseDto;
import org.cdu.backend.mapper.FundraisingMapper;
import org.cdu.backend.model.Fundraising;
import org.cdu.backend.repository.fundraising.FundraisingRepository;
import org.cdu.backend.service.FundraisingService;
import org.cdu.backend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FundraisingServiceImpl implements FundraisingService {
    private final FundraisingRepository fundraisingRepository;
    private final FundraisingMapper fundraisingMapper;
    private final ImageService imageService;

    @Override
    public FundraisingResponseDto getActual() {
        Fundraising actualFundraising = fundraisingRepository.findAll().getFirst();
        return fundraisingMapper.toDto(actualFundraising);
    }

    @Override
    public FundraisingResponseDto replaceActual(MultipartFile image) {
        fundraisingRepository.deleteAll();
        String imageUrl =
                imageService.save(image, DropboxImageServiceImpl.ImageType.FUNDRAISING_IMAGE);
        Fundraising fundraising = new Fundraising();
        fundraising.setImage(imageUrl);
        fundraisingRepository.save(fundraising);
        return fundraisingMapper.toDto(fundraising);
    }
}
