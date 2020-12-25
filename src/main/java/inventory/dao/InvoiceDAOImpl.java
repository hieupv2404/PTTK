package inventory.dao;

import inventory.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDAOImpl extends BaseDAOImpl<Invoice> implements InvoiceDAO<Invoice> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Invoice invoice) {
        String sql = "INSERT INTO invoice (code , type,product_id,qty, price, active_flag, supplier_id,create_date,update_date)"
                + " VALUES (?, ?, ?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, invoice.getCode(), invoice.getType(), invoice.getProductId(), invoice.getQty(), invoice.getPrice(), invoice.getActiveFlag(), invoice.getSupplierId(), invoice.getCreateDate(), invoice.getUpdateDate());

    }

    @Override
    public void updateDTO(Invoice invoice) {
        String sql = "UPDATE invoice SET " +
                "code=?, type=?, product_id=?,  qty=? , price=?,active_flag=?, supplier_id=?,update_date=? WHERE id=?";

        jdbcTemplate.update(sql, invoice.getCode(), invoice.getType(), invoice.getProductId(), invoice.getQty(), invoice.getPrice(), invoice.getActiveFlag(), invoice.getSupplierId(), invoice.getUpdateDate(), invoice.getId());

    }
}
