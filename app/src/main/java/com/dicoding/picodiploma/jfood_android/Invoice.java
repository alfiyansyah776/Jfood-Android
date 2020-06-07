package com.dicoding.picodiploma.jfood_android;


public class Invoice {

    private int invoiceId;
    private String date;
    private String customerName;
    private String status;

    public Invoice(int invoiceId, String date, String customerName, String status){
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.date = date;
        this.status = status;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
