package inventory.dao;

import inventory.model.ProductStatusDetail;

import java.sql.SQLException;

public interface ProductStatusDetailDAO<E> extends BaseDAO<E> {
    void saveDTO(ProductStatusDetail productStatusDetail) throws SQLException;
    void updateDTO(ProductStatusDetail productStatusDetail) throws SQLException;
    ProductStatusDetail findByIdDTO(int id);
}
