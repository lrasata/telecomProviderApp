package com.lrasata.telecomproviderapp.service.mapper;

import com.lrasata.telecomproviderapp.domain.ApplicationUser;
import com.lrasata.telecomproviderapp.domain.MobilePlan;
import com.lrasata.telecomproviderapp.domain.User;
import com.lrasata.telecomproviderapp.service.dto.ApplicationUserDTO;
import com.lrasata.telecomproviderapp.service.dto.MobilePlanDTO;
import com.lrasata.telecomproviderapp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationUserMapper extends EntityMapper<ApplicationUserDTO, ApplicationUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userLogin")
    @Mapping(target = "chosenMobilePlan", source = "chosenMobilePlan", qualifiedByName = "mobilePlanId")
    ApplicationUserDTO toDto(ApplicationUser s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("mobilePlanId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MobilePlanDTO toDtoMobilePlanId(MobilePlan mobilePlan);
}
