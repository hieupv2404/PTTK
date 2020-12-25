package inventory.dao;


import inventory.model.InvoiceTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceTempDAOImpl extends BaseDAOImpl<InvoiceTemp> implements InvoiceTempDAO<InvoiceTemp> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(InvoiceTemp invoiceTemp) {
        String sql = "INSERT INTO invoice_temp (active_flag , code, product_name,qty, price, update_date)"
                + " VALUES (?, ?, ?,?,?,?)";

        jdbcTemplate.update(sql, invoiceTemp.getActiveFlag(), invoiceTemp.getCode(), invoiceTemp.getProductName(), invoiceTemp.getQty(), invoiceTemp.getPrice(), invoiceTemp.getUpdateDate());

    }

    @Override
    public void updateDTO(InvoiceTemp invoiceTemp) {
        String sql = "UPDATE invoiceTemp SET " +
                "active_flag=?, code=?, product_name=?,  qty=?, price = ?, update_date=? WHERE id=?";

        jdbcTemplate.update(sql, invoiceTemp.getActiveFlag(), invoiceTemp.getCode(), invoiceTemp.getProductName(), invoiceTemp.getQty(), invoiceTemp.getPrice(), invoiceTemp.getUpdateDate());

    }

    @Override
    public InvoiceTemp findById(int id) {
        String sql = "SELECT * FROM invoice_temp WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(InvoiceTemp.class));
    }
}
