package inventory.controller;

import inventory.model.*;
import inventory.service.*;
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
import java.util.*;

@Controller
public class ProductDoneDetailController {
    @Autowired
    private ProductStatusDetailService productStatusDetailService;
    @Autowired
    private ProductStatusListService productStatusListService;
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private VatService vatService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private VatDetailService vatDetailService;

    @Autowired
    private ShelfService shelfService;

    static final Logger log = Logger.getLogger(ProductDoneDetailController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

    }

    @RequestMapping(value= {"/product-done-detail/list","/product-done-detail/list/"})

    public String redirect() {
        return "redirect:/product-done-detail/list/1";
    }

    @RequestMapping(value="/product-done-detail/list/{page}")
    public String showProductInfoList(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusDetail productStatusDetail, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
//        Vat vat = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        if (productStatusDetail.getProductStatusList() == null) {
            productStatusDetail.setProductStatusList(new ProductStatusList());
        }

        productStatusDetail.getProductStatusList().setType(Constant.PRODUCT_DONE);
        if (productStatusDetail.getProductInfo() == null)
        {
            productStatusDetail.setProductInfo(new ProductInfo());
        }
        List<ProductStatusDetail> productStatusDetails = productStatusDetailService.getAllProductStatusDetail(productStatusDetail,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", productStatusDetails);
        return "productDoneDetail-list";

    }

