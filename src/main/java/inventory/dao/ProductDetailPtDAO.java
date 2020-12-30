package inventory.dao;

import inventory.model.ProductDetailPt;

import java.sql.SQLException;

public interface ProductDetailPtDAO<E> extends BaseDAO<E> {
    void saveDTO(ProductDetailPt productDetailPt) throws SQLException;

}
