package inventory.dao;

import inventory.model.Shelf;
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
public class ShelfDAOImpl extends BaseDAOImpl<Shelf> implements ShelfDAO<Shelf> {
    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName="com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = Constant.USERNAME;
    String password = Constant.PASSWORD;

    public ShelfDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);
    @Override
    public void saveDTO(Shelf shelf) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_shelf(?,?,?)}");
        statement.setString(1, shelf.getName());
        statement.setString(2, shelf.getDescription());
        statement.setInt(3, shelf.getTotal());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }


    }

    @Override
    public void updateDTO(Shelf shelf) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_shelf(?,?,?,?,?)}");
        statement.setString(1, shelf.getName());
        statement.setString(2, shelf.getDescription());
        statement.setInt(3, shelf.getActiveFlag());
        statement.setInt(4, shelf.getTotal());
        statement.setInt(5, shelf.getId());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }

    }
}
