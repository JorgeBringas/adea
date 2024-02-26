package com.adea.exam.management.service;

import com.adea.exam.management.config.MessagesProperties;
import com.adea.exam.management.config.UserDetailImp;
import com.adea.exam.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessagesProperties messagesProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(messagesProperties.getUserNotFound().formatted(username)));
        return new UserDetailImp(user.getUser().trim(), user.getPassword().trim());
    }
}
