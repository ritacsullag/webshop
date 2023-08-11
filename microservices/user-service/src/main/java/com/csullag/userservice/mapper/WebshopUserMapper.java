package com.csullag.userservice.mapper;

import com.csullag.userservice.dto.WebshopUserDto;
import com.csullag.userservice.model.WebshopUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WebshopUserMapper {


    WebshopUser dtoToWebshopUser(WebshopUserDto userDto);

    @Mapping(target = "password", ignore = true)
    WebshopUserDto webshopUserToDto(WebshopUser webshopUser);
}
