package inventory.service;

import inventory.dao.IssueDAO;
import inventory.model.Issue;
import inventory.model.IssueDetail;
import inventory.model.Paging;
import inventory.model.Issue;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueService {
    @Autowired
    private IssueDAO<Issue> issueDAO;

    @Autowired
    private IssueDetailService issueDetailService;

    private static final Logger log = Logger.getLogger(IssueService.class);

    //  service
    public void saveIssue(Issue issue) throws SQLException {
        log.info("Insert Issue "+issue.toString());
//        if(issue.getTax()!=null && !StringUtils.isEmpty(issue.getTax()))
//        {
//            issue.setTax("123456789");
//        }
        issue.setPrice(BigDecimal.valueOf(0));
        issue.setActiveFlag(1);
        issue.setCreateDate(new Date());
        issue.setUpdateDate(new Date());
        issueDAO.saveDTO(issue);
    }

    public void updateIssue(Issue issue) throws Exception {
        log.info("Update issue "+issue.toString());

        issue.setUpdateDate(new Date());
        issueDAO.updateDTO(issue);
    }
    public void deleteIssue(Issue issue) throws Exception{
        issue.setActiveFlag(0);
        issue.setUpdateDate(new Date());
        log.info("Delete Issue "+issue.toString());
        issueDAO.updateDTO(issue);


    }
    public List<Issue> findIssue(String property , Object value){
        log.info("=====Find by property Issue start====");
        log.info("property ="+property +" value"+ value.toString());
        return issueDAO.findByProperty(property, value);
    }
    public List<Issue>  getAllIssue(Issue issue, Paging paging){
        log.info("Show all Issue");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(issue!=null ) {
            if(issue.getId()!=null && issue.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", issue.getId());
            }
            if(issue.getCode()!=null && !StringUtils.isEmpty(issue.getCode())) {
                queryStr.append(" and model.code like :code");
                mapParams.put("code","%"+ issue.getCode()+"%");
            }

            if(issue.getFromDate()!=null) {
                queryStr.append(" and model.updateDate >= :fromDate");
                mapParams.put("fromDate", issue.getFromDate());
            }
            if(issue.getToDate()!=null) {
                queryStr.append(" and model.updateDate <= :toDate");
                mapParams.put("toDate", issue.getToDate());
            }
        }
        return issueDAO.findAll(queryStr.toString(), mapParams,paging);
    }

    public Issue findByIdIssue(int id) {
        log.info("find Issue by id ="+id);
        return issueDAO.findByIdDTO( id);
    }

}
