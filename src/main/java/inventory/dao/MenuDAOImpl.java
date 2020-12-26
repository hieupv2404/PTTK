package inventory.dao;

import inventory.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class MenuDAOImpl extends BaseDAOImpl<Menu> implements MenuDAO<Menu> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Menu menu) {
        String sql = "INSERT INTO menu (parent_id , url, name,order_index, active_flag,create_date, update_date)"
                + " VALUES (?, ?, ?,?,?,?,?)";

        jdbcTemplate.update(sql, menu.getParentId(), menu.getUrl(), menu.getName(), menu.getOrderIndex(), menu.getActiveFlag(), menu.getCreateDate(), menu.getUpdateDate());

    }

    @Override
    public void updateDTO(Menu menu) {
        String sql = "UPDATE menu SET " +
                "parent_id=?, url=?, name=?,  order_index=?, active_flag = ?, update_date=? WHERE id=?";

        jdbcTemplate.update(sql, menu.getParentId(), menu.getUrl(), menu.getName(), menu.getOrderIndex(), menu.getActiveFlag(), menu.getUpdateDate());

    }
}
