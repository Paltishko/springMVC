package com.RestTutorial.controllers;

import com.RestTutorial.controllers.BookmarkRestController;
import com.RestTutorial.model.Bookmark;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by atretjak on 25.12.2016.
 */


public class BookmarkResource extends ResourceSupport {

    private final Bookmark bookmark;

    BookmarkResource(Bookmark bookmark){
        String username = bookmark.getAccount().getUsername();
        this.bookmark = bookmark;
        this.add(new Link(bookmark.getUri(),"bookmark-uri"));
        this.add(linkTo(BookmarkRestController.class, username).withRel("bookmarks"));
        this.add(linkTo(methodOn(BookmarkRestController.class, username)
                .readBookmark(username, bookmark.getId())).withSelfRel());
    }

    public Bookmark getBookmark(){
        return bookmark;
    }

}
