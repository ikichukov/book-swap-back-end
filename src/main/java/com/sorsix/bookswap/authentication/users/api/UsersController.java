package com.sorsix.bookswap.authentication.users.api;

import com.sorsix.bookswap.authentication.users.domain.User;
import com.sorsix.bookswap.authentication.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Component
@Scope("session")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    User authenticatedUser;

    @GetMapping("/user")
    public ResponseEntity<User> getAuthenticatedUser(Principal principal){
        return usersService.findUser(authenticatedUser.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
