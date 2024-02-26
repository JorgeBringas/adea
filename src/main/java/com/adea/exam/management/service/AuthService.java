package com.adea.exam.management.service;

import com.adea.exam.management.config.MessagesProperties;
import com.adea.exam.management.config.UserDetailImp;
import com.adea.exam.management.controller.beans.Login;
import com.adea.exam.management.controller.beans.LoginResponse;
import com.adea.exam.management.entity.UserEntity;
import com.adea.exam.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MessagesProperties messagesProperties;


    public LoginResponse login(Login login) throws UsernameNotFoundException {
        var response = new LoginResponse();
        var userEntity = userRepository.findById(login.user())
                .orElseThrow(() -> new UsernameNotFoundException(messagesProperties.getUserNotFound().formatted(login.user())));
        var user = login.user().trim();
        var rawPassword = login.password().trim();
        var encodePassword = userEntity.getPassword().trim();

        if (passwordEncoder.matches(rawPassword, encodePassword) && userEntity.getStatus()=='A' && isValidDate(userEntity)) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, rawPassword));
            var userDetails = new UserDetailImp(user, encodePassword);
            var token =  jwtService.getToken(userDetails);
            response.setToken(token);
            return response;
        } else {
            log.error("user %s - wrong password".formatted(login.user()));
            throw new UsernameNotFoundException(messagesProperties.getUserNotFound().formatted(login.user()));
        }

    }

    private boolean isValidDate(UserEntity userEntity){
        if (userEntity.getValidityDate()!=null) {
            return Calendar.getInstance().getTime().before(userEntity.getValidityDate());
        }else{
            return true;
        }
    }
}
