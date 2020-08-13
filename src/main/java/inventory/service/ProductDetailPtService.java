package inventory.service;

import inventory.dao.*;
import inventory.model.*;
import inventory.util.ConfigLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductDetailPtService {

    @Autowired
    private ProductInfoDAO<ProductInfo> productInfoDAO;

    @Autowired
    private CategoryDAO<Category> categoryDAO;

    @Autowired
    private SupplierDAO<Supplier> supplierDAO;

  @Autowired
  private ProductDetailPtDAO<ProductDetailPt> productDetailPtDAO;
    
    @Autowired
    private ProductStatusListDAO<ProductStatusList> productStatusListDAO;

    
    
    

    private static final Logger log = Logger.getLogger(ProductDetailPtService.class);

    // PRODUCT Deatil

    public void saveProductDetailPt(ProductDetailPt productDetailPt){
        log.info("Insert productDetailPt "+productDetailPt.toString());
        productDetailPt.setActiveFlag(1);
        productDetailPt.setStatus("Valid");
        productDetailPt.setCreateDate(new Date());
        productDetailPt.setUpdateDate(new Date());

        productDetailPtDAO.save(productDetailPt);
    }

    public void updateProductDetailPt(ProductDetailPt productDetailPt) throws Exception {
        log.info("Update productDetailPt "+productDetailPt.toString());

        productDetailPt.setUpdateDate(new Date());
        productDetailPtDAO.update(productDetailPt);
    }
    public void deleteProductDetailPt(ProductDetailPt productDetailPt) throws Exception{
        productDetailPt.setActiveFlag(0);
        productDetailPt.setUpdateDate(new Date());
        log.info("Delete productDetailPt "+productDetailPt.toString());
        productDetailPtDAO.update(productDetailPt);
    }
    public List<ProductDetailPt> findProductDetailPt(String property , Object value){
        log.info("=====Find by property productDetailPt start====");
        log.info("property ="+property +" value"+ value.toString());
        return productDetailPtDAO.findByProperty(property, value);
    }
    public List<ProductDetailPt> getAllProductDetailPt(ProductDetailPt productDetailPt, Paging paging){
        log.info("Show all productDetailPt");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(productDetailPt!=null && productDetailPt.getProductInfo()!=null && productDetailPt.getSupplier()!=null) {
            if(productDetailPt.getId()!=null && productDetailPt.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", productDetailPt.getId());
            }
            if(productDetailPt.getCode()!=null && !StringUtils.isEmpty(productDetailPt.getCode())) {
                queryStr.append(" and model.code like :code");
                mapParams.put("code","%"+ productDetailPt.getCode()+"%");
            }
            if(productDetailPt.getStatus()!=null && !StringUtils.isEmpty(productDetailPt.getStatus())) {
                queryStr.append(" and model.status like :status");
                mapParams.put("status",productDetailPt.getStatus()+"%");
            }
            if(productDetailPt.getProductInfo().getName()!=null && !StringUtils.isEmpty(productDetailPt.getProductInfo().getName()) ) {
                queryStr.append(" and model.productInfo.name like :name");
                mapParams.put("name", "%"+productDetailPt.getProductInfo().getName()+"%");
            }
            if(productDetailPt.getSupplier().getName()!=null && !StringUtils.isEmpty(productDetailPt.getSupplier().getName()) ) {
                queryStr.append(" and model.supplier.name like :supplierName");
                mapParams.put("supplierName", "%"+productDetailPt.getSupplier().getName()+"%");
            }

        }
        return productDetailPtDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public ProductDetailPt findByIdProductDetailPt(int id) {
        log.info("find productDetailPt by id ="+id);
        return productDetailPtDAO.findById(ProductDetailPt.class, id);
    }

    // Product Info Service
    public void saveProductInfo(ProductInfo productInfo)  throws Exception{
        log.info("Insert productInfo "+productInfo.toString());
        productInfo.setActiveFlag(1);
        productInfo.setCreateDate(new Date());
        productInfo.setUpdateDate(new Date());

        String fileName = System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename();
        processUploadFile(productInfo.getMultipartFile(),fileName);
        productInfo.setImgUrl("/upload/"+fileName);
        productInfoDAO.save(productInfo);
    }
    public void updateProductInfo(ProductInfo productInfo) throws Exception {
        log.info("Update productInfo "+productInfo.toString());


        if(!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {

            String fileName =  System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename();
            processUploadFile(productInfo.getMultipartFile(),fileName);
            productInfo.setImgUrl("/upload/"+fileName);
        }
        productInfo.setUpdateDate(new Date());
        productInfoDAO.update(productInfo);
    }
    public void deleteProductInfo(ProductInfo productInfo) throws Exception{
        productInfo.setActiveFlag(0);
        productInfo.setUpdateDate(new Date());
        log.info("Delete productInfo "+productInfo.toString());
        productInfoDAO.update(productInfo);
    }
    public List<ProductInfo> findProductInfo(String property , Object value){
        log.info("=====Find by property productInfo start====");
        log.info("property ="+property +" value"+ value.toString());
        return productInfoDAO.findByProperty(property, value);
    }
    public List<ProductInfo> getAllProductInfo(ProductInfo productInfo,Paging paging){
        log.info("show all productInfo");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(productInfo!=null) {
            if(productInfo.getId()!=null && productInfo.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", productInfo.getId());
            }
//            if(productInfo.getCode()!=null && !StringUtils.isEmpty(productInfo.getCode())) {
//                queryStr.append(" and model.code=:code");
//                mapParams.put("code", productInfo.getCode());
//            }
            if(productInfo.getName()!=null && !StringUtils.isEmpty(productInfo.getName()) ) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%"+productInfo.getName()+"%");
            }
        }
        return productInfoDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public ProductInfo findByIdProductInfo(int id) {
        log.info("find productInfo by id ="+id);
        return productInfoDAO.findById(ProductInfo.class, id);
    }
    private void processUploadFile(MultipartFile multipartFile,String fileName) throws IllegalStateException, IOException {
        if(!multipartFile.getOriginalFilename().isEmpty()) {
            File dir = new File(ConfigLoader.getInstance().getValue("upload.location"));
            if(!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(ConfigLoader.getInstance().getValue("upload.location"),fileName);
            multipartFile.transferTo(file);
        }
    }

    public List<Supplier> getAllSupplier(Supplier supplier, Paging paging){
        log.info("show all Supplier");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(supplier!=null) {
            if(supplier.getId()!=null && supplier.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", supplier.getId());
            }
//            if(productInfo.getCode()!=null && !StringUtils.isEmpty(productInfo.getCode())) {
//                queryStr.append(" and model.code=:code");
//                mapParams.put("code", productInfo.getCode());
//            }
            if(supplier.getName()!=null && !StringUtils.isEmpty(supplier.getName()) ) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%"+supplier.getName()+"%");
            }
        }
        return supplierDAO.findAll(queryStr.toString(), mapParams,paging);
    }



}
