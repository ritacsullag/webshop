package com.csullag.userservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class WebshopUserDto {

    private long id;
    private String username;
    private String password;
    private String email;
    private String facebookId;

    private Set<String> roles;
}
