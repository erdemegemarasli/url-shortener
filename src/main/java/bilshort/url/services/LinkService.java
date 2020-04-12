package bilshort.url.services;

import bilshort.url.models.Link;

import java.util.Optional;

public interface LinkService {
    Optional<Link> getLongLink(String shortLink);
    Optional<Link> createShortLink(String longLink, String preferredShortLink, String timeToLive);
}
