package inventory.dao;

import inventory.model.ProductInfo;
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
@Transactional(rollbackFor = Exception.class)
public class ProductInfoDAOImpl extends BaseDAOImpl<ProductInfo> implements ProductInfoDAO<ProductInfo> {
    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName = "com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = Constant.USERNAME;
    String password = Constant.PASSWORD;

    public ProductInfoDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);


    @Override
    public void saveDTO(ProductInfo productInfo) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_product_info(?,?,?,?)}");
        statement.setInt(1, productInfo.getCateId());
        statement.setString(2, productInfo.getName());
        statement.setString(3, productInfo.getDescription());
        statement.setString(4, productInfo.getImgUrl());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }
    }

    @Override
    public void updateDTO(ProductInfo productInfo) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_product_info(?,?,?,?,?,?)}");
        statement.setInt(1, productInfo.getCateId());
        statement.setString(2, productInfo.getName());
        statement.setString(3, productInfo.getDescription());
        statement.setString(4, productInfo.getImgUrl());
        statement.setInt(5, productInfo.getActiveFlag());
        statement.setInt(6, productInfo.getId());
        if (!statement.execute()) {
            statement.close();
            throw new SQLException();
        }
    }

    @Override
    public ProductInfo findByIdDTO(int id) {
        return findById(ProductInfo.class, id);
    }
}
