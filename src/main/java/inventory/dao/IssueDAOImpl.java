package inventory.dao;

import inventory.enums.EnumForDB;
import inventory.model.Issue;
import inventory.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
@Transactional(rollbackFor = Exception.class)
public class IssueDAOImpl extends BaseDAOImpl<Issue> implements IssueDAO<Issue>{

    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName="com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = Constant.USERNAME;
    String password = Constant.PASSWORD;

    public IssueDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);

    @Override
    public void saveDTO(Issue issue) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_issue(?,?,?)}");
        statement.setString(1, issue.getCode());
        statement.setInt(2, issue.getCustomerId());
        statement.setInt(3, issue.getUserId());
        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }

    @Override
    public void updateDTO(Issue issue) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_issue(?,?,?,?,?)}");
        statement.setString(1, issue.getCode());
        statement.setInt(2, issue.getCustomerId());
        statement.setInt(3, issue.getUserId());
        statement.setInt(4, issue.getActiveFlag());
        statement.setInt(5, issue.getId());
        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }

    @Override
    public Issue findByIdDTO(int id) {
        return findById(Issue.class, id);
    }

}
