package inventory.dao;

import inventory.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CategoryDAOImpl extends BaseDAOImpl<Category> implements CategoryDAO<Category> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Category category) {
        String sql = "INSERT INTO category (name, code, description,active_flag,create_date,update_date)"
                + " VALUES (?, ?,?,?,?,?)";

        jdbcTemplate.update(sql, category.getName(), category.getCode(), category.getDescription(), category.getActiveFlag(), category.getCreateDate(), category.getUpdateDate());
    }

    @Override
    public void updateDTO(Category category) {
        String sql = "UPDATE category SET " +
                "name=?, code=?, description=?,  active_flag=?,update_date=? WHERE id=?";

        jdbcTemplate.update(sql, category.getName(), category.getCode(), category.getDescription(), category.getActiveFlag(), category.getUpdateDate(), category.getId());
    }

    @Override
    public Category findById(int id) {
        String sql = "SELECT * FROM category WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(Category.class));
    }
}
