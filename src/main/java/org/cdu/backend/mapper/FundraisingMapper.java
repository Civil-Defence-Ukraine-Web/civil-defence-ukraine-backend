package org.cdu.backend.mapper;

import org.cdu.backend.config.MapperConfig;
import org.cdu.backend.dto.fundraising.FundraisingResponseDto;
import org.cdu.backend.model.Fundraising;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface FundraisingMapper {
    FundraisingResponseDto toDto(Fundraising fundraising);
}
