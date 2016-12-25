package com.RestTutorial.dao;

import com.RestTutorial.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by atretjak on 25.12.2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
