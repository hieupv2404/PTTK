package inventory.dao;

import inventory.model.Shelf;

import java.sql.SQLException;

public interface ShelfDAO<E> extends BaseDAO<E> {
    void saveDTO(Shelf shelf) throws SQLException;

    void updateDTO(Shelf shelf) throws SQLException;
}
