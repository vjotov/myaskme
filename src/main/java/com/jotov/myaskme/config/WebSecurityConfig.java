package com.jotov.myaskme.config;

import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.repos.UserRepo;
import com.jotov.myaskme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()//.authorizeRequests()
                .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll()
                .anyRequest().authenticated()
                .and().logout().permitAll()
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

//    @Bean
//    public PrincipalExtractor principalExtractor (UserRepo userRepo){
//        return map -> {
//            String id = (String) map.get("sub");
//
//            //User user = userRepo.findByGoogleid(id).orElseGet(() -> {
//            User user = userRepo.findById(Long.valueOf(1)).orElseGet(() -> {
//                User newUser = new User();
//
//                newUser.setGoogleid(id);
////                newUser.setName((String) map.get("name"));
//                newUser.setEmail((String) map.get("email"));
////                newUser.setGender((String) map.get("gender"));
////                newUser.setLocale((String) map.get("locale"));
////                newUser.setUserpic((String) map.get("picture"));
//
//                return newUser;
//            });
////            user.setLastVisit(LocalDateTime.now());
//
//            return  userRepo.save(user);
//        };
//    }
}
