package inventory.dao;

import inventory.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class IssueDAOImpl extends BaseDAOImpl<Issue> implements IssueDAO<Issue>{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Issue issue) {

    }

    @Override
    public void updateDTO(Issue issue) {

    }

    @Override
    public Issue findByIdDTO(int id) {
        return null;
    }

    @Override
    public void deleteDTO(Issue issue) {

    }
}
