package inventory.dao;

import inventory.model.Menu;

public interface MenuDAO<E> extends BaseDAO<E> {
    void saveDTO(Menu menu);

    void updateDTO(Menu menu);

}
