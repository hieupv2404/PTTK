package inventory.controller;

import inventory.model.*;
import inventory.service.*;
import inventory.util.Constant;
import inventory.validate.ProductInfoValidator;
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
public class ProductDetailPtController {
    @Autowired
    private ProductDetailPtService productDetailService;

    @Autowired
    private ProductInfoValidator productInfoValidator;

    @Autowired
    private VatService vatService;
    

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductStatusDetailService productStatusDetailService;

    @Autowired
    private ProductStatusListService productStatusListService;

    static final Logger log = Logger.getLogger(ProductDetailPtController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        
    }
    @RequestMapping(value= {"/product-detail-pt/list","/product-detail-pt/list/"})

    public String redirect() {
        return "redirect:/product-detail-pt/list/1";
    }

    @RequestMapping(value="/product-detail-pt/list/{page}")
    public String showProductInfoList(Model model,HttpSession session , @ModelAttribute("searchForm") ProductDetailPt productDetail,@PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductDetailPt> products = productDetailService.getAllProductDetailPt(productDetail,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", products);
        return "productDetailPt-list";

    }

    @RequestMapping(value="/product-detail-pt/getAll/{page}")
    public String getAll(Model model,HttpSession session , @ModelAttribute("searchForm") ProductDetailPt productDetail,@PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductDetailPt> products = productDetailService.getAllProductDetailPt(null,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", products);
        return "productDetailPt-list";

    }

    @GetMapping("/product-detail-pt/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Product Detail");
        model.addAttribute("modelForm", new ProductDetailPt());

        List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
        Map<String, String> mapProductInfo = new HashMap<>();
        for(ProductInfo productInfo : productInfos) {
            mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
        }

        List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
        Map<String, String> mapSupplier = new HashMap<>();
        for(Supplier supplier : suppliers) {
            mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
        }

        ProductStatusList productStatusListTemp = new ProductStatusList();
        productStatusListTemp.setType(Constant.PRODUCT_DONE);
        if (productStatusListTemp.getUser() == null)
        {
            productStatusListTemp.setUser(new Users());
        }
        if (productStatusListTemp.getVat() == null)
        {
            productStatusListTemp.setVat(new Vat());
        }
        List<ProductStatusList> invoices = productStatusListService.getAllProductStatusList(productStatusListTemp, null);
        Map<String, String> mapInvoice = new HashMap<>();
        for(ProductStatusList invoice : invoices) {
            mapInvoice.put(String.valueOf(invoice.getId()), invoice.getCode());
        }

        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapSupplier",mapSupplier);
        model.addAttribute("mapSupplier",mapSupplier);
        model.addAttribute("mapInvoice",mapInvoice);
        model.addAttribute("mapInvoice",mapInvoice);
        model.addAttribute("viewOnly", false);
        return "productDetailPt-action";
    }
    @GetMapping("/product-detail-pt/edit/{id}")
    public String edit(Model model , @PathVariable("id") int id) {
        log.info("Edit productDetail with id="+id);
        ProductDetailPt productDetail = productDetailService.findByIdProductDetailPt(id);
        if(productDetail!=null) {

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }
            productDetail.setProductInfoId(productDetail.getProductInfo().getId());

            List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Supplier supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
            }
            productDetail.setSupplierId(productDetail.getSupplier().getId());

            ProductStatusList productStatusListTemp = new ProductStatusList();
            productStatusListTemp.setType(Constant.PRODUCT_DONE);
            if (productStatusListTemp.getUser() == null)
            {
                productStatusListTemp.setUser(new Users());
            }
            if (productStatusListTemp.getVat() == null)
            {
                productStatusListTemp.setVat(new Vat());
            }
            List<ProductStatusList> invoices = productStatusListService.getAllProductStatusList(productStatusListTemp, null);
            Map<String, String> mapInvoice = new HashMap<>();
            for(ProductStatusList invoice : invoices) {
                mapInvoice.put(String.valueOf(invoice.getId()), invoice.getCode());
            }
            productDetail.setInvoiceId(productDetail.getProductStatusList().getId());

            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("mapInvoice",mapInvoice);
            model.addAttribute("titlePage", "Edit Product Detail");
            model.addAttribute("modelForm", productDetail);
            model.addAttribute("modelForm", productDetail);
            model.addAttribute("viewOnly", false);
            return "productDetailPt-action";
        }
        return "redirect:/product-detail-pt/list";
    }

    @GetMapping("/product-detail-pt/changeStatus/{id}")
    public String changeStatus(Model model , @PathVariable("id") int id) throws Exception {
        log.info("Change Status productDetail with id="+id);
        ProductDetailPt productDetail = productDetailService.findByIdProductDetailPt(id);
        if (productDetail.getStatus().equals("Valid"))
        {
            productDetail.setStatus("InValid");
        }
        else
        {
            productDetail.setStatus("Valid");
        }
        productDetailService.updateProductDetailPt(productDetail);
        return "redirect:/product-detail-pt/list";
    }
    @GetMapping("/product-detail-pt/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View productDetail with id="+id);
        ProductDetailPt productDetail = productDetailService.findByIdProductDetailPt(id);
        if(productDetail!=null) {
            model.addAttribute("titlePage", "View Product Detail");
            model.addAttribute("modelForm", productDetail);
            model.addAttribute("viewOnly", true);
            return "productDetailPt-action";
        }
        return "redirect:/product-detail-pt/list";
    }
    @PostMapping("/product-detail-pt/save") 
    public String save(Model model,@ModelAttribute("modelForm") @Validated ProductDetailPt productDetail,BindingResult result,HttpSession session) {
        if(result.hasErrors()) {
            if(productDetail.getId()!=null) {
                model.addAttribute("titlePage", "Edit Product Detail");
            }else {
                model.addAttribute("titlePage", "Add Product Detail");
            }

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }

            List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Supplier supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
            }

            ProductStatusList productStatusListTemp = new ProductStatusList();
            productStatusListTemp.setType(Constant.PRODUCT_DONE);
            if (productStatusListTemp.getUser() == null)
            {
                productStatusListTemp.setUser(new Users());
            }
            if (productStatusListTemp.getVat() == null)
            {
                productStatusListTemp.setVat(new Vat());
            }
            List<ProductStatusList> invoices = productStatusListService.getAllProductStatusList(productStatusListTemp, null);
            Map<String, String> mapInvoice = new HashMap<>();
            for(ProductStatusList invoice : invoices) {
                mapInvoice.put(String.valueOf(invoice.getId()), invoice.getCode());
            }

            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("mapInvoice",mapInvoice);
            model.addAttribute("modelForm", productDetail);
            model.addAttribute("viewOnly", false);
        }

        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(productDetail.getProductInfoId());
        productDetail.setProductInfo(productInfo);



        ProductStatusList invoice = productStatusListService.findByIdProductStatusList(productDetail.getInvoiceId());
