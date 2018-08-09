package com.sorsix.bookswap.books.api;

import com.sorsix.bookswap.authentication.users.domain.User;
import com.sorsix.bookswap.authentication.users.service.UsersService;
import com.sorsix.bookswap.books.domain.Book;
import com.sorsix.bookswap.books.service.BooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/books")
@Component
@Scope("session")
public class BooksController {

    @Autowired
    private User authenticatedUser;

    @Autowired
    private BooksService booksService;

    @Autowired
    private UsersService usersService;

    private static Logger logger = LoggerFactory.getLogger(BooksController.class);

    @PostMapping("")
    public ResponseEntity<?> addNewBook(@RequestBody Book book){
        logger.info("POST /api/books " + book.getTitle());

        return this.booksService.saveBook(book).map(newBook -> {
            URI location = createUriLocationForId(newBook.getId());
            this.usersService.addBookForUser(book, authenticatedUser);
            return ResponseEntity.created(location).build();
        })
        .orElse(ResponseEntity.badRequest().build());
    }

    public URI createUriLocationForId(String id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
