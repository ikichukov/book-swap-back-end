package com.sorsix.bookswap.authentication.users.service;

import com.sorsix.bookswap.authentication.users.domain.User;
import com.sorsix.bookswap.authentication.users.repository.UsersRepository;
import com.sorsix.bookswap.books.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    private static Logger logger = LoggerFactory.getLogger(UsersService.class);

    public User saveUser(String id, String name, String email){
        boolean exists = this.usersRepository.existsById(id);
        if(!exists) {
            return this.usersRepository.save(new User(id, name, email));
        }
        else return this.usersRepository.findById(id).get();
    }

    public Optional<User> findUser(String id) {
        return this.usersRepository.findById(id);
    }

    public void addBookForUser(Book book, User user) {
        Optional<User> optionalUser = usersRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            updatedUser.addBook(book);
            this.usersRepository.save(updatedUser);
        }
    }
}
