package inventory.dao;

import inventory.model.ProductDetailPt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductDetailPtDAOImpl extends BaseDAOImpl<ProductDetailPt> implements ProductDetailPtDAO<ProductDetailPt> {
}
