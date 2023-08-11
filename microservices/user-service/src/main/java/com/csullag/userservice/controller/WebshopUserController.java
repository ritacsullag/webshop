package com.csullag.userservice.controller;


import com.csullag.userservice.dto.LoginDto;
import com.csullag.userservice.dto.WebshopUserDto;
import com.csullag.userservice.mapper.WebshopUserMapper;
import com.csullag.userservice.model.WebshopUser;
import com.csullag.userservice.service.WebshopUserService;
import com.nimbusds.oauth2.sdk.util.singleuse.AlreadyUsedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webshopusers")
public class WebshopUserController {

    private final WebshopUserMapper webshopUserMapper;

    private final WebshopUserService webshopUserService;

    @GetMapping("/{id}")
    public WebshopUserDto findById(@PathVariable("id") long id) {
        return webshopUserMapper.webshopUserToDto(
                webshopUserService.findById(id));
    }

    @PostMapping("/register")
    public WebshopUserDto createWebshopUser(@Valid @RequestBody WebshopUserDto userDto) throws AlreadyUsedException {
        WebshopUser webshopUser = webshopUserService.createWebshopUser(webshopUserMapper.dtoToWebshopUser(userDto));
        WebshopUserDto webshopUserDto = webshopUserMapper.webshopUserToDto(webshopUser);
        return webshopUserDto;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return webshopUserService.login(loginDto);
        }
}
