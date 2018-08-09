package com.sorsix.bookswap.authentication.users.api;

import com.sorsix.bookswap.authentication.users.domain.User;
import com.sorsix.bookswap.authentication.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
@Scope("session")
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    User authenticatedUser;

    @GetMapping("/authenticated")
    public ResponseEntity<User> getAuthenticatedUser(){
        if(authenticatedUser.getId() == null) return ResponseEntity.notFound().build();

        return this.usersService.findUser(authenticatedUser.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        return this.usersService.findUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
