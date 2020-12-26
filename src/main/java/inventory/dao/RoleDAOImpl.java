package inventory.dao;

import inventory.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO<Role> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Role role) {
        String sql = "INSERT INTO role (role_name, description, active_flag, create_date, update_date)"
                + " VALUES (?, ?, ?, ?,?)";

        jdbcTemplate.update(sql, role.getRoleName(), role.getDescription(), role.getActiveFlag(), role.getCreateDate(), role.getUpdateDate());

    }

    @Override
    public void updateDTO(Role role) {
        String sql = "UPDATE role SET " +
                "role_name=?, description=?, active_flag=?, update_date=? WHERE id=?";

        jdbcTemplate.update(sql, role.getRoleName(), role.getDescription(), role.getActiveFlag(), role.getUpdateDate(), role.getId());

    }

    @Override
    public Role findRoleById(int id) {
        String sql = "SELECT * FROM role WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(Role.class));
    }
}
