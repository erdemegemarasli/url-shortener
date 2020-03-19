package com.cs443.urlshortener.controllers;

import com.cs443.urlshortener.models.Link;
import com.cs443.urlshortener.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ShortLinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping("/{shortLink}")
    public String getRandomJoke(@PathVariable String shortLink){
        Optional<Link> link = linkService.getLongLink(shortLink);
        return "redirect:" + link.orElseThrow(null).getLongLink();
    }

    @PostMapping("/createShortLink")
    public String createShortLink(@RequestParam String longLink, @RequestParam(required = false) String preferredShortLink, @RequestParam(required = false) String timeToLive){
        Optional<Link> link = linkService.createShortLink(longLink, preferredShortLink, timeToLive);
        return link.orElseThrow(null).getShortLink();
    }
}
