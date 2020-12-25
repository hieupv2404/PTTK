package inventory.dao;


import inventory.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CustomerDAOImpl extends BaseDAOImpl<Customer> implements CustomerDAO<Customer> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDatasource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Customer customer) {
        String sql = "INSERT INTO customer (name, phone, address,active_flag,create_date,update_date)"
                + " VALUES (?, ?, ?,?,?,?)";

        jdbcTemplate.update(sql, customer.getName(), customer.getPhone(), customer.getAddress(), customer.getActiveFlag(), customer.getCreateDate(), customer.getUpdateDate());
    }

    @Override
    public void updateDTO(Customer customer) {
        String sql = "UPDATE customer SET " +
                "name=?, phone=?, address=?,  active_flag=? , update_date= ? WHERE id=?";

        jdbcTemplate.update(sql, customer.getName(), customer.getPhone(), customer.getAddress(), customer.getActiveFlag(), customer.getUpdateDate(), customer.getId());
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(Customer.class));

    }
}
