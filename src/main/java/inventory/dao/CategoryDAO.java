package inventory.dao;

import inventory.model.Category;

public interface CategoryDAO<E> extends BaseDAO<E> {
    void saveDTO(Category category);
    void updateDTO(Category category);
    Category findById(int id);

}