    @RequestMapping(value="/product-done-detail/getAll/{page}")
    public String getAll(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusDetail productStatusDetail, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
//        Vat vat = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        ProductStatusDetail productStatusDetail1 = new ProductStatusDetail();
        if (productStatusDetail1.getProductStatusList() == null) {
            productStatusDetail1.setProductStatusList(new ProductStatusList());
        }

        productStatusDetail1.getProductStatusList().setType(Constant.PRODUCT_DONE);
        if (productStatusDetail1.getProductInfo() == null)
        {
            productStatusDetail1.setProductInfo(new ProductInfo());
        }
        List<ProductStatusDetail> productStatusDetails = productStatusDetailService.getAllProductStatusDetail(productStatusDetail1,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", productStatusDetails);
        return "productDoneDetail-list";

    }

    @RequestMapping(value="/product-done-detail/code/{code}")
    public String showProductInfoListFilter(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusDetail productStatusDetail, @PathVariable("code") String code) {
        Paging paging = new Paging(5);

//        Vat vat = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        ProductStatusList productStatusList = productStatusListService.findProductStatusList("code",code).get(0);
        productStatusDetail.setProductStatusList(productStatusList);
        if (productStatusDetail.getProductStatusList() == null) {
            productStatusDetail.setProductStatusList(new ProductStatusList());
        }
        if (productStatusDetail.getProductInfo() == null) {
            productStatusDetail.setProductInfo(new ProductInfo());
        }
        productStatusDetail.getProductStatusList().setType(Constant.PRODUCT_DONE);
        List<ProductStatusDetail> productStatusDetails = productStatusDetailService.getAllProductStatusDetail(productStatusDetail,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", productStatusDetails);
        return "productDoneDetail-list";

    }
    @GetMapping("/product-done-detail/{productDoneListId}/add")
    public String add(Model model, @PathVariable("productDoneListId") int productDoneListId) {
        model.addAttribute("titlePage", "Add Product Done Detail");
//
        ProductStatusList productStatusListFind = productStatusListService.findByIdProductStatusList(productDoneListId);
//        vatDetail.setVat(vat);
        model.addAttribute("modelForm", new ProductStatusDetail());

        List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
        Map<String, String> mapProductInfo = new HashMap<>();
        for(ProductInfo productInfo : productInfos) {
            mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
        }

//        Map<String, String> mapVat = new HashMap<>();
//        mapVat.put(String.valueOf(vat.getId()), vat.getCode());
        List<ProductStatusList> productStatusLists = productStatusListService.getAllProductStatusList(productStatusListFind, null);
//        Collections.sort(productStatusLists,new UpdateDateCompatatorVat());
        Map<String, String> mapProductStatusList = new HashMap<>();
        for(ProductStatusList productStatusList : productStatusLists) {
            mapProductStatusList.put(String.valueOf(productStatusList.getId()), productStatusList.getCode());
        }

        List<Shelf> shelves = shelfService.getAllShelf(null, null);
        Map<String, String> mapShelf = new HashMap<>();
        for(Shelf shelf : shelves) {
            mapShelf.put(String.valueOf(shelf.getId()), shelf.getName());
        }

        model.addAttribute("mapProductStatusList",mapProductStatusList);
        model.addAttribute("mapProductStatusList",mapProductStatusList);
        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapShelf", mapShelf);
        model.addAttribute("mapShelf", mapShelf);

        model.addAttribute("viewOnly", false);
        return "productDoneDetail-action";
    }

    @GetMapping("/product-done-detail/edit/{id}")
    public String edit(Model model ,@PathVariable("id") int id) {
        log.info("Edit Product Done Detail with id="+id);
        ProductStatusDetail productStatusDetail = productStatusDetailService.findByIdProductStatusDetail(id);
        if(productStatusDetail!=null) {

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }
            productStatusDetail.setProductInfoId(productStatusDetail.getProductInfo().getId());

            List<ProductStatusList> productStatusLists = productStatusListService.getAllProductStatusList(null, null);
            Map<String, String> mapProductStatusList = new HashMap<>();
            for(ProductStatusList productStatusList : productStatusLists) {
                mapProductStatusList.put(String.valueOf(productStatusList.getId()), productStatusList.getCode());
            }
            productStatusDetail.setProductStatusListId(productStatusDetail.getProductStatusList().getId());

            List<Shelf> shelves = shelfService.getAllShelf(null, null);
            Map<String, String> mapShelf = new HashMap<>();
            for(Shelf shelf : shelves) {
                mapShelf.put(String.valueOf(shelf.getId()), shelf.getName());
            }
            productStatusDetail.setShelfId(productStatusDetail.getShelf().getId());



            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapShelf", mapShelf);
            model.addAttribute("mapProductStatusList", mapProductStatusList);
            model.addAttribute("titlePage", "Edit Product Done Detail");
            model.addAttribute("modelForm", productStatusDetail);
            model.addAttribute("viewOnly", false);
            return "productDoneDetail-action";
        }
        return "redirect:/product-done-detail/list";
    }

    @GetMapping("/product-done-detail/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View productDetail with id="+id);
        ProductStatusDetail productStatusDetail = productStatusDetailService.findByIdProductStatusDetail(id);
        if(productStatusDetail!=null) {
            model.addAttribute("titlePage", "View Product Done Detail");
            model.addAttribute("modelForm", productStatusDetail);
            model.addAttribute("viewOnly", true);
            return "productDoneDetail-action";
        }
        return "redirect:/product-done-detail/list";
    }

    @PostMapping("/product-done-detail/save")
    public String save(Model model,@ModelAttribute("modelForm") @Validated ProductStatusDetail productStatusDetail,BindingResult result,HttpSession session) {
        if(result.hasErrors()) {
            if(productStatusDetail.getId()!=null) {
                model.addAttribute("titlePage", "Edit Product Done Detail");
            }else {
                model.addAttribute("titlePage", "Add Product Done Detail");
            }

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }

            List<ProductStatusList> productStatusLists = productStatusListService.getAllProductStatusList(null, null);
//            Collections.sort(productStatusLists,new UpdateDateCompatatorVat());
            Map<String, String> mapProductStatusList = new HashMap<>();
            for(ProductStatusList productStatusList : productStatusLists) {
                mapProductStatusList.put(String.valueOf(productStatusList.getId()), productStatusList.getCode());
            }

//            List<Vat> vats = vatService.findVat("code",vatDetail.getVat().getCode());
//            Map<String, String> mapVat = new HashMap<>();
//            for (Vat vat : vats) {
//                mapVat.put(String.valueOf(vat.getId()), vat.getCode());
//            }
//            model.addAttribute("mapVat",vats);

            List<Shelf> shelves = shelfService.getAllShelf(null, null);
            Map<String, String> mapShelf = new HashMap<>();
            for(Shelf shelf : shelves) {
                mapShelf.put(String.valueOf(shelf.getId()), shelf.getName());
            }

            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapProductStatusList", mapProductStatusList);
            model.addAttribute("mapShelf", mapShelf);

            model.addAttribute("modelForm", productStatusDetail);
            model.addAttribute("viewOnly", false);
        }

        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(productStatusDetail.getProductInfoId());
        productStatusDetail.setProductInfo(productInfo);

