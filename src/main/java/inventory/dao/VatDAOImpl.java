package inventory.dao;

import inventory.model.Vat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Date;

@Repository
@Transactional(rollbackFor = Exception.class)
public class VatDAOImpl extends BaseDAOImpl<Vat> implements VatDAO<Vat> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Vat vat) {
        String sql = "INSERT INTO vat (code, tax, percent, active_flag, create_date, update_date," +
                "supplier_id, user_id)"
                + " VALUES (?, ?, ?, ?,?,?,?,?)";

        jdbcTemplate.update(sql, vat.getCode(), vat.getTax(), vat.getPercent(), vat.getActiveFlag(), vat.getCreateDate(), vat.getUpdateDate(),
                vat.getSupplierId(), vat.getUserId());
    }

    @Override
    public void updateDTO(Vat vat) {
        String sql = "UPDATE vat SET " +
                "supplier_id=?, code =?, tax=?, update_date=?, user_id=?, active_flag=? WHERE id=?";

        jdbcTemplate.update(sql, vat.getSupplierId(), vat.getCode(), vat.getTax(), vat.getUpdateDate(), vat.getUserId(), vat.getActiveFlag(), vat.getId());
    }

    @Override
    public Vat findByIdDTO(int id) {
        String sql = "SELECT * FROM vat WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(Vat.class));

    }
}
