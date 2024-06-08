package com.s1511.ticketcine.application.implementations;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.s1511.ticketcine.application.dto.login.RequestLogin;
import com.s1511.ticketcine.application.dto.login.ResponseLogin;
import com.s1511.ticketcine.application.security.JwtService;
import com.s1511.ticketcine.domain.entities.User;
import com.s1511.ticketcine.domain.repository.UserRepository;
import com.s1511.ticketcine.domain.services.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public ResponseLogin login(RequestLogin data) {

        String role = "";
        String id = "";
        String token = "";
        String firstName = "";
        String lastName = "";
        Double moviePoints = 0.0;

        var user = userRepository.findByEmail(data.email());
        if (user.isPresent()) {
            role = user.get().getAuthorities().toString();
            id = user.get().getId();
            token = jwtService.getToken(user.get());
            firstName = user.get().getFirstName();
            lastName = user.get().getLastName();
            moviePoints = user.get().getMoviePoints();
        } else throw new EntityNotFoundException("No se puede encontrar el usuario con el email " + data.email());


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.email(),
                        data.password()));

        ResponseLogin responseLogin = new ResponseLogin(token, role, id, firstName, lastName, moviePoints);

        return responseLogin;
    }

}
