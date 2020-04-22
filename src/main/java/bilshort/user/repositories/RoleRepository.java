package bilshort.user.repositories;

import bilshort.user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleId(Integer roleId);
    Role findByRoleName(String role);

    @Transactional
    Long deleteByRoleId(Integer roleId);
}
