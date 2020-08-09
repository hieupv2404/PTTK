package inventory.service;

import inventory.dao.ProductStatusDetailTempDAO;
import inventory.model.ProductStatusDetailTemp;
import inventory.model.Paging;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductStatusDetailTempService {
    @Autowired
    private ProductStatusDetailTempDAO<ProductStatusDetailTemp> categoryDAO;
    @Autowired
//    private ProductInfoDAO<ProductInfo> productInfoDAO;
    private static final Logger log = Logger.getLogger(ProductStatusDetailTempService.class);
    public void saveProductStatusDetailTemp(ProductStatusDetailTemp category)  throws Exception{
        log.info("Insert category "+category.toString());
        category.setActiveFlag(1);
        categoryDAO.save(category);
    }

    public void updateProductStatusDetailTemp(ProductStatusDetailTemp category) throws Exception {
        log.info("Update category "+category.toString());
        categoryDAO.update(category);
    }
    public void deleteProductStatusDetailTemp(ProductStatusDetailTemp category) throws Exception{
        category.setActiveFlag(0);
        log.info("Delete category "+category.toString());
        categoryDAO.deleteDone(category);
    }
    public List<ProductStatusDetailTemp> findProductStatusDetailTemp(String property , Object value){
        log.info("=====Find by property category start====");
        log.info("property ="+property +" value"+ value.toString());
        return categoryDAO.findByProperty(property, value);
    }

    public List<ProductStatusDetailTemp> getAllProductStatusDetailTemp(ProductStatusDetailTemp category, Paging paging){
        log.info("show all category");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(category!=null) {
            if(category.getId()!=null && category.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", category.getId());
            }
        }
        return categoryDAO.findAll(queryStr.toString(), mapParams,paging);
    }

    public ProductStatusDetailTemp findByIdProductStatusDetailTemp(int id) {
        log.info("find category by id ="+id);
        return categoryDAO.findById(ProductStatusDetailTemp.class, id);
    }
}
