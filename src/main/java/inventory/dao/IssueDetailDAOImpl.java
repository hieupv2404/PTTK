package inventory.dao;

import inventory.model.Issue;
import inventory.model.IssueDetail;
import inventory.model.Vat;
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
public class IssueDetailDAOImpl extends BaseDAOImpl<IssueDetail> implements IssueDetailDAO<IssueDetail>{

    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName="com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = "root";
    String password = "ult.zda1";

    public IssueDetailDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);

    @Override
    public void saveDTO(IssueDetail issueDetail) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_issue_detail(?,?,?)}");
        statement.setInt(1, issueDetail.getProductInfo().getId());
        statement.setInt(2, issueDetail.getIssueId());
        statement.setString(3, issueDetail.getImei());
        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }

    @Override
    public void updateDTO(IssueDetail issueDetail) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_product_detail(?,?,?)}");
        statement.setString(1, issueDetail.getImei());
        statement.setInt(2, issueDetail.getActiveFlag());
        statement.setInt(3, issueDetail.getId());
        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }

    @Override
    public IssueDetail findByIdDTO(int id) {
        return null;
    }
}
