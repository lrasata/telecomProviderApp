package com.lrasata.telecomproviderapp.service.mapper;

import com.lrasata.telecomproviderapp.domain.MobilePlan;
import com.lrasata.telecomproviderapp.service.dto.MobilePlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MobilePlan} and its DTO {@link MobilePlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface MobilePlanMapper extends EntityMapper<MobilePlanDTO, MobilePlan> {}
