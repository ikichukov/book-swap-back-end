package com.sorsix.bookswap.books.service;

import com.sorsix.bookswap.books.domain.Book;
import com.sorsix.bookswap.books.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    public Optional<Book> saveBook(Book book){
        if(this.booksRepository.existsById(book.getId())) return this.booksRepository.findById(book.getId());
        return Optional.of(this.booksRepository.save(book));
    }

}
