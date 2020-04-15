package bilshort.link.controllers;

import bilshort.link.models.Link;
import bilshort.link.services.LinkService;
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

/*
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    boolean isUser  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
    boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));
*/

    @PostMapping
    public Link createShortLink(@RequestBody @NonNull Link link) {
        


        return linkService.createShortLink(link);
    }

    @GetMapping
    public List<Link> getAllShortURLs() {
        return linkService.getAllLinks();
    }

    @GetMapping(path = "{id}")
    public Link getShortURLById(@PathVariable("id") int id) {
        return null;
    }

    @DeleteMapping(path = "{id}")
    public void deleteShortURLById(@PathVariable("id") int id) {
    }

    @PutMapping(path = "{id}")
    public void updateShortURLById(@PathVariable("id") int id, @RequestBody @NonNull Link link) {
    }
}
