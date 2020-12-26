package inventory.dao;

import inventory.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class SupplierDAOImpl extends BaseDAOImpl<Supplier> implements SupplierDAO<Supplier> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Supplier supplier) {
        String sql = "INSERT INTO supplier (name, phone, address, create_date,update_date, active_flag)"
                + " VALUES (?, ?, ?, ?,?,?)";

        jdbcTemplate.update(sql, supplier.getName(), supplier.getPhone(), supplier.getAddress(),
                supplier.getCreateDate(), supplier.getUpdateDate(), supplier.getActiveFlag());
    }

    @Override
    public void updateDTO(Supplier supplier) {
        String sql = "UPDATE supplier SET " +
                "name=?, phone=?, address=?, update_date=?, active_flag=? WHERE id=?";

        jdbcTemplate.update(sql, supplier.getName(), supplier.getPhone(), supplier.getAddress(), supplier.getUpdateDate(), supplier.getActiveFlag(), supplier.getId());

    }
}
