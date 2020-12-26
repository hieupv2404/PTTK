package inventory.dao;

import inventory.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDAOImpl extends BaseDAOImpl<Users> implements UserDAO<Users> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDTO(Users users) {
        String sql = "INSERT INTO users (user_name, password, email, name,status)"
                + " VALUES (?, ?, ?, ?,?)";

        jdbcTemplate.update(sql, users.getUserName(), users.getPassword(), users.getEmail(),
                users.getName(), users.getStatus());
    }

    @Override
    public void updateDTO(Users users) {
        String sql = "UPDATE users SET " +
                "user_name=?, password=?, email=?, name=?, status=? WHERE id=?";

        jdbcTemplate.update(sql, users.getUserName(), users.getPassword(), users.getEmail(), users.getName(), users.getStatus(), users.getId());
    }
}
