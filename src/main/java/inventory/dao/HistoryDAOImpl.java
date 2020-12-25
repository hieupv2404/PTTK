package inventory.dao;

import inventory.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class HistoryDAOImpl extends BaseDAOImpl<History> implements HistoryDAO<History> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(History history) {
        String sql = "INSERT INTO history (action_name, type,product_id,qty,price,user_id,active_flag,create_date,update_date)"
                + " VALUES (?, ?, ?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, history.getActionName(), history.getType(), history.getProductId(), history.getQty(), history.getPrice(), history.getUserId(), history.getActiveFlag(), history.getCreateDate(), history.getUpdateDate());
    }
}
