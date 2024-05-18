package com.s1511.Ticketcine.application.security;
import com.s1511.Ticketcine.domain.repository.UserRepository;
import com.s1511.Ticketcine.domain.utils.RolesEnum;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            var user = User.builder()
                    .username(email)
                    .roles(RolesEnum.USER.name())
                    .password(found.get().getPassword())
                    .build();
            return user;
        }

        throw new EntityNotFoundException(email);
    }
}