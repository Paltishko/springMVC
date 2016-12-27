package com.RestTutorial.controllers;

import com.RestTutorial.dao.AccountRepository;
import com.RestTutorial.dao.BookmarkRepository;
import com.RestTutorial.model.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by atretjak on 25.12.2016.
 */

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/bookmarks")
public class BookmarkRestController {
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<BookmarkResource> readBookmarks(Principal principal) {
        this.validateUser(principal);

        List<BookmarkResource> bookmarkResourceList = bookmarkRepository.findByAccountUsername(principal.getName())
                .stream()
                .map(BookmarkResource::new)
                .collect(Collectors.toList());
        return new Resources<>(bookmarkResourceList);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addBookmark(Principal principal, @RequestBody Bookmark input) {
        this.validateUser(principal);

        return this.accountRepository
                .findByUsername(principal.getName())
                .map(account -> {
                    Bookmark bookmark = bookmarkRepository
                            .save(new Bookmark(account, input.getUri(), input.getDescription()));

                    Link forOneBookmark = new BookmarkResource(bookmark).getLink(Link.REL_SELF);
                    return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    BookmarkResource readBookmark(Principal principal, @PathVariable Long bookmarkId) {
        this.validateUser(principal);
        return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
    }

    private void validateUser(Principal principal) {
        String userId = principal.getName();
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
