package inventory.controller;

import inventory.model.*;
import inventory.service.*;
import inventory.util.Constant;
import inventory.validate.IssueValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IssueController {
    @Autowired
    private IssueService issueService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private IssueValidator issueValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private IssueDetailService issueDetailService;

    @Autowired
    private CustomerService customerService;

    static final Logger log = Logger.getLogger(IssueController.class);

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if(binder.getTarget().getClass()== Issue.class) {
            binder.setValidator(issueValidator);
        }
    }

    @RequestMapping(value= {"/issue/list","/issue/list/"})

    public String redirect() {
        return "redirect:/issue/list/1";
    }

    @RequestMapping(value= "/issue/list/{page}")
    public String showIssue(Model model, HttpSession session , @ModelAttribute("searchForm") Issue issue, @PathVariable("page") int page) throws Exception {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<Issue> issues = issueService.getAllIssue(issue,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }

        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", issues);
        return "issue-list";

    }

    @RequestMapping(value= "/issue/getAll/{page}")
    public String getAll(Model model, HttpSession session , @ModelAttribute("searchForm") Issue issue, @PathVariable("page") int page) throws Exception {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<Issue> issues = issueService.getAllIssue(null,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
//        for (Issue issue1:issues)
//        {
//            List<IssueDetail> issueDetailList = issueDetailService.findIssueDetail("issue.id",issue1.getId());
//            for (IssueDetail vatDetail:issueDetailList){
//                issue1.setPrice(issue1.getPrice().add(vatDetail.getPriceOne().multiply(BigDecimal.valueOf(vatDetail.getQty()))));
//            }
//            issue1.setTotal(issue1.getPrice().add(issue1.getPrice().multiply(issue1.getPercent())));
//            issue1.setCustomerId(issue1.getCustomer().getId());
//            issue1.setUserId(issue1.getUser().getId());
//            issueService.updateIssue(issue1);
//        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", issues);
        return "issue-list";
    }

    @GetMapping("/issue/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Issue");
        model.addAttribute("modelForm", new Issue());

        List<Customer> customers = customerService.getAllCustomer(null, null);
        Map<String, String> mapCustomer = new HashMap<>();
        for(Customer customer : customers) {
            mapCustomer.put(String.valueOf(customer.getId()), customer.getName());
        }

        model.addAttribute("mapCustomer",mapCustomer);
        model.addAttribute("viewOnly", false);
        return "issue-action";
    }
    @GetMapping("/issue/edit/{id}")
    public String edit(Model model , @PathVariable("id") int id) {
        log.info("Edit issue with id="+id);
        Issue issue = issueService.findByIdIssue(id);
        if(issue!=null) {

            List<Customer> customers = customerService.getAllCustomer(null, null);
            Map<String, String> mapCustomer = new HashMap<>();
            for(Customer customer : customers) {
                mapCustomer.put(String.valueOf(customer.getId()), customer.getName());
            }
            issue.setCustomerId(issue.getCustomerId());

            model.addAttribute("titlePage", "Edit Issue");
            model.addAttribute("mapCustomer", mapCustomer);
            model.addAttribute("modelForm", issue);
            model.addAttribute("viewOnly", false);
            return "issue-action";
        }
        return "redirect:/issue/list";
    }
    @GetMapping("/issue/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View vat with id="+id);
        Issue vat = issueService.findByIdIssue(id);
        if(vat!=null) {
            model.addAttribute("titlePage", "View Issue");
            model.addAttribute("modelForm", vat);
            model.addAttribute("viewOnly", true);
            return "issue-action";
        }
        return "redirect:/issue/list";
    }
    @PostMapping("/issue/save")
    public String save(Model model, @ModelAttribute("modelForm")  @Validated Issue vat, BindingResult result, HttpSession session) {
        if(result.hasErrors())
        {
            if(vat.getId()!=null) {
                model.addAttribute("titlePage", "Edit Issue");
            }else {
                model.addAttribute("titlePage", "Add Issue");
            }

            List<Customer> customers = customerService.getAllCustomer(null, null);
            Map<String, String> mapCustomer = new HashMap<>();
            for(Customer customer : customers) {
                mapCustomer.put(String.valueOf(customer.getId()), customer.getName());
            }

            model.addAttribute("mapCustomer", mapCustomer);
            model.addAttribute("modelForm", vat);
            model.addAttribute("viewOnly", false);
        }

        Customer supplier = new Customer();
        supplier.setId(vat.getCustomerId());
        vat.setCustomer(supplier);

        Users user = userService.findByProperty("status",1).get(0);
        vat.setUser(user);
        vat.setUserId(user.getId());

        if(vat.getId()!=null && vat.getId()!=0 ) {
            try {
                issueService.updateIssue(vat);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }
        }
        else {
            try {
                issueService.saveIssue(vat);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/issue/list";

    }
    @GetMapping("/issue/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete productInfo with id="+id);
        Issue vat = issueService.findByIdIssue(id);
        if(vat!=null) {
            try {
                issueService.deleteIssue(vat);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/issue/list";
    }
}
