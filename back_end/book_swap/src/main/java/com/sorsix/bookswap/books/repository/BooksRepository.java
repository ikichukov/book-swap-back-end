package com.sorsix.bookswap.books.repository;

import com.sorsix.bookswap.books.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, String> {
}
