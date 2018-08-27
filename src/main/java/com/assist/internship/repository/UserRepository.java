package com.assist.internship.repository;

import com.assist.internship.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findById(long id);
    User findByResetToken(String token);
    User deleteByEmail(String email);
}