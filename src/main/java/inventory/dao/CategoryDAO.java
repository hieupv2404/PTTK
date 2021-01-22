package inventory.dao;

import inventory.model.Category;

import java.sql.SQLException;

public interface CategoryDAO<E> extends BaseDAO<E> {
    void saveDTO(Category category) throws SQLException;

    void updateDTO(Category category) throws SQLException;

    Category findById(int id);

}
