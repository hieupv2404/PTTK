package inventory.dao;


import inventory.model.ProductStatusList;

import java.sql.SQLException;

public interface ProductStatusListDAO<E> extends BaseDAO<E> {
    void saveDTO(ProductStatusList productStatusList) throws SQLException;
    void updateDTO(ProductStatusList productStatusList) throws SQLException;
    ProductStatusList findByIdDTO(int id);

}
