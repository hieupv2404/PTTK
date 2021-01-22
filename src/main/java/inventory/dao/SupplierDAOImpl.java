package inventory.dao;

import inventory.model.Supplier;
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
public class SupplierDAOImpl extends BaseDAOImpl<Supplier> implements SupplierDAO<Supplier> {
    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName="com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = Constant.USERNAME;
    String password = Constant.PASSWORD;

    public SupplierDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);

    @Override
    public void saveDTO(Supplier supplier) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_supplier(?,?,?)}");
        statement.setString(1, supplier.getName());
        statement.setString(2, supplier.getPhone());
        statement.setString(3, supplier.getAddress());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }

    }

    @Override
    public void updateDTO(Supplier supplier) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_supplier(?,?,?,?,?)}");
        statement.setString(1, supplier.getName());
        statement.setString(2, supplier.getPhone());
        statement.setString(3, supplier.getAddress());
        statement.setInt(4, supplier.getActiveFlag());
        statement.setInt(5, supplier.getId());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }
    }

    @Override
    public Supplier findByIdDTO(int id) {
        return findById(Supplier.class, id);
    }
}
