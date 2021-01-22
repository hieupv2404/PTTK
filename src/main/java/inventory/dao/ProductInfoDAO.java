package inventory.dao;

import inventory.model.ProductInfo;

import java.sql.SQLException;

public interface ProductInfoDAO<E> extends BaseDAO<E> {
    void saveDTO(ProductInfo productInfo) throws SQLException;

    void updateDTO(ProductInfo productInfo) throws SQLException;
    ProductInfo findByIdDTO(int id);
}
