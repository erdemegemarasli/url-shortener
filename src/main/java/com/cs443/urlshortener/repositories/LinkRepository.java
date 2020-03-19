package com.cs443.urlshortener.repositories;

import com.cs443.urlshortener.models.Link;
import org.springframework.data.repository.Repository;

public interface LinkRepository extends Repository<Link, Integer> {
}
