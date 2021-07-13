package com.ixcorp.rapitel_app.Model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Orders {

    private Integer orderId;
    private String mumOrder;
    private String createDate;
    private String deliveryDate;
    private String deliveyTime;
    private Double subTotal;
    private Double igv;
    private Double total;

    private String numerDocument;
    private String firstName;
    private String lastName;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMumOrder() {
        return mumOrder;
    }

    public void setMumOrder(String mumOrder) {
        this.mumOrder = mumOrder;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveyTime() {
        return deliveyTime;
    }

    public void setDeliveyTime(String deliveyTime) {
        this.deliveyTime = deliveyTime;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNumerDocument() {
        return numerDocument;
    }

    public void setNumerDocument(String numerDocument) {
        this.numerDocument = numerDocument;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
