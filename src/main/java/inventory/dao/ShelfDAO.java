package inventory.dao;

import inventory.model.Shelf;

public interface ShelfDAO<E> extends BaseDAO<E> {
    void saveDTO(Shelf shelf);
    void updateDTO(Shelf shelf);
}
