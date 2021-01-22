package inventory.dao;


import inventory.model.ProductStatusList;
import inventory.model.ProductStatusList;
import inventory.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductStatusListDAOImpl extends BaseDAOImpl<ProductStatusList> implements ProductStatusListDAO<ProductStatusList>{
    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName="com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = Constant.USERNAME;
    String password = Constant.PASSWORD;

    public ProductStatusListDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);

    @Override
    public void saveDTO(ProductStatusList productStatusList) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_product_status_list(?,?,?,?)}");
        statement.setString(1, productStatusList.getCode());
        statement.setInt(2, productStatusList.getType());
        statement.setInt(3, productStatusList.getUserId());
        statement.setInt(4, productStatusList.getVatId());
        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }

    @Override
    public void updateDTO(ProductStatusList productStatusList) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_product_status_list(?,?,?,?,?)}");
        statement.setString(1, productStatusList.getCode());
        statement.setInt(2, productStatusList.getActiveFlag());
        statement.setInt(3, productStatusList.getUserId());
        statement.setInt(4, productStatusList.getVatId());
        statement.setInt(5, productStatusList.getId());
        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }

    @Override
    public ProductStatusList findByIdDTO(int id) {
        return findById(ProductStatusList.class, id);
    }
}
