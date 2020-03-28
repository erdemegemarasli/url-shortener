package com.cs443.user.repositories;

import com.cs443.user.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Business findByBusinessName(String businessName);
    Business findByApiKey(String apiKey);
}
