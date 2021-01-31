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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service   // It has to be annotated with @Service.
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<UserEntity> oAuthenticatedUser = userRepository.findByLogin(username);

        if (!oAuthenticatedUser.isPresent())
            // If user not found. Throw this exception.
            throw new UsernameNotFoundException("Username: " + username + " not found");

        if (oAuthenticatedUser.get().getIsAuthenticated().equals(Boolean.FALSE))
            throw new UsernameNotFoundException("Username: " + username + " is unauthorized.");

        // Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
        // So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
        List<GrantedAuthority> grantedAuthorities = oAuthenticatedUser.get()
                .getRoles()
                .stream()
                .map(userRole -> (GrantedAuthority) new SimpleGrantedAuthority("" + userRole))
                .collect(Collectors.toList());

        // The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
        // And used by auth manager to verify and check user authentication.
        return new User(
                oAuthenticatedUser.get().getLogin(),
                encoder.encode(oAuthenticatedUser.get().getPassword()),
                grantedAuthorities
        );

    }
}