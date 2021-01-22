package inventory.dao;


import inventory.model.Customer;
import inventory.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class CustomerDAOImpl extends BaseDAOImpl<Customer> implements CustomerDAO<Customer> {

    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName="com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = Constant.USERNAME;
    String password = Constant.PASSWORD;

    public CustomerDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);

    @Override
    public void saveDTO(Customer customer) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_customer(?,?,?)}");
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getPhone());
        statement.setString(3, customer.getAddress());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }
    }

    @Override
    public void updateDTO(Customer customer) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_customer(?,?,?,?,?)}");
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getPhone());
        statement.setString(3, customer.getAddress());
        statement.setInt(4, customer.getActiveFlag());
        statement.setInt(5, customer.getId());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(Customer.class));

    }
}
