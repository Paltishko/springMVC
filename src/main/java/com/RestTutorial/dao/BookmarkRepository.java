package com.RestTutorial.dao;

import com.RestTutorial.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by atretjak on 25.12.2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