        ProductStatusList productStatusList = new ProductStatusList();
        productStatusList.setId(productStatusDetail.getProductStatusListId());
        productStatusDetail.setProductStatusList(productStatusList);

        Shelf shelf = new Shelf();
        shelf.setId(productStatusDetail.getShelfId());
        productStatusDetail.setShelf(shelf);

        ProductStatusList productStatusList1 = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());
        Vat vat = vatService.findByIdVat(productStatusList1.getVat().getId());
        VatDetail vatDetail = new VatDetail();

        vatDetail.setVatId(vat.getId());

        Shelf shelf1 = shelfService.findByIdShelf(productStatusDetail.getShelf().getId());

        if(productStatusDetail.getId()!=null && productStatusDetail.getId()!=0) {
            try {

                ProductStatusDetail productStatusDetail1 = productStatusDetailService.findByIdProductStatusDetail(productStatusDetail.getId());
                int qtyTemp = productStatusDetail1.getQty() - productStatusDetail.getQty();
                shelf1.setQty(shelf1.getQty()-qtyTemp);
                shelfService.updateShelf(shelf1);
                List<VatDetail> vatDetailList = vatDetailService.getAllVatDetail(vatDetail,null);
                for (VatDetail vatDetail1 : vatDetailList)
                {
                    if (productStatusDetail.getProductInfo().getId() == vatDetail1.getProductInfo().getId())
                    {
                        productStatusDetail.setQtyRest(vatDetail1.getQty()- productStatusDetail.getQty());
                        productStatusDetail.setPriceOne(vatDetail1.getPriceOne());
                        break;
                    }

                }
                productStatusDetailService.updateProductStatusDetail(productStatusDetail);
//                ProductStatusList productStatusList1 = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());
                productStatusList1.setPrice(productStatusList1.getPrice().add(productStatusDetail.getPriceTotal()));

                productStatusListService.updateProductStatusList(productStatusList1);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        }else {
            try {

                shelf1.setQty(shelf1.getQty()+productStatusDetail.getQty());
                shelfService.updateShelf(shelf1);
                List<VatDetail> vatDetailList = vatDetailService.getAllVatDetail(vatDetail,null);
                for (VatDetail vatDetail1 : vatDetailList)
                {
                    if (productStatusDetail.getProductInfo().getId() == vatDetail1.getProductInfo().getId())
                    {
                        productStatusDetail.setQtyRest(vatDetail1.getQty()- productStatusDetail.getQty());
                        productStatusDetail.setPriceOne(vatDetail1.getPriceOne());
                        break;
                    }

                }
                productStatusDetailService.saveProductStatusDetail(productStatusDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
//                ProductStatusList productStatusList1 = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());
                productStatusList1.setPrice(productStatusList1.getPrice().add(productStatusDetail.getPriceTotal()));
                productStatusListService.updateProductStatusList(productStatusList1);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/product-done-detail/list";

    }
    @GetMapping("/product-done-detail/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete productStatusDetail with id="+id);
        ProductStatusDetail productStatusDetail = productStatusDetailService.findByIdProductStatusDetail(id);
        if(productStatusDetail!=null) {
            try {
                productStatusDetailService.deleteProductStatusDetail(productStatusDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
                ProductStatusList productStatusList = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());
                productStatusList.setPrice(productStatusList.getPrice().subtract(productStatusDetail.getPriceTotal()));
                productStatusListService.updateProductStatusList(productStatusList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/product-done-detail/list";
    }
}
