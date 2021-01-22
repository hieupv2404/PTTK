package inventory.dao;

import inventory.model.ProductStatusDetail;
import inventory.model.ProductStatusDetail;
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
public class ProductStatusDetailDAOImpl extends BaseDAOImpl<ProductStatusDetail> implements ProductStatusDetailDAO<ProductStatusDetail>{
    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName="com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = Constant.USERNAME;
    String password = Constant.PASSWORD;

    public ProductStatusDetailDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);

    @Override
    public void saveDTO(ProductStatusDetail productStatusDetail) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_product_status_detail(?,?,?)}");
        statement.setInt(1, productStatusDetail.getProductStatusList().getId());
        statement.setInt(2, productStatusDetail.getProductInfoId());
        statement.setInt(3, productStatusDetail.getQty());
        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }

    @Override
    public void updateDTO(ProductStatusDetail productStatusDetail) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_product_status_detail(?,?,?,?,?)}");
        statement.setInt(1, productStatusDetail.getProductStatusList().getId());
        statement.setInt(2, productStatusDetail.getProductInfo().getId());
        statement.setInt(3, productStatusDetail.getQty());
        statement.setInt(4, productStatusDetail.getActiveFlag());
        statement.setInt(5, productStatusDetail.getId());
        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }

    @Override
    public ProductStatusDetail findByIdDTO(int id) {
        return findById(ProductStatusDetail.class, id);
    }
}
