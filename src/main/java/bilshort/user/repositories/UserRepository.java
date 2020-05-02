package bilshort.user.repositories;

import bilshort.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    User findByApiKey(String apiKey);
    User findByUserId(Integer userId);
    User findByEmail(String email);

    @Transactional
    Long deleteByUserId(Integer userId);

}