package com.csullag.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebshopUser {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private long id;
    @EqualsAndHashCode.Include()
    private String username;
    private String password;
    private String email;
    private String facebookId;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;}
