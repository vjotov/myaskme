package com.jotov.myaskme.service;

import com.jotov.myaskme.domain.Role;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.dto.CreateUserDto;
import com.jotov.myaskme.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSender mailSender;

    @Value("${hostname}")
    private String hostname;

    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepo.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    public boolean addUser(CreateUserDto userDto) {
        if (userRepo.findByUsername(userDto.getUsername()) != null) {
            return false;
        }
        User user = new User();
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        userRepo.save(user);

        sendMassage(user);
        return true;
    }

    private void sendMassage(User user) {
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s!\n"+
                            "Welcome to MyASKme. Please, visit next link: http://%s/activate/%s",
                    user.getUsername(),
                    hostname,
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if(user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);
        return true;
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email!= null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if(isEmailChanged) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if(!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        userRepo.save(user);
        if(isEmailChanged) {
            sendMassage(user);
        }
    }
}
