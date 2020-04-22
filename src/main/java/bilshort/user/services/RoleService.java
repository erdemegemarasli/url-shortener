package bilshort.user.services;

import bilshort.user.models.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);
    List<Role> getAllRoles();
    Role getRoleById(Integer id);
    Long deleteRoleById(Integer id);
    Role updateRole(Role role);
    Role getRoleByName(String roleName);

}
