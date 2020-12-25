package inventory.dao;

import inventory.model.Customer;

public interface CustomerDAO<E> extends BaseDAO<E> {
    void saveDTO(Customer customer);
    void updateDTO(Customer customer);

    Customer findById(int id);
}
