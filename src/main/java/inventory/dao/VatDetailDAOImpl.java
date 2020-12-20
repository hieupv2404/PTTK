package inventory.dao;


import inventory.model.Vat;
import inventory.model.VatDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor=Exception.class)
public class VatDetailDAOImpl extends BaseDAOImpl<VatDetail> implements VatDetailDAO<VatDetail>{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(VatDetail vatDetail) {
        String sql = "INSERT INTO vat_detail (vat_id, product_id, qty, price_one, active_flag)"
                + " VALUES (?, ?, ?, ?,?)";

        jdbcTemplate.update(sql, vatDetail.getVatId(), vatDetail.getProductInfoId(), vatDetail.getQty(),
                vatDetail.getPriceOne(), vatDetail.getActiveFlag());
    }

    @Override
    public void updateDTO(VatDetail vatDetail) {
        String sql = "UPDATE vat_detail SET " +
                "vat_id=?, product_id=?, qty=?, price_one=?, active_flag=? WHERE id=?";

        jdbcTemplate.update(sql, vatDetail.getVatId(), vatDetail.getProductInfoId(), vatDetail.getQty(),  vatDetail.getPriceOne(), vatDetail.getActiveFlag(), vatDetail.getId());
    }

    @Override
    public VatDetail findByIdDTO(int id) {
        String sql = "SELECT * FROM vat_detail WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(VatDetail.class));

    }
}
