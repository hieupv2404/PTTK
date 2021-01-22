package inventory.dao;

import inventory.model.Customer;

import java.sql.SQLException;

public interface CustomerDAO<E> extends BaseDAO<E> {
    void saveDTO(Customer customer) throws SQLException;

    void updateDTO(Customer customer) throws SQLException;

    Customer findById(int id);
}
