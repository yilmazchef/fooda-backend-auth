package be.fooda.backend.auth.security;

import be.fooda.backend.auth.dao.UserRepository;
import be.fooda.backend.auth.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userRepository.existsByLogin(username))
            throw new UsernameNotFoundException("Username not found");

        UserEntity userEntity = userRepository.getOneByLogin(username);

        if (userEntity.getIsAuthenticated().equals(Boolean.FALSE))
            throw new UsernameNotFoundException("User not authenticated");

        return new User(
                userEntity.getLogin(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.name())).collect(Collectors.toSet())
        );
    }
}
