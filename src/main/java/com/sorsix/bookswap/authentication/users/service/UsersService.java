package com.sorsix.bookswap.authentication.users.service;

import com.sorsix.bookswap.authentication.users.domain.User;
import com.sorsix.bookswap.authentication.users.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository jpaUsersRepository;

    private static Logger logger = LoggerFactory.getLogger(UsersService.class);

    public User saveUser(String id, String name, String email){
        return this.jpaUsersRepository.save(new User(id,name, email));
    }

    public Optional<User> findUser(String id) {
        return this.jpaUsersRepository.findById(id);
    }
}
