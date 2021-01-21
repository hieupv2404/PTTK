package inventory.controller;

import inventory.model.Paging;
import inventory.model.Customer;
import inventory.service.CustomerService;
import inventory.util.Constant;
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
import java.util.List;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;


	static final Logger log = Logger.getLogger(CustomerController.class);
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		
	}
	@RequestMapping(value= {"/customer/list","/customer/list/"})
	
	public String redirect() {
		return "redirect:/customer/list/1";
	}
	
	@RequestMapping(value="/customer/list/{page}")
	public String showCustomerList(Model model,HttpSession session , @ModelAttribute("searchForm") Customer customer,@PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Customer> categories = customerService.getAllCustomer(customer,paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("categories", categories);
		return "customer-list";
		
	}

	@RequestMapping(value="/customer/getAll/{page}")
	public String getAll(Model model, HttpSession session , @ModelAttribute("searchForm") Customer customer, @PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Customer> categories = customerService.getAllCustomer(null,paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("categories", categories);
		return "customer-list";

	}

	@GetMapping("/customer/add")
	public String add(Model model) {
		model.addAttribute("titlePage", "Add Customer");
		model.addAttribute("modelForm", new Customer());
		model.addAttribute("viewOnly", false);
		return "customer-action";
	}
	@GetMapping("/customer/edit/{id}")
	public String edit(Model model , @PathVariable("id") int id) {
		log.info("Edit customer with id="+id);
		Customer customer = customerService.findByIdCustomer(id);
		if(customer!=null) {
			model.addAttribute("titlePage", "Edit Customer");
			model.addAttribute("modelForm", customer);
			model.addAttribute("viewOnly", false);
			return "customer-action";
		}
		return "redirect:/customer/list";
	}

	@GetMapping("/customer/view/{id}")
	public String view(Model model , @PathVariable("id") int id) {
		log.info("View customer with id="+id);
		Customer customer = customerService.findByIdCustomer(id);
		if(customer!=null) {
			model.addAttribute("titlePage", "View Customer");
			model.addAttribute("modelForm", customer);
			model.addAttribute("viewOnly", true);
			return "customer-action";
		}
		return "redirect:/customer/list";
	}
	@PostMapping("/customer/save")
	public String save(Model model,@ModelAttribute("modelForm") @Validated Customer customer,BindingResult result,HttpSession session) {
		if(result.hasErrors()) {
			if(customer.getId()!=null) {
				model.addAttribute("titlePage", "Edit Customer");
			}else {
				model.addAttribute("titlePage", "Add Customer");
			}
			
			model.addAttribute("modelForm", customer);
			model.addAttribute("viewOnly", false);
			return "customer-action";
			
		}
		if(customer.getId()!=null && customer.getId()!=0) {
			try {
				customerService.updateCustomer(customer);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			
		}else {
				try {
					customerService.saveCustomer(customer);
					session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
				}
		}
		return "redirect:/customer/list";
		
	}
	@GetMapping("/customer/delete/{id}")
	public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
		log.info("Delete customer with id="+id);
		Customer customer = customerService.findByIdCustomer(id);
		if(customer!=null) {
			try {
				customerService.deleteCustomer(customer);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
			}
		}
		return "redirect:/customer/list";
	}
	
}
