package com.cs443.urlshortener.services;

import com.cs443.urlshortener.models.Link;

import java.util.Optional;

public interface LinkService {
    Optional<Link> getLongLink(String shortLink);
    Optional<Link> createShortLink(String longLink, String preferredShortLink, String timeToLive);
}
