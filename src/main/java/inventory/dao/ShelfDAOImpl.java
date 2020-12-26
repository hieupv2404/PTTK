package inventory.dao;

import inventory.model.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ShelfDAOImpl extends BaseDAOImpl<Shelf> implements ShelfDAO<Shelf> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Shelf shelf) {
        String sql = "INSERT INTO shelf (name, description, active_flag, create_date, update_date,total,qty)"
                + " VALUES (?, ?, ?, ?,?,?,?)";

        jdbcTemplate.update(sql, shelf.getName(), shelf.getDescription(), shelf.getActiveFlag(),
                shelf.getCreateDate(), shelf.getUpdateDate(), shelf.getTotal(), shelf.getQty());

    }

    @Override
    public void updateDTO(Shelf shelf) {
        String sql = "UPDATE shelf SET " +
                "name=?, description=?, active_flag=?, create_date=?, update_date=? ,total=?,qty=? WHERE id=?";

        jdbcTemplate.update(sql, shelf.getName(), shelf.getDescription(), shelf.getActiveFlag(), shelf.getCreateDate(), shelf.getUpdateDate(), shelf.getTotal(), shelf.getQty(), shelf.getId());

    }
}
