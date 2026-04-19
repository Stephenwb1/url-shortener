package com.stephenwb.url_shortener.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stephenwb.url_shortener.model.Url;
import com.stephenwb.url_shortener.repository.UrlRepository;

@RestController
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    @GetMapping("/r/{shortCode}")
    public ResponseEntity<Void> getUrl(@PathVariable String shortCode) {

        Url myUrl = urlRepository.findByShortCode(shortCode);

        return (ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(myUrl.getOldUrl()))
                .build());
    }

    @PostMapping("/api/urls")
    public String addUrl(@RequestBody String url) {

        Url myUrl = new Url();
        myUrl.setOldUrl(url);
        myUrl.setShortCode(UUID.randomUUID().toString().substring(0, 7));
        urlRepository.save(myUrl);

        return myUrl.getShortCode();
    }

}
