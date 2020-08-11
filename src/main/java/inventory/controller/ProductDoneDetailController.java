package inventory.controller;

import inventory.model.*;
import inventory.service.*;
import inventory.util.Constant;
import org.apache.commons.math3.analysis.function.Abs;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
    private ProductStatusDetailTempService productStatusDetailTempService;

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
    public String showProductInfoList(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusDetail productStatusDetail, @PathVariable("page") int page) throws Exception {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
//        Vat vat = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        List<ProductStatusDetailTemp> productStatusDetailTempList = productStatusDetailTempService.findProductStatusDetailTemp("activeFlag",1);
        for (ProductStatusDetailTemp productStatusDetailTemp:productStatusDetailTempList)
        {
            productStatusDetailTempService.deleteProductStatusDetailTemp(productStatusDetailTemp);
        }
        if (productStatusDetail.getProductStatusList() == null) {
            productStatusDetail.setProductStatusList(new ProductStatusList());
        }
        if (productStatusDetail.getShelf() == null) {
            productStatusDetail.setShelf(new Shelf());
        }

        productStatusDetail.getProductStatusList().setType(Constant.PRODUCT_DONE);
        if (productStatusDetail.getProductInfo() == null)
        {
            productStatusDetail.setProductInfo(new ProductInfo());
        }
        List<ProductStatusDetail> productStatusDetails = productStatusDetailService.getAllProductStatusDetail(productStatusDetail,paging);
        int totalQty = 0;
        BigDecimal totalPriceOne = new BigDecimal(0);
        BigDecimal totalPriceTotal = new BigDecimal(0);
        for (ProductStatusDetail productStatusDetail1:productStatusDetails)
        {
            ProductStatusDetailTemp productStatusDetailTemp = new ProductStatusDetailTemp();
            productStatusDetailTemp.setProductName(productStatusDetail1.getProductInfo().getName());
            productStatusDetailTemp.setProductStatusName(productStatusDetail1.getProductStatusList().getCode());
            productStatusDetailTemp.setQty(productStatusDetail1.getQty());
            productStatusDetailTemp.setPriceOne(productStatusDetail1.getPriceOne());
            productStatusDetailTemp.setPriceTotal(productStatusDetail1.getPriceTotal());
            productStatusDetailTemp.setShelfName(productStatusDetail1.getShelf().getName());
            productStatusDetailTempService.saveProductStatusDetailTemp(productStatusDetailTemp);
            totalQty+=productStatusDetail1.getQty();
            totalPriceOne = totalPriceOne.add(productStatusDetail1.getPriceOne());
            totalPriceTotal = totalPriceTotal.add(productStatusDetail1.getPriceTotal());

        }


        model.addAttribute("totalQty",totalQty);
        model.addAttribute("totalPriceOne",totalPriceOne);
        model.addAttribute("totalPriceTotal",totalPriceTotal);
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
    public String getAll(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusDetail productStatusDetail, @PathVariable("page") int page) throws Exception {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
//        Vat vat = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        List<ProductStatusDetailTemp> productStatusDetailTempList = productStatusDetailTempService.findProductStatusDetailTemp("activeFlag",1);
        for (ProductStatusDetailTemp productStatusDetailTemp:productStatusDetailTempList)
        {
            productStatusDetailTempService.deleteProductStatusDetailTemp(productStatusDetailTemp);
        }
        ProductStatusDetail productStatusDetail1 = new ProductStatusDetail();
        if (productStatusDetail1.getProductStatusList() == null) {
            productStatusDetail1.setProductStatusList(new ProductStatusList());
        }

        if (productStatusDetail1.getShelf() == null) {
            productStatusDetail1.setShelf(new Shelf());
        }
        productStatusDetail1.getProductStatusList().setType(Constant.PRODUCT_DONE);
        if (productStatusDetail1.getProductInfo() == null)
        {
            productStatusDetail1.setProductInfo(new ProductInfo());
        }
        List<ProductStatusDetail> productStatusDetails = productStatusDetailService.getAllProductStatusDetail(productStatusDetail1,paging);
        int totalQty = 0;
        BigDecimal totalPriceOne = new BigDecimal(0);
        BigDecimal totalPriceTotal = new BigDecimal(0);
        for (ProductStatusDetail productStatusDetail2:productStatusDetails)
        {
            ProductStatusDetailTemp productStatusDetailTemp = new ProductStatusDetailTemp();
            productStatusDetailTemp.setProductName(productStatusDetail2.getProductInfo().getName());
            productStatusDetailTemp.setProductStatusName(productStatusDetail2.getProductStatusList().getCode());
            productStatusDetailTemp.setQty(productStatusDetail2.getQty());
            productStatusDetailTemp.setPriceOne(productStatusDetail2.getPriceOne());
            productStatusDetailTemp.setPriceTotal(productStatusDetail2.getPriceTotal());
            productStatusDetailTemp.setShelfName(productStatusDetail2.getShelf().getName());
            productStatusDetailTempService.saveProductStatusDetailTemp(productStatusDetailTemp);
            totalQty+=productStatusDetail2.getQty();
            totalPriceOne = totalPriceOne.add(productStatusDetail2.getPriceOne());
            totalPriceTotal = totalPriceTotal.add(productStatusDetail2.getPriceTotal());
        }
        model.addAttribute("totalQty",totalQty);
        model.addAttribute("totalPriceOne",totalPriceOne);
        model.addAttribute("totalPriceTotal",totalPriceTotal);
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
    public String showProductInfoListFilter(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusDetail productStatusDetail, @PathVariable("code") String code) throws Exception {
        Paging paging = new Paging(5);

//        Vat vat = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        List<ProductStatusDetailTemp> productStatusDetailTempList = productStatusDetailTempService.findProductStatusDetailTemp("activeFlag",1);
        for (ProductStatusDetailTemp productStatusDetailTemp:productStatusDetailTempList)
        {
            productStatusDetailTempService.deleteProductStatusDetailTemp(productStatusDetailTemp);
        }
        ProductStatusList productStatusList = productStatusListService.findProductStatusList("code",code).get(0);
        productStatusDetail.setProductStatusList(productStatusList);
        if (productStatusDetail.getProductStatusList() == null) {
            productStatusDetail.setProductStatusList(new ProductStatusList());
        }
        if (productStatusDetail.getShelf() == null) {
            productStatusDetail.setShelf(new Shelf());
        }
        if (productStatusDetail.getProductInfo() == null) {
            productStatusDetail.setProductInfo(new ProductInfo());
        }
        productStatusDetail.getProductStatusList().setType(Constant.PRODUCT_DONE);
        List<ProductStatusDetail> productStatusDetails = productStatusDetailService.getAllProductStatusDetail(productStatusDetail,paging);
        int totalQty = 0;
        BigDecimal totalPriceOne = new BigDecimal(0);
        BigDecimal totalPriceTotal = new BigDecimal(0);
        for (ProductStatusDetail productStatusDetail1:productStatusDetails)
        {
            ProductStatusDetailTemp productStatusDetailTemp = new ProductStatusDetailTemp();
            productStatusDetailTemp.setProductName(productStatusDetail1.getProductInfo().getName());
            productStatusDetailTemp.setProductStatusName(productStatusDetail1.getProductStatusList().getCode());
            productStatusDetailTemp.setQty(productStatusDetail1.getQty());
            productStatusDetailTemp.setPriceOne(productStatusDetail1.getPriceOne());
            productStatusDetailTemp.setPriceTotal(productStatusDetail1.getPriceTotal());
            productStatusDetailTemp.setShelfName(productStatusDetail1.getShelf().getName());
            productStatusDetailTempService.saveProductStatusDetailTemp(productStatusDetailTemp);
            totalQty+=productStatusDetail1.getQty();
            totalPriceOne = totalPriceOne.add(productStatusDetail1.getPriceOne());
            totalPriceTotal = totalPriceTotal.add(productStatusDetail1.getPriceTotal());
        }
        model.addAttribute("totalQty",totalQty);
        model.addAttribute("totalPriceOne",totalPriceOne);
        model.addAttribute("totalPriceTotal",totalPriceTotal);
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

       int checkPrice =0, checkQty =0;
        if (productStatusDetail.getQty() < 0)
        {
            productStatusDetail.setQty(Math.abs(productStatusDetail.getQty()));
            checkQty=1;
        }

        if(productStatusDetail.getId()!=null && productStatusDetail.getId()!=0) {
            try {

                ProductStatusDetail productStatusDetail1 = productStatusDetailService.findByIdProductStatusDetail(productStatusDetail.getId());


                int qtyTemp = productStatusDetail1.getQty() - productStatusDetail.getQty();


                List<VatDetail> vatDetailList = vatDetailService.getAllVatDetail(vatDetail,null);
                for (VatDetail vatDetail1 : vatDetailList)
                {
                    if (productStatusDetail.getProductInfo().getId() == vatDetail1.getProductInfo().getId())
                    {
                        productStatusDetail.setQtyRest(vatDetail1.getQty()- productStatusDetail.getQty());
                        productStatusDetail.setPriceOne(vatDetail1.getPriceOne());
                        if ( productStatusDetail.getQtyRest()<0) {
                            session.setAttribute(Constant.MSG_ERROR,"Update Error");
                            return "redirect:/product-done-detail/list";
                        }
                    }

                }
                productStatusList1.setPrice(productStatusList1.getPrice().subtract(productStatusDetail1.getPriceTotal()));

                productStatusDetailService.updateProductStatusDetail(productStatusDetail);
//                ProductStatusList productStatusList1 = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());
                productStatusList1.setPrice(productStatusList1.getPrice().add(productStatusDetail.getPriceTotal()));

                productStatusListService.updateProductStatusList(productStatusList1);

                shelf1.setQty(shelf1.getQty()-qtyTemp);
                shelfService.updateShelf(shelf1);
                if (checkQty==1) session.setAttribute(Constant.MSG_SUCCESS,"Qty has ABS-ed and Update success!!!");
                else if (checkPrice==1) session.setAttribute(Constant.MSG_SUCCESS,"Price has ABS-ed and Update success!!!");
                else if (checkPrice==1 && checkQty==1) session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS and Price has ABS and Update success!!!");
                else session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        }else {
            try {





                List<VatDetail> vatDetailList = vatDetailService.getAllVatDetail(vatDetail,null);
                int checkProductVat = 0;
                for (VatDetail vatDetail1 : vatDetailList)
                {


                    if (productStatusDetail.getProductInfo().getId() == vatDetail1.getProductInfo().getId())
                    {
                        productStatusDetail.setQtyRest(vatDetail1.getQty()- productStatusDetail.getQty());
                        productStatusDetail.setPriceOne(vatDetail1.getPriceOne());
                        checkProductVat = 1;
                        if ( productStatusDetail.getQtyRest()<0) {
                            session.setAttribute(Constant.MSG_ERROR,"Insert Error");
                            return "redirect:/product-done-detail/list";
                        }
                    }


                }
                if (checkProductVat == 0)
                {
                    session.setAttribute(Constant.MSG_ERROR,"Insert Error");
                    return "redirect:/product-done-detail/list";
                }
                List<ProductStatusDetail> productStatusDetailList = productStatusDetailService.findProductStatusDetail("productStatusList.id",productStatusDetail.getProductStatusList().getId());
                int updateCheck =0;
                for ( ProductStatusDetail productStatusDetail1 : productStatusDetailList)
                {
                    if (productStatusDetail1.getProductInfo().getId() == productStatusDetail.getProductInfo().getId())
                    {
                        productStatusDetail1.setQty(productStatusDetail1.getQty()+productStatusDetail.getQty());
                        productStatusDetail1.setQtyRest(productStatusDetail1.getQtyRest()-productStatusDetail.getQty());

                        for (VatDetail vatDetail1 : vatDetailList)
                        {
                            if (productStatusDetail1.getProductInfo().getId() == vatDetail1.getProductInfo().getId())
                            {
                               int test = vatDetail1.getQty()- productStatusDetail1.getQty();
                                if ( test <0) {
                                    session.setAttribute(Constant.MSG_ERROR,"Insert Error");
                                    return "redirect:/product-done-detail/list";

                                }
                                break;
                            }
                        }
                        productStatusList1.setPrice(productStatusList1.getPrice().subtract(productStatusDetail1.getPriceTotal()));

                        productStatusDetailService.updateProductStatusDetail(productStatusDetail1);
                        if (checkQty==1) session.setAttribute(Constant.MSG_SUCCESS,"Qty has ABS-ed and Insert success!!!");
                        else if (checkPrice==1) session.setAttribute(Constant.MSG_SUCCESS,"Price has ABS-ed and Insert success!!!");
                        else if (checkPrice==1 && checkQty==1) session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS and Price has ABS and Insert success!!!");
                        else session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
                        shelf1.setQty(shelf1.getQty()+productStatusDetail.getQty());
                        shelfService.updateShelf(shelf1);
                        productStatusList1.setPrice(productStatusList1.getPrice().add(productStatusDetail1.getPriceTotal()));
                        productStatusListService.updateProductStatusList(productStatusList1);
                        return "redirect:/product-done-detail/list";


                    }
                }
                if (updateCheck ==0) {
                    productStatusDetailService.saveProductStatusDetail(productStatusDetail);

                    if (checkQty==1) session.setAttribute(Constant.MSG_SUCCESS,"Qty has ABS-ed and Insert success!!!");
                    else if (checkPrice==1) session.setAttribute(Constant.MSG_SUCCESS,"Price has ABS-ed and Insert success!!!");
                    else if (checkPrice==1 && checkQty==1) session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS and Price has ABS and Insert success!!!");
                    else session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
                    productStatusList1.setPrice(productStatusList1.getPrice().add(productStatusDetail.getPriceTotal()));
                    productStatusListService.updateProductStatusList(productStatusList1);
                    shelf1.setQty(shelf1.getQty()+productStatusDetail.getQty());
                    shelfService.updateShelf(shelf1);
                    return "redirect:/product-done-detail/list";
                }

//                ProductStatusList productStatusList1 = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());



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
                Shelf shelf = shelfService.findByIdShelf(productStatusDetail.getShelf().getId());
                shelf.setQty(shelf.getQty()-productStatusDetail.getQty());
                shelfService.updateShelf(shelf);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/product-done-detail/list";
    }

    @GetMapping("/product-done-detail/export")
    public ModelAndView exportReport(Model model, HttpSession session , @ModelAttribute("searchForm") @RequestBody ProductStatusDetailTemp productStatusDetailTemp
    ) {
        ModelAndView modelAndView = new ModelAndView();

        List<ProductStatusDetailTemp> productStatusDetailTempList = productStatusDetailTempService.findProductStatusDetailTemp("activeFlag",1);

        modelAndView.addObject(Constant.KEY_GOODS_RECEIPT_REPORT, productStatusDetailTempList);
        modelAndView.setView(new ProductStatusDetailReport());
        return modelAndView;
    }
}
