package com.RestTutorial.controllers;

import com.RestTutorial.dao.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by atretjak on 27.12.2016.
 */
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return (username -> accountRepository
                .findByUsername(username)
                .map(account -> new User(account.getUsername(), account.getPassword(),
                        true, true, true, true,
                        AuthorityUtils.createAuthorityList("USER", "write")))
                .orElseThrow(() -> new UsernameNotFoundException("could not find the user '" + username + "'")));
    }
}
