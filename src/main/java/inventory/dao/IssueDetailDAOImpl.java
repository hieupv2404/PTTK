package inventory.dao;

import inventory.model.IssueDetail;
import inventory.model.Vat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class IssueDetailDAOImpl extends BaseDAOImpl<IssueDetail> implements IssueDetailDAO<IssueDetail>{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(IssueDetail issueDetail) {

    }

    @Override
    public void updateDTO(IssueDetail issueDetail) {

    }

    @Override
    public IssueDetail findByIdDTO(int id) {
        return null;
    }
}
