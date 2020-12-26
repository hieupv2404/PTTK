package inventory.dao;

import inventory.model.Users;

public interface UserDAO<E> extends BaseDAO<E> {
    void saveDTO(Users users);

    void updateDTO(Users users);

}
