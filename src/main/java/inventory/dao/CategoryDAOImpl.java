package inventory.dao;

import inventory.conection.ConnectionDatabase;
import inventory.model.Category;
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
public class CategoryDAOImpl extends BaseDAOImpl<Category> implements CategoryDAO<Category> {

    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName = "com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = "root";
    String password = "123456789";
    private ConnectionDatabase connectionDatabase;

    public CategoryDAOImpl() throws SQLException {

    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Category category) throws SQLException {
        CallableStatement statement = conn.prepareCall("{ call  insert_category(?,?,?,?)}");
        statement.setString(1, category.getCode());
        statement.setString(2, category.getName());
        statement.setString(3, category.getDescription());
        statement.setInt(4, category.getActiveFlag());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }
    }

    @Override
    public void updateDTO(Category category) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_category(?,?,?,?,?)}");
        statement.setString(1, category.getCode());
        statement.setString(2, category.getName());
        statement.setString(3, category.getDescription());
        statement.setInt(4, category.getActiveFlag());
        statement.setInt(5, category.getId());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }
    }

    @Override
    public Category findById(int id) {
        return null;
    }
}
