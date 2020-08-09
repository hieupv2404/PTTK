package inventory.service;

import inventory.dao.VatDetailTempDAO;
import inventory.model.VatDetailTemp;
import inventory.model.Paging;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VatDetailTempService {
    @Autowired
    private VatDetailTempDAO<VatDetailTemp> categoryDAO;
    @Autowired
//    private ProductInfoDAO<ProductInfo> productInfoDAO;
    private static final Logger log = Logger.getLogger(VatDetailTempService.class);
    public void saveVatDetailTemp(VatDetailTemp category)  throws Exception{
        log.info("Insert category "+category.toString());
        category.setActiveFlag(1);
        categoryDAO.save(category);
    }

    public void updateVatDetailTemp(VatDetailTemp category) throws Exception {
        log.info("Update category "+category.toString());
        categoryDAO.update(category);
    }
    public void deleteVatDetailTemp(VatDetailTemp category) throws Exception{
        category.setActiveFlag(0);
        log.info("Delete category "+category.toString());
        categoryDAO.deleteDone(category);
    }
    public List<VatDetailTemp> findVatDetailTemp(String property , Object value){
        log.info("=====Find by property category start====");
        log.info("property ="+property +" value"+ value.toString());
        return categoryDAO.findByProperty(property, value);
    }

    public List<VatDetailTemp> getAllVatDetailTemp(VatDetailTemp category, Paging paging){
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

    public VatDetailTemp findByIdVatDetailTemp(int id) {
        log.info("find category by id ="+id);
        return categoryDAO.findById(VatDetailTemp.class, id);
    }
}
