package com.tmt.challenge.service.security;

import com.tmt.challenge.dto.UserDTO;
import com.tmt.challenge.mapper.UserMapper;
import com.tmt.challenge.model.User;
import com.tmt.challenge.repository.UserRepository;
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
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder bcryptEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles;

        User user = userRepository.findByUsername(username);
        if (user != null) {
            roles = List.of(new SimpleGrantedAuthority(user.getRole()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
        }
//        if(username.equals("admin"))
//        {
//            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            return new User("admin", "$2y$12$I0Di/vfUL6nqwVbrvItFVOXA1L9OW9kLwe.1qDPhFzIJBpWl76PAe",
//                    roles);
//        }
//        else if(username.equals("user"))
//        {
//            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//            return new User("user", "$2y$12$VfZTUu/Yl5v7dAmfuxWU8uRfBKExHBWT1Iqi.s33727NoxHrbZ/h2",
//                    roles);
//        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public User save(UserDTO user) {
        User newUser = userMapper.toUserEntity(user);
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));    // Password Encoding
        return userRepository.save(newUser);
    }

}
