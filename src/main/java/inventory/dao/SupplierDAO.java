package inventory.dao;

import inventory.model.Supplier;

public interface SupplierDAO<E> extends BaseDAO<E> {
    void saveDTO(Supplier supplier);

    void updateDTO(Supplier supplier);
}
