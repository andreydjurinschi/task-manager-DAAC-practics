package com.academy.taskmanager.task_manager.config;

import com.academy.taskmanager.task_manager.userservice.entities.User;
import com.academy.taskmanager.task_manager.userservice.entities.UserRole;
import com.academy.taskmanager.task_manager.userservice.repository.UserDaoImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.time.LocalDateTime;
import java.util.*;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
    private final UserDaoImpl userRepository;

    public KeycloakJwtAuthenticationConverter(UserDaoImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>(defaultConverter.convert(jwt));
        String username = jwt.getClaimAsString("preferred_username");
        createOrUpdateFromKeycloak(jwt);
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        optionalUser.ifPresent(user ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );

        return new JwtAuthenticationToken(jwt, authorities);
    }

    public void createOrUpdateFromKeycloak(Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");
        String fullName = jwt.getClaimAsString("name");

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        String role = null;
        /*
              "resource_access": {
            "my-client": {
                "roles": ["ADMIN", что-то еще]
            },
            "account": {
                "roles": ["manage-account", "view-profile"]
            }
        },
             "scope": "openid profile email"
    }
        */

        if (resourceAccess != null && resourceAccess.containsKey("my-client")) {
            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("my-client");
            /*
            "my-client": {
                "roles": ["ADMIN", что-то еще]
            },*/

            List<String> roles = (List<String>) clientAccess.get("roles");
            /*
                "roles": ["ADMIN", что-то еще]
            */
            if (roles != null && !roles.isEmpty()) {
                role = roles.getFirst();
            }
        }

        if (role == null) {
            role = "TEACHER";
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFullName(fullName);
            user.setRole(UserRole.valueOf(role.toUpperCase()));
            user.setCreatedAt(LocalDateTime.now());
            userRepository.createOrUpdate(user);
        } else {
            boolean updated = false;
            if (!email.equals(user.getEmail())) {
                user.setEmail(email);
                updated = true;
            }
            if (!fullName.equals(user.getFullName())) {
                user.setFullName(fullName);
                updated = true;
            }
            if (updated) {
                userRepository.createOrUpdate(user);
            }
        }
    }


}
