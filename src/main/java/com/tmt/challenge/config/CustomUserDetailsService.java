package com.tmt.challenge.config;

import java.util.Arrays;
import java.util.List;

import com.tmt.challenge.dto.UserDTO;
import com.tmt.challenge.model.DAOUser;
import com.tmt.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userDAO;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles=null;

        DAOUser user = userDAO.findByUsername(username);
        if (user != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(), user.getPassword(), roles);
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

    public DAOUser save(UserDTO user) {
        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));    // Password Encoding
        newUser.setRole(user.getRole());
        return userDAO.save(newUser);
    }

}
