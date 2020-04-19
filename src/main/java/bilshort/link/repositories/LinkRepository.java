package bilshort.link.repositories;

import bilshort.link.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {
    Link findByShortLink(String shortLink);

    Link findByLinkId(Integer linkId);

    @Transactional
    Long deleteByLinkId(Integer linkId);

    @Query(
            value = "SELECT * FROM links l WHERE l.user_id = :ownerId",
            nativeQuery = true)
    List<Link> findByUserIdEx(@Param("ownerId") Integer ownerId);
}