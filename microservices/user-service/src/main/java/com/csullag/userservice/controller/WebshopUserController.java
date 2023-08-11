package com.csullag.userservice.controller;


import com.csullag.userservice.dto.LoginDto;
import com.csullag.userservice.dto.WebshopUserDto;
import com.csullag.userservice.mapper.WebshopUserMapper;
import com.csullag.userservice.model.WebshopUser;
import com.csullag.userservice.service.WebshopUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createWebshopUser(@Valid @RequestBody WebshopUserDto userDto) {
        try {
            WebshopUser webshopUser = webshopUserService.createWebshopUser(webshopUserMapper.dtoToWebshopUser(userDto));
            WebshopUserDto webshopUserDto = webshopUserMapper.webshopUserToDto(webshopUser);
            return ResponseEntity.ok(webshopUserDto);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return webshopUserService.login(loginDto);
        }
}
