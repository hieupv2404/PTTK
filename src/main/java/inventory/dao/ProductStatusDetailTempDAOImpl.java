package inventory.dao;

import inventory.model.Category;
import inventory.model.ProductStatusDetailTemp;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductStatusDetailTempDAOImpl extends BaseDAOImpl<ProductStatusDetailTemp> implements ProductStatusDetailTempDAO<ProductStatusDetailTemp>{

}
