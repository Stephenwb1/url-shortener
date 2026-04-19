package com.stephenwb.url_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stephenwb.url_shortener.model.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

    public Url findByShortCode(String shortCode);

}
