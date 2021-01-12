package inventory.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class IssueDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Issue issue;
    private ProductInfo productInfo;
    private int qty;
    private BigDecimal priceOne;
    @Transient
    private BigDecimal priceTotal;
    private Integer issueId;
    private Integer productInfoId;
    private BigDecimal fromPriceOne;
    private BigDecimal toPriceOne;
    private BigDecimal fromPriceTotal;
    private BigDecimal toPriceTotal;
    private int activeFlag;

    public IssueDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPriceOne() {
        return priceOne;
    }

    public void setPriceOne(BigDecimal priceOne) {
        this.priceOne = priceOne;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getProductInfoId() {
        return productInfoId;
    }

    public void setProductInfoId(Integer productInfoId) {
        this.productInfoId = productInfoId;
    }

    public BigDecimal getFromPriceOne() {
        return fromPriceOne;
    }

    public void setFromPriceOne(BigDecimal fromPriceOne) {
        this.fromPriceOne = fromPriceOne;
    }

    public BigDecimal getToPriceOne() {
        return toPriceOne;
    }

    public void setToPriceOne(BigDecimal toPriceOne) {
        this.toPriceOne = toPriceOne;
    }

    public BigDecimal getFromPriceTotal() {
        return fromPriceTotal;
    }

    public void setFromPriceTotal(BigDecimal fromPriceTotal) {
        this.fromPriceTotal = fromPriceTotal;
    }

    public BigDecimal getToPriceTotal() {
        return toPriceTotal;
    }

    public void setToPriceTotal(BigDecimal toPriceTotal) {
        this.toPriceTotal = toPriceTotal;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }
}
