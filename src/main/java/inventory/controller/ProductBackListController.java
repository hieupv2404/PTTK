package inventory.controller;

import inventory.model.*;
import inventory.service.ProductDetailService;
import inventory.service.ProductStatusListService;
import inventory.service.UserService;
import inventory.service.VatService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductBackListController {
    @Autowired
    private ProductStatusListService productStatusListService;

    @Autowired
    private ProductDetailService productDetailService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private VatService vatService;

    static final Logger log = Logger.getLogger(ProductBackListController.class);

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//        if(binder.getTarget().getClass()== Vat.class) {
//            binder.setValidator(vatValidator);
//        }
    }

    @RequestMapping(value= {"/product-back-list/list","/product-back-list/list/"})

    public String redirect() {
        return "redirect:/product-back-list/list/1";
    }

    @RequestMapping(value= "/product-back-list/list/{page}")
    public String showProductDone(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusList productStatusList, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);

        productStatusList.setType(Constant.PRODUCT_BACK);

        if (productStatusList.getVat() == null)
        {
            productStatusList.setVat(new Vat());
        }
        if (productStatusList.getUser() == null)
        {
            productStatusList.setUser(new Users());
        }
        List<ProductStatusList> productStatusLists = productStatusListService.getAllProductStatusList(productStatusList,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", productStatusLists);
        return "product-back-list-list";

    }

    @RequestMapping(value= "/product-back-list/getAll/{page}")
    public String getAll(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusList productStatusList, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        ProductStatusList productStatusList1 = new ProductStatusList();
        productStatusList1.setType(Constant.PRODUCT_BACK);

        if (productStatusList1.getVat() == null)
        {
            productStatusList1.setVat(new Vat());
        }
        if (productStatusList1.getUser() == null)
        {
            productStatusList1.setUser(new Users());
        }
        List<ProductStatusList> productStatusLists = productStatusListService.getAllProductStatusList(productStatusList1,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", productStatusLists);
        return "product-back-list-list";

    }
    @GetMapping("/product-back-list/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Product Status");
        model.addAttribute("modelForm", new ProductStatusList());

        List<Vat> vats = vatService.getAllVat(null,null);
        Map<String, String> mapSupplier = new HashMap<>();
        for(Vat supplier : vats) {
            mapSupplier.put(String.valueOf(supplier.getId()), supplier.getCode());
        }



        model.addAttribute("mapSupplier",mapSupplier);
        model.addAttribute("viewOnly", false);
        return "product-back-list-action";
    }
    @GetMapping("/product-back-list/edit/{id}")
    public String edit(Model model , @PathVariable("id") int id) {
        log.info("Edit ProductStatusList with id="+id);
        ProductStatusList productStatusList = productStatusListService.findByIdProductStatusList(id);
        if(productStatusList!=null) {

            List<Vat> suppliers = vatService.getAllVat(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Vat supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getCode());
            }
            productStatusList.setVatId(productStatusList.getVat().getId());

            model.addAttribute("titlePage", "Edit Product Back List");
            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("modelForm", productStatusList);
            model.addAttribute("viewOnly", false);
            return "product-back-list-action";
        }
        return "redirect:/product-back-list/list";
    }
    @GetMapping("/product-back-list/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View ProductStatusList with id="+id);
        ProductStatusList productStatusList = productStatusListService.findByIdProductStatusList(id);
        if(productStatusList!=null) {
            model.addAttribute("titlePage", "View Product Back List");
            model.addAttribute("modelForm", productStatusList);
            model.addAttribute("viewOnly", true);
            return "product-back-list-action";
        }
        return "redirect:/product-back-list/list";
    }
    @PostMapping("/product-back-list/save")
    public String save(Model model, @ModelAttribute("modelForm")  @Validated ProductStatusList productStatusList, BindingResult result, HttpSession session) {
        if(result.hasErrors())
        {
            if(productStatusList.getId()!=null) {
                model.addAttribute("titlePage", "Edit Product Back List");
            }else {
                model.addAttribute("titlePage", "Add Product Back List");
            }

            List<Vat> suppliers = vatService.getAllVat(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Vat supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getCode());
            }



            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("modelForm", productStatusList);
            model.addAttribute("viewOnly", false);
        }

        Vat supplier = new Vat();
        supplier.setId(productStatusList.getVatId());
        productStatusList.setVat(supplier);

        Users user = userService.findByProperty("status",1).get(0);
        productStatusList.setUser(user);
        productStatusList.setUserId(user.getId());

        productStatusList.setType(Constant.PRODUCT_BACK);

        if(productStatusList.getId()!=null && productStatusList.getId()!=0 ) {
            try {
                productStatusListService.updateProductStatusList(productStatusList);
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
                productStatusListService.saveProductStatusList(productStatusList);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/product-back-list/list";

    }
    @GetMapping("/product-back-list/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete ProductStatusList with id="+id);
        ProductStatusList productStatusList = productStatusListService.findByIdProductStatusList(id);
        if(productStatusList!=null) {
            try {
                productStatusListService.deleteProductStatusList(productStatusList);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/product-back-list/list";
    }
}
