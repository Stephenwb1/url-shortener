package com.stephenwb.url_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stephenwb.url_shortener.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
