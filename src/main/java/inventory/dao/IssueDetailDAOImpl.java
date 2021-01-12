package inventory.dao;

import inventory.model.IssueDetail;
import inventory.model.Vat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class IssueDetailDAOImpl extends BaseDAOImpl<IssueDetail> implements IssueDetailDAO<IssueDetail>{
}
