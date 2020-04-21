package bilshort.user.repositories;

import bilshort.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    User findByUserId(Integer userId);

    @Transactional
    Long deleteByUserId(Integer userId);

}