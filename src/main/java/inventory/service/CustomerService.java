package inventory.service;

import inventory.dao.CustomerDAO;
import inventory.model.Paging;
import inventory.model.Customer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    private CustomerDAO<Customer> customerDAO;

    private static final Logger log = Logger.getLogger(CustomerService.class);
    public List<Customer> getAllCustomer(Customer customer, Paging paging){
        log.info("show all Customer");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(customer!=null) {
            if(customer.getId()!=null && customer.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", customer.getId());
            }
//            if(productInfo.getCode()!=null && !StringUtils.isEmpty(productInfo.getCode())) {
//                queryStr.append(" and model.code=:code");
//                mapParams.put("code", productInfo.getCode());
//            }
            if(customer.getName()!=null && !StringUtils.isEmpty(customer.getName()) ) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%"+customer.getName()+"%");
            }
            if(customer.getPhone()!=null && !StringUtils.isEmpty(customer.getPhone()) ) {
                queryStr.append(" and model.phone like :phone");
                mapParams.put("phone", "%"+customer.getPhone()+"%");
            }
        }
        return customerDAO.findAll(queryStr.toString(), mapParams,paging);
    }

    public void saveCustomer(Customer customer)  throws Exception{
        log.info("Insert customer "+customer.toString());
        customer.setActiveFlag(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        customerDAO.saveDTO(customer);
    }

    public void updateCustomer(Customer customer) throws Exception {
        log.info("Update customer "+customer.toString());
        customer.setUpdateDate(new Date());
        customerDAO.updateDTO(customer);
    }
    public void deleteCustomer(Customer customer) throws Exception{
        customer.setActiveFlag(0);
        customer.setUpdateDate(new Date());
        log.info("Delete customer "+customer.toString());
        customerDAO.updateDTO(customer);
    }
    public List<Customer> findCustomer(String property , Object value){
        log.info("=====Find by property customer start====");
        log.info("property ="+property +" value"+ value.toString());
        return customerDAO.findByProperty(property, value);
    }

    public Customer findByIdCustomer(int id) {
        log.info("find customer by id ="+id);
        return customerDAO.findById(Customer.class, id);
    }

}