//        invoice.setId(productDetail.getInvoiceId());
        productDetail.setProductStatusList(invoice);

        Vat vat = vatService.findByIdVat(invoice.getVat().getId());

        Supplier supplier = new Supplier();
        supplier.setId(vat.getSupplier().getId());
        productDetail.setSupplier(supplier);

        List<ProductStatusDetail> productStatusDetailList = productStatusDetailService.findProductStatusDetail("productStatusList.id",invoice.getId());
        ProductStatusDetail productStatusDetail = new ProductStatusDetail();
        for (ProductStatusDetail productStatusDetail1: productStatusDetailList)
        {
            if (productStatusDetail1.getProductInfo().getId() == productDetail.getProductInfo().getId())
            {
                productDetail.setPriceIn(productStatusDetail1.getPriceOne());
                break;
            }
        }



        if(productDetail.getId()!=null && productDetail.getId()!=0) {
            try {


                productDetailService.updateProductDetailPt(productDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        }else {
            try {

                productDetailService.saveProductDetailPt(productDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/product-detail-pt/list";

    }
    @GetMapping("/product-detail-pt/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete productDetail with id="+id);
        ProductDetailPt productDetail = productDetailService.findByIdProductDetailPt(id);
        if(productDetail!=null) {
            try {
                productDetailService.deleteProductDetailPt(productDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/product-detail-pt/list";
    }

}
