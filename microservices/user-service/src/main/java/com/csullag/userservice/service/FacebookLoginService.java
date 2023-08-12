package com.csullag.userservice.service;

import com.csullag.userservice.model.WebshopUser;
import com.csullag.userservice.repository.WebshopUserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {
    private static final String GRAPH_API_BASE_URL = "https://graph.facebook.com/v13.0";
    private static final String CUSTOMER_ROLE = "customer";

    private final WebshopUserRepository userRepository;

    @Getter
    @Setter
    public static class FacebookData {
        private String email;
        private long id;
    }

    @Transactional
    public UserDetails getUserDetailsForToken(String facebookToken) {
        FacebookData facebookData = getEmailOfFacebookUser(facebookToken);
        WebshopUser webshopUser = findOrCreateUser(facebookData);
        return WebshopUserDetailsService.createUserDetails(webshopUser);

    }

    private FacebookData getEmailOfFacebookUser(String facebookToken) {
        //RestApi hivas a fb fele (webflux)
        return WebClient
                .create(GRAPH_API_BASE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/me")
                        .queryParam("fields", "email,name")
                        .build()
                )
                .headers(headers -> headers.setBearerAuth(facebookToken))
                .retrieve()
                .bodyToMono(FacebookData.class)
                .block();
    }


    private WebshopUser findOrCreateUser(FacebookData facebookData) {
        String facebookId = String.valueOf(facebookData.getId());
        Optional<WebshopUser> optionalExistingUser = userRepository.findByFacebookId(facebookId);
        if (optionalExistingUser.isEmpty()) {
            WebshopUser newUser = WebshopUser.builder()
                    .facebookId(facebookId)
                    .username(facebookData.getEmail())
                    .password("dummy")
                    .roles(Set.of(CUSTOMER_ROLE))
                    .email(facebookData.getEmail())
                    .build();
            return userRepository.save(newUser);
        } else {
            return optionalExistingUser.get();
        }
    }

}
