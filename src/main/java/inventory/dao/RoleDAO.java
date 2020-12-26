package inventory.dao;

import inventory.model.Role;

public interface RoleDAO<E> extends BaseDAO<E> {
    void saveDTO(Role role);
    void updateDTO(Role role);
    Role findRoleById(int id);

}
