package com.challenge2.core.java.entity;

import com.challenge2.core.java.constant.Status;

import javax.persistence.*;

@Entity
@Table(name = "Invoice")
public class Invoice extends BaseEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "amount")
    private int amount;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ps_id", nullable = false)
    private PurchasingSpecialist purchasingSpecialist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public PurchasingSpecialist getPurchasingSpecialist() {
        return purchasingSpecialist;
    }

    public void setPurchasingSpecialist(PurchasingSpecialist purchasingSpecialist) {
        this.purchasingSpecialist = purchasingSpecialist;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", amount=" + amount +
                ", productName='" + productName + '\'' +
                ", billNo='" + billNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

