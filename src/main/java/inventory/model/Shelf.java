package inventory.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Shelf implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private int activeFlag;
    private Date updateDate;
    private Date createDate;
    private int total;
    private int qty;
    @Transient
    private int qtyRest;
    private Set productStatusDetails = new HashSet(0);

    public Shelf() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQtyRest() {
        return qtyRest;
    }

    public void setQtyRest(int qtyRest) {
        this.qtyRest = qtyRest;
    }

    public Set getProductStatusDetails() {
        return productStatusDetails;
    }

    public void setProductStatusDetails(Set productStatusDetails) {
        this.productStatusDetails = productStatusDetails;
    }
}
