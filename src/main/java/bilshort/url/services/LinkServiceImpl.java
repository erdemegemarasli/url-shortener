package bilshort.url.services;

import bilshort.url.models.Link;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService{


    @Override
    public Optional<Link> getLongLink(String shortLink) {
        //todo return long link given the short link -> perform query on db, if exists return longlink to redirect, else return empty
        return Optional.empty();
    }

    @Override
    public Optional<Link> createShortLink(String longLink, String preferredShortLink, String timeToLive) {
        //todo create and return a short link given longLink and preferredShortLink -> if preferred name is taken, return empty
        return Optional.empty();
    }
}
