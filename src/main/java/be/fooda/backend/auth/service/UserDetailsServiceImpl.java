package be.fooda.backend.auth.service;

import be.fooda.backend.auth.dao.UserRepository;
import be.fooda.backend.auth.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service   // It has to be annotated with @Service.
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (userRepository.existsByLogin(username))
            // If user not found. Throw this exception.
            throw new UsernameNotFoundException("Username: " + username + " not found");

        final UserEntity authenticatedUser = userRepository.getOneByLogin(username);

        if (authenticatedUser.getIsAuthenticated().equals(Boolean.FALSE))
            throw new UsernameNotFoundException("Username: " + username + " is unauthorized.");

        // Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
        // So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
        Set<GrantedAuthority> grantedAuthorities = authenticatedUser
                .getRoles()
                .stream()
                .map(userRole -> (GrantedAuthority) new SimpleGrantedAuthority("" + userRole))
                .collect(Collectors.toSet());

        // The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
        // And used by auth manager to verify and check user authentication.
        return new User(
                authenticatedUser.getLogin(),
                passwordEncoder.encode(authenticatedUser.getPassword()),
                grantedAuthorities
        );

    }
}