package inventory.service;

import inventory.dao.ProductInfoDAO;
import inventory.dao.VatDAO;
import inventory.dao.IssueDetailDAO;
import inventory.model.Paging;
import inventory.model.ProductInfo;
import inventory.model.Vat;
import inventory.model.IssueDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueDetailService {
    @Autowired
    private IssueDetailDAO<IssueDetail> vatDetailDAO;
    
    @Autowired
    private ProductInfoDAO<ProductInfo> productInfoDAO;
    
    @Autowired
    private VatDAO<Vat> vatDao;

    private static final Logger log = Logger.getLogger(IssueDetailService.class);

    public void saveIssueDetail(IssueDetail vatDetail){
        log.info("Insert IssueDetail "+vatDetail.toString());
        vatDetail.setPriceTotal(BigDecimal.valueOf(vatDetail.getQty()).multiply(vatDetail.getPriceOne()));
       vatDetail.setActiveFlag(1);
        vatDetailDAO.saveDTO(vatDetail);
    }

    public void updateIssueDetail(IssueDetail vatDetail) throws Exception {
        log.info("Update IssueDetail "+vatDetail.toString());
        vatDetail.setPriceTotal(BigDecimal.valueOf(vatDetail.getQty()).multiply(vatDetail.getPriceOne()));
        vatDetail.setActiveFlag(1);
        vatDetailDAO.updateDTO(vatDetail);
    }

    public void deleteIssueDetail(IssueDetail vatDetail) throws Exception{

        log.info("Delete IssueDetail "+vatDetail.toString());
        vatDetail.setActiveFlag(0);
        vatDetailDAO.updateDTO(vatDetail);
    }

    public List<IssueDetail> findIssueDetail(String property , Object value){
        log.info("=====Find by property IssueDetail start====");
        log.info("property ="+property +" value"+ value.toString());
        return vatDetailDAO.findByProperty(property, value);
    }

    public List<IssueDetail> getAllIssueDetail(IssueDetail issueDetail, Paging paging){
        log.info("Show all IssueDetail");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(issueDetail!=null && issueDetail.getProductInfo()!=null && issueDetail.getIssue()!=null) {
            if(issueDetail.getId()!=null && issueDetail.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", issueDetail.getId());
            }

            if(issueDetail.getIssue().getCode()!=null && !StringUtils.isEmpty(issueDetail.getIssue().getCode())) {
                queryStr.append(" and model.vat.code=:code");
                mapParams.put("code", issueDetail.getIssue().getCode());
            }

            if(issueDetail.getIssue().getId()!=null ) {
                queryStr.append(" and model.vat.id=:id");
                mapParams.put("id", issueDetail.getIssue().getId());
            }

            if(issueDetail.getFromPriceOne()!=null) {
                queryStr.append(" and model.priceOne >= :fromPriceOne");
                mapParams.put("fromPriceOne", issueDetail.getFromPriceOne());
            }

            if(issueDetail.getToPriceOne()!=null) {
                queryStr.append(" and model.priceOne <= :toPriceOne");
                mapParams.put("toPriceOne", issueDetail.getToPriceOne());
            }

            if(issueDetail.getFromPriceTotal()!=null) {
                queryStr.append(" and model.priceTotal >= :fromPriceTotal");
                mapParams.put("fromPriceTotal", issueDetail.getFromPriceTotal());
            }

            if(issueDetail.getToPriceTotal()!=null) {
                queryStr.append(" and model.priceTotal <= :toPriceTotal");
                mapParams.put("toPriceTotal", issueDetail.getToPriceTotal());
            }

            if(issueDetail.getProductInfo().getName()!=null && !StringUtils.isEmpty(issueDetail.getProductInfo().getName()) ) {
                queryStr.append(" and model.productInfo.name like :name");
                mapParams.put("name", "%"+issueDetail.getProductInfo().getName()+"%");
            }

        }
        return vatDetailDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public IssueDetail findByIdIssueDetail(int id) {
        log.info("find IssueDetail by id ="+id);
        return vatDetailDAO.findById(IssueDetail.class, id);
    }

}
