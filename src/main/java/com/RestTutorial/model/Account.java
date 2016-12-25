package com.RestTutorial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by atretjak on 25.12.2016.
 */

@SuppressWarnings("DefaultFileTemplate")
@Entity
@NoArgsConstructor
@Getter

public class Account {

    @OneToMany(mappedBy = "account")
    private Set<Bookmark> bookmarks = new HashSet<>();

    @Id @GeneratedValue
    private Long id;


    private String username;
    @JsonIgnore
    private String password;

    public Account(String username, @SuppressWarnings("SameParameterValue") String password) {
        this.username = username;
        this.password = password;
    }
}
