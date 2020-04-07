package com.cs443.urlshortener.controllers;

import com.cs443.urlshortener.models.Link;
import com.cs443.urlshortener.services.LinkService;
import com.cs443.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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

        List<Link> links = new ArrayList<>();
        Date date = new Date();
        System.out.println();

        User user = new User();
        user.setId(4);
        user.setUserName("Ztan");
        user.setPassword("1235679");

        links.add(new Link(user, "Link1", "URL1", date.toString(), date.toString(), 0));
        links.add(new Link(user, "Link2", "URL2", date.toString(), date.toString(), 0));
        links.add(new Link(user, "Link3", "URL3", date.toString(), date.toString(), 0));

        return links;
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
        System.out.println(id.toString());
    }
}
