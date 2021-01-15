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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IssueDetailController {
    @Autowired
    private IssueDetailService issueDetailService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private VatDetailTempService vatDetailTempService;

    @Autowired
    private ProductDetailService productDetailService;


    static final Logger log = Logger.getLogger(IssueDetailController.class);

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

    }

    @RequestMapping(value = {"/vat-detail/list", "/vat-detail/list/"})

    public String redirect() {
        return "redirect:/vat-detail/list/1";
    }

    @RequestMapping(value = "/vat-detail/list/{page}")
    public String showProductInfoList(Model model, HttpSession session, @ModelAttribute("searchForm") IssueDetail vatDetail, @PathVariable("page") int page) throws Exception {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
//        Issue vat = vatService.findByIdIssue(vatId);
//        vatDetail.setIssue(vat);
        List<VatDetailTemp> vatDetailTempList = vatDetailTempService.findVatDetailTemp("activeFlag", 1);
        for (VatDetailTemp vatDetailTemp : vatDetailTempList) {
            vatDetailTempService.deleteVatDetailTemp(vatDetailTemp);
        }

        List<IssueDetail> vatDetails = issueDetailService.getAllIssueDetail(vatDetail, paging);

        int totalQty = 0;
        BigDecimal totalPriceOne = new BigDecimal(0);
        BigDecimal totalPriceTotal = new BigDecimal(0);
        for (IssueDetail vatDetail1 : vatDetails) {
            VatDetailTemp vatDetailTemp = new VatDetailTemp();
            vatDetail1.setPriceTotal(vatDetail1.getPriceOne().multiply(BigDecimal.valueOf(vatDetail1.getQty())));
            vatDetailTemp.setProductName(vatDetail1.getProductInfo().getName());
            vatDetailTemp.setQty(vatDetail1.getQty());
            vatDetailTemp.setPriceOne(vatDetail1.getPriceOne());
            vatDetailTemp.setPriceTotal(vatDetail1.getPriceTotal());
            vatDetailTemp.setIssueName(vatDetail1.getIssue().getCode());
            vatDetailTemp.setSupplierName(vatDetail1.getIssue().getSupplier().getName());
            vatDetailTempService.saveVatDetailTemp(vatDetailTemp);
            totalQty += vatDetail1.getQty();
            totalPriceOne = totalPriceOne.add(vatDetail1.getPriceOne());
            totalPriceTotal = totalPriceTotal.add(vatDetail1.getPriceTotal());
        }
        model.addAttribute("totalQty", totalQty);
        model.addAttribute("totalPriceOne", totalPriceOne);
        model.addAttribute("totalPriceTotal", totalPriceTotal);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", vatDetails);
        return "vatDetail-list";

    }

    @RequestMapping(value = "/vat-detail/getAll/{page}")
    public String getAll(Model model, HttpSession session, @ModelAttribute("searchForm") IssueDetail vatDetail, @PathVariable("page") int page) throws Exception {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
//        Issue vat = vatService.findByIdIssue(vatId);
//        vatDetail.setIssue(vat);
        List<VatDetailTemp> vatDetailTempList = vatDetailTempService.findVatDetailTemp("activeFlag", 1);
        for (VatDetailTemp vatDetailTemp : vatDetailTempList) {
            vatDetailTempService.deleteVatDetailTemp(vatDetailTemp);
        }
        List<IssueDetail> vatDetails = issueDetailService.getAllIssueDetail(null, paging);
        int totalQty = 0;
        BigDecimal totalPriceOne = new BigDecimal(0);
        BigDecimal totalPriceTotal = new BigDecimal(0);
        for (IssueDetail vatDetail1 : vatDetails) {
            VatDetailTemp vatDetailTemp = new VatDetailTemp();
            vatDetail1.setPriceTotal(vatDetail1.getPriceOne().multiply(BigDecimal.valueOf(vatDetail1.getQty())));
            vatDetailTemp.setProductName(vatDetail1.getProductInfo().getName());
            vatDetailTemp.setQty(vatDetail1.getQty());
            vatDetailTemp.setPriceOne(vatDetail1.getPriceOne());
            vatDetailTemp.setPriceTotal(vatDetail1.getPriceTotal());
            vatDetailTemp.setIssueName(vatDetail1.getIssue().getCode());
            vatDetailTemp.setSupplierName(vatDetail1.getIssue().getSupplier().getName());
            vatDetailTempService.saveVatDetailTemp(vatDetailTemp);
            totalQty += vatDetail1.getQty();
            totalPriceOne = totalPriceOne.add(vatDetail1.getPriceOne());
            totalPriceTotal = totalPriceTotal.add(vatDetail1.getPriceTotal());
        }
        model.addAttribute("totalQty", totalQty);
        model.addAttribute("totalPriceOne", totalPriceOne);
        model.addAttribute("totalPriceTotal", totalPriceTotal);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", vatDetails);
        return "vatDetail-list";

    }

    @RequestMapping(value = "/vat-detail/vat/{code}")
    public String showProductInfoList(Model model, HttpSession session, @ModelAttribute("searchForm") IssueDetail vatDetail, @PathVariable("code") String code) throws Exception {
        Paging paging = new Paging(5);

        Issue vat = issueService.findIssue("code", code).get(0);
//        vatDetail.setIssue(vat);

        vatDetail.setIssue(vat);
        if (vatDetail.getProductInfo() == null) {
            vatDetail.setProductInfo(new ProductInfo());
        }

        List<VatDetailTemp> vatDetailTempList = vatDetailTempService.findVatDetailTemp("activeFlag", 1);
        for (VatDetailTemp vatDetailTemp : vatDetailTempList) {
            vatDetailTempService.deleteVatDetailTemp(vatDetailTemp);
        }

        List<IssueDetail> vatDetails = issueDetailService.getAllIssueDetail(vatDetail, paging);

        int totalQty = 0;
        BigDecimal totalPriceOne = new BigDecimal(0);
        BigDecimal totalPriceTotal = new BigDecimal(0);
        for (IssueDetail vatDetail1 : vatDetails) {
            VatDetailTemp vatDetailTemp = new VatDetailTemp();
            vatDetail1.setPriceTotal(vatDetail1.getPriceOne().multiply(BigDecimal.valueOf(vatDetail1.getQty())));
            vatDetailTemp.setProductName(vatDetail1.getProductInfo().getName());
            vatDetailTemp.setQty(vatDetail1.getQty());
            vatDetailTemp.setPriceOne(vatDetail1.getPriceOne());
            vatDetailTemp.setPriceTotal(vatDetail1.getPriceTotal());
            vatDetailTemp.setIssueName(vatDetail1.getIssue().getCode());
            vatDetailTemp.setSupplierName(vatDetail1.getIssue().getSupplier().getName());
            vatDetailTempService.saveVatDetailTemp(vatDetailTemp);
            totalQty += vatDetail1.getQty();
            totalPriceOne = totalPriceOne.add(vatDetail1.getPriceOne());
            totalPriceTotal = totalPriceTotal.add(vatDetail1.getPriceTotal());
        }
        model.addAttribute("totalQty", totalQty);
        model.addAttribute("totalPriceOne", totalPriceOne);
        model.addAttribute("totalPriceTotal", totalPriceTotal);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", vatDetails);
        return "vatDetail-list";

    }

    @GetMapping("/vat-detail/{vatId}/add")
    public String add(Model model, @PathVariable("vatId") int vatId) {
        model.addAttribute("titlePage", "Add Issue Detail");
//
        Issue vatFind = issueService.findByIdIssue(vatId);
//        vatDetail.setIssue(vat);
        model.addAttribute("modelForm", new IssueDetail());

        List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
        Map<String, String> mapProductInfo = new HashMap<>();
        for (ProductInfo productInfo : productInfos) {
            mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
        }

//        Map<String, String> mapIssue = new HashMap<>();
//        mapIssue.put(String.valueOf(vat.getId()), vat.getCode());
        List<Issue> vats = issueService.getAllIssue(vatFind, null);
        Collections.sort(vats, new UpdateDateCompatatorIssue());
        Map<String, String> mapIssue = new HashMap<>();
        for (Issue vat : vats) {
            mapIssue.put(String.valueOf(vat.getId()), vat.getCode());
        }

        model.addAttribute("mapIssue", mapIssue);
        model.addAttribute("mapIssue", mapIssue);
        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapProductInfo", mapProductInfo);

        model.addAttribute("viewOnly", false);
        return "vatDetail-action";
    }

    @GetMapping("/vat-detail/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        log.info("Edit vat Detail with id=" + id);
        IssueDetail vatDetail = issueDetailService.findByIdIssueDetail(id);
        if (vatDetail != null) {

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for (ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }
            vatDetail.setProductInfoId(vatDetail.getProductInfo().getId());

            List<Issue> vats = issueService.getAllIssue(null, null);
            Collections.sort(vats, new UpdateDateCompatatorIssue());
            Map<String, String> mapIssue = new HashMap<>();
            for (Issue vat : vats) {
                mapIssue.put(String.valueOf(vat.getId()), vat.getCode());
            }

            vatDetail.setIssueId(vatDetail.getIssue().getId());


            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapIssue", mapIssue);
            model.addAttribute("titlePage", "Edit Issue Detail");
            model.addAttribute("modelForm", vatDetail);
            model.addAttribute("modelForm", vatDetail);
            model.addAttribute("viewOnly", false);
            return "vatDetail-action";
        }
        return "redirect:/vat-detail/list";
    }

    @GetMapping("/vat-detail/view/{id}")
    public String view(Model model, @PathVariable("id") int id) {
        log.info("View productDetail with id=" + id);
        IssueDetail vatDetail = issueDetailService.findByIdIssueDetail(id);
        if (vatDetail != null) {
            model.addAttribute("titlePage", "View Issue Detail");
            model.addAttribute("modelForm", vatDetail);
            model.addAttribute("viewOnly", true);
            return "vatDetail-action";
        }
        return "redirect:/vat-detail/list";
    }

    @PostMapping("/vat-detail/save")
    public String save(Model model, @ModelAttribute("modelForm") @Validated IssueDetail vatDetail, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (vatDetail.getId() != null) {
                model.addAttribute("titlePage", "Edit Issue Detail");
            } else {
                model.addAttribute("titlePage", "Add Issue Detail");
            }

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for (ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }

            List<Issue> vats = issueService.getAllIssue(null, null);
            Collections.sort(vats, new UpdateDateCompatatorIssue());
            Map<String, String> mapIssue = new HashMap<>();
            for (Issue vat : vats) {
                mapIssue.put(String.valueOf(vat.getId()), vat.getCode());
            }

//            List<Issue> vats = vatService.findIssue("code",vatDetail.getIssue().getCode());
//            Map<String, String> mapIssue = new HashMap<>();
//            for (Issue vat : vats) {
//                mapIssue.put(String.valueOf(vat.getId()), vat.getCode());
//            }
//            model.addAttribute("mapIssue",vats);
            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapIssue", mapIssue);

            model.addAttribute("modelForm", vatDetail);
            model.addAttribute("viewOnly", false);
        }

        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(vatDetail.getProductInfoId());
        vatDetail.setProductInfo(productInfo);

        Issue vat = new Issue();
        vat.setId(vatDetail.getIssueId());
        vatDetail.setIssue(vat);

        int checkPrice = 0, checkQty = 0;
        if (vatDetail.getQty() < 0) {
            vatDetail.setQty(Math.abs(vatDetail.getQty()));
            checkQty = 1;
        }
        if (vatDetail.getPriceOne().compareTo(new BigDecimal(0)) < 0) {
            vatDetail.setPriceOne(vatDetail.getPriceOne().abs());
            checkPrice = 1;
        }

        if (vatDetail.getId() != null && vatDetail.getId() != 0) {
            try {

                issueDetailService.updateIssueDetail(vatDetail);
//                Issue vat1 = vatService.findByIdIssue(vatDetail.getIssue().getId());
//                vat1.setPrice(vat1.getPrice().add(vatDetail.getPriceTotal()));
//                vat1.setTotal(vat1.getPrice().add(vat1.getPercent().multiply(vat1.getPrice())));
//                vatService.updateIssue(vat1);
                if (checkQty == 1) session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS-ed and Update success!!!");
                else if (checkPrice == 1)
                    session.setAttribute(Constant.MSG_SUCCESS, "Price has ABS-ed and Update success!!!");
                else if (checkPrice == 1 && checkQty == 1)
                    session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS and Price has ABS and Update success!!!");
                else session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        } else {
            try {
                issueDetailService.saveIssueDetail(vatDetail);
                if (checkQty == 1) session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS-ed and Insert success!!!");
                else if (checkPrice == 1)
                    session.setAttribute(Constant.MSG_SUCCESS, "Price has ABS-ed and Insert success!!!");
                else if (checkPrice == 1 && checkQty == 1)
                    session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS and Price has ABS and Insert success!!!");
                else session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
//                Issue vat1 = vatService.findByIdIssue(vatDetail.getIssue().getId());
//                vat1.setPrice(vat1.getPrice().add(vatDetail.getPriceTotal()));
//                vat1.setTotal(vat1.getPrice().add(vat1.getPercent().multiply(vat1.getPrice())));
//                vatService.updateIssue(vat1);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/vat-detail/list";

    }

    @GetMapping("/vat-detail/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
        log.info("Delete vatDetail with id=" + id);
        IssueDetail vatDetail = issueDetailService.findByIdIssueDetail(id);
        if (vatDetail != null) {
            try {
                issueDetailService.deleteIssueDetail(vatDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
                Issue vat = issueService.findByIdIssue(vatDetail.getIssue().getId());
                vat.setPrice(vat.getPrice().subtract(vatDetail.getPriceTotal()));
                vat.setTotal(vat.getPrice().add(vat.getPercent().multiply(vat.getPrice())));
                issueService.updateIssue(vat);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/vat-detail/list";
    }

    @GetMapping("/vat-detail/export")
    public ModelAndView exportReport(Model model, HttpSession session, @ModelAttribute("searchForm") @RequestBody IssueDetail vatDetail
    ) {
        ModelAndView modelAndView = new ModelAndView();

        List<VatDetailTemp> vatDetailTempList = vatDetailTempService.findVatDetailTemp("activeFlag", 1);

        modelAndView.addObject(Constant.KEY_GOODS_RECEIPT_REPORT, vatDetailTempList);
        modelAndView.setView(new IssueDetailReport());
        return modelAndView;
    }
}
