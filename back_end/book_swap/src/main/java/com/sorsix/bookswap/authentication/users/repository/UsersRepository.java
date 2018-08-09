package com.sorsix.bookswap.authentication.users.repository;

import com.sorsix.bookswap.authentication.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {

}
