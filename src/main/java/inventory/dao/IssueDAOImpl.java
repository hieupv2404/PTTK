package inventory.dao;

import inventory.model.Issue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class IssueDAOImpl extends BaseDAOImpl<Issue> implements IssueDAO<Issue>{
}
