package bilshort.link.controllers;

import bilshort.link.models.Link;
import bilshort.link.models.LinkDTO;
import bilshort.link.services.LinkService;
import bilshort.user.controllers.UserController;
import bilshort.user.services.UserDetailsServiceEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public LinkDTO createShortLink(@RequestBody @NonNull LinkDTO linkDTO) {
        

        Link link = linkService.createShortLink(linkDTO);

        LinkDTO response = new LinkDTO();
        response.setExpTime(link.getTimeToLive());
        response.setLongUrl(link.getLongLink());
        response.setShortUrl(link.getShortLink());
        response.setId(link.getLinkId());
        response.setDescription(null);

        return response;
    }

    @GetMapping
    public ResponseEntity<?> getAllShortURLs(@RequestParam Map<String, String> params) {
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));
        if (!isAdmin){
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        List<LinkDTO> links = new ArrayList<>();
        if (params.isEmpty())
        {
            for (Link link : linkService.getAllLinks())
            {
                LinkDTO tempLink = new LinkDTO();

                tempLink.setExpTime(link.getTimeToLive());
                tempLink.setLongUrl(link.getLongLink());
                tempLink.setShortUrl(link.getShortLink());
                tempLink.setId(link.getLinkId());
                tempLink.setUsername(link.getUserId().getUsername());
                tempLink.setDescription(null);

                links.add(tempLink);
            }
        }
        else {
            if (params.containsKey("userId"))
            {
                Integer userId = Integer.parseInt(params.get("userId"));
                List<Link> linksByUserId = linkService.getLinksByUserId(userId);
                for (Link link : linksByUserId)
                {
                    LinkDTO tempLink = new LinkDTO();

                    tempLink.setExpTime(link.getTimeToLive());
                    tempLink.setLongUrl(link.getLongLink());
                    tempLink.setShortUrl(link.getShortLink());
                    tempLink.setId(link.getLinkId());
                    tempLink.setUsername(link.getUserId().getUsername());
                    tempLink.setDescription(null);

                    links.add(tempLink);
                }
            }

        }


        return ResponseEntity.ok(links);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getShortURLById(@PathVariable("id") Integer id) {

        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin && !isUser)
        {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        Link link = linkService.getLinkById(id);

        LinkDTO response = new LinkDTO();

        response.setExpTime(link.getTimeToLive());
        response.setLongUrl(link.getLongLink());
        response.setShortUrl(link.getShortLink());
        response.setId(link.getLinkId());
        response.setUsername(link.getUserId().getUsername());
        response.setDescription(null);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteShortURLById(@PathVariable("id") int id) {
        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin && !isUser)
        {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }
        Long operationCode = linkService.deleteLinkById(id);
        if (operationCode == 0){
            return ResponseEntity.notFound().build();
        }
        else if (operationCode == 1){
            return ResponseEntity.ok().body("Deletion Successful");
        }

        return ResponseEntity.badRequest().body("Unexpected Error");

    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateShortURLById(@PathVariable("id") int id, @RequestBody @NonNull LinkDTO linkDTO) {

        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin && !isUser)
        {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }
        Link link = linkService.getLinkById(id);
        link.setShortLink(linkDTO.getShortUrl());
        link.setTimeToLive(linkDTO.getExpTime());
        link.setLongLink(linkDTO.getLongUrl());

        link = linkService.updateLink(link);

        LinkDTO response = new LinkDTO();

        response.setExpTime(link.getTimeToLive());
        response.setLongUrl(link.getLongLink());
        response.setShortUrl(link.getShortLink());
        response.setId(link.getLinkId());
        response.setUsername(link.getUserId().getUsername());
        response.setDescription(null);

        return ResponseEntity.ok(response);
    }
}
