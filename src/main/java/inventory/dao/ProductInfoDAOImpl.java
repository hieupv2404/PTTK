package inventory.dao;

import inventory.model.ProductInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    String user = "root";
    String password = "123456789";
    Connection conn = DriverManager.getConnection(dbURL, user, password);

    public ProductInfoDAOImpl() throws SQLException {
    }

    @Override
    public void saveDTO(ProductInfo productInfo) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call insert_product_info(?,?,?,?,?)}");
        statement.setInt(1, productInfo.getCateId());
        statement.setString(2, productInfo.getName());
        statement.setString(3, productInfo.getDescription());
        statement.setString(4, productInfo.getImgUrl());
        statement.setInt(5, productInfo.getActiveFlag());
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
}
