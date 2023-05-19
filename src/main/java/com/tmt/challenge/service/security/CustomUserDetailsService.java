package com.tmt.challenge.service.security;

import com.tmt.challenge.dto.UserDTO;
import com.tmt.challenge.mapper.UserMapper;
import com.tmt.challenge.model.User;
import com.tmt.challenge.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final UserMapper userMapper;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles;

        User user = userRepository.findByUsername(username);
        if (user != null) {
            roles = List.of(new SimpleGrantedAuthority(user.getRole()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public User save(UserDTO user) {
        User newUser = userMapper.toUserEntity(user);
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));    // Password Encoding
        return userRepository.save(newUser);
    }

}
