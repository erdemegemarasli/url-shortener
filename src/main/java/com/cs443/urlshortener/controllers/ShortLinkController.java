package com.cs443.urlshortener.controllers;

import com.cs443.urlshortener.models.Link;
import com.cs443.urlshortener.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1/shortURL")
@RestController
public class ShortLinkController {

    @Autowired
    private LinkService linkService;

    // New endpoint required for redirect ?
/*
    @RequestMapping("/{shortLink}")
    public String getRandomJoke(@PathVariable String shortLink) {
        Optional<Link> link = linkService.getLongLink(shortLink);
        return "redirect:" + link.orElseThrow(null).getLongLink();
    }
*/

    @PostMapping
    public String createShortLink(@RequestParam String longLink, @RequestParam(required = false) String preferredShortLink, @RequestParam(required = false) String timeToLive) {
        Optional<Link> link = linkService.createShortLink(longLink, preferredShortLink, timeToLive);
        return link.orElseThrow(null).getShortLink();
    }

    @GetMapping
    public List<Link> getAllShortURLs() {
        //  todo return all short URLs
        return null;
    }

    @GetMapping(path = "{id}")
    public Link getShortURLById(@PathVariable("id") UUID id) {
        // todo return shortURLById
        return null;
    }

    @DeleteMapping(path = "{id}")
    public void deleteShortURLById(@PathVariable("id") UUID id) {

    }

    @PutMapping(path = "{id}")
    public void updateShortURLById(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Link newShortURL) {

    }
}
