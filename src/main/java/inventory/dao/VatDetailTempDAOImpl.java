package inventory.dao;

import inventory.model.Category;
import inventory.model.VatDetailTemp;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class VatDetailTempDAOImpl extends BaseDAOImpl<VatDetailTemp> implements VatDetailTempDAO<VatDetailTemp>{

}
