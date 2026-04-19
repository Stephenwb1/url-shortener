package com.stephenwb.url_shortener.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stephenwb.url_shortener.model.Url;
import com.stephenwb.url_shortener.repository.UrlRepository;

@RestController
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    @PostMapping("/api/urls")
    public String addUrl(@RequestBody String url) {

        Url myUrl = new Url();
        myUrl.setOldUrl(url);
        myUrl.setShortCode(UUID.randomUUID().toString().substring(0, 7));
        urlRepository.save(myUrl);

        return myUrl.getShortCode();
    }

    @GetMapping("/api/urls")
    public String getUrls() {

        Url myUrl = new Url();
        myUrl.setOldUrl(url);
        myUrl.setShortCode(UUID.randomUUID().toString().substring(0, 7));
        urlRepository.save(myUrl);

        return myUrl.getShortCode();
    }

}
