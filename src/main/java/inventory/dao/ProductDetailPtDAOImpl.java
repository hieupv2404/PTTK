package inventory.dao;

import inventory.model.ProductDetailPt;
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
public class ProductDetailPtDAOImpl extends BaseDAOImpl<ProductDetailPt> implements ProductDetailPtDAO<ProductDetailPt> {
    private JdbcTemplate jdbcTemplate;
    String dbDriverClassName="com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/inventory_management";
    String user = "root";
    String password = "ult.zda1";

    public ProductDetailPtDAOImpl() throws SQLException {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Connection conn = DriverManager.getConnection(dbURL, user, password);


    @Override
    public void saveDTO(ProductDetailPt productDetailPt) throws SQLException {

            CallableStatement statement = conn.prepareCall("{call insert_product_detail(?, ?,?,?,?,?,?)}");
            statement.setInt(1, productDetailPt.getProductInfoId());
            statement.setInt(2, productDetailPt.getProductStatusList().getId());
            statement.setInt(3, productDetailPt.getActiveFlag());
            statement.setString(4, productDetailPt.getCode());
            statement.setInt(5, productDetailPt.getSupplier().getId());
            statement.setInt(6, productDetailPt.getShelfId());
            statement.setBigDecimal(7, productDetailPt.getPriceOut());
            if(!statement.execute())
            {
                statement.close();
                throw  new SQLException();
            }
        }

    @Override
    public void updateDTO(ProductDetailPt productDetailPt) throws SQLException {
        CallableStatement statement = conn.prepareCall("{call update_product_detail(?,?, ?,?,?,?,?,?)}");
        statement.setInt(1, productDetailPt.getProductInfoId());
        statement.setInt(2, productDetailPt.getProductStatusList().getId());
        statement.setInt(3, productDetailPt.getActiveFlag());
        statement.setString(4, productDetailPt.getCode());
        statement.setInt(5, productDetailPt.getSupplier().getId());
        statement.setInt(6, productDetailPt.getShelfId());
        statement.setBigDecimal(7, productDetailPt.getPriceOut());
        statement.setInt(8, productDetailPt.getId());

        if(!statement.execute())
        {
            statement.close();
            throw  new SQLException();
        }
    }
}
