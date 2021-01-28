package be.fooda.backend.auth.service;

import be.fooda.backend.auth.dao.FoodaUserRepository;
import be.fooda.backend.auth.model.entity.FoodaUser;
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
    private FoodaUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<FoodaUser> oAuthenticatedUser = userRepository.findByLogin(username);

        if (oAuthenticatedUser.isPresent()) {
            final FoodaUser authenticatedUser = oAuthenticatedUser.get();
            if (authenticatedUser.getIsAuthenticated().equals(Boolean.TRUE)) {
                // Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
                // So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
                List<GrantedAuthority> grantedAuthorities = authenticatedUser.getRoles().stream()
                        .map(userRole -> (GrantedAuthority) new SimpleGrantedAuthority( ""+ userRole))
                        .collect(Collectors.toList());

                // The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
                // And used by auth manager to verify and check user authentication.
                return new User(authenticatedUser.getLogin(), encoder.encode(authenticatedUser.getPassword()), grantedAuthorities);
            } else {
                throw new UsernameNotFoundException("Username: " + username + " unauthorized.");
            }
        }
        // If user not found. Throw this exception.
        throw new UsernameNotFoundException("Username: " + username + " not found");
    }
}