package inventory.dao;

import inventory.model.Supplier;

import java.sql.SQLException;

public interface SupplierDAO<E> extends BaseDAO<E> {
    void saveDTO(Supplier supplier)throws SQLException;

    void updateDTO(Supplier supplier) throws SQLException;

    Supplier findByIdDTO(int id);
}
