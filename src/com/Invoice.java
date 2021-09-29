package com;

import java.util.ArrayList;

public class Invoice {
    private int invoiceNumber;
    private ArrayList<Orders> items= new ArrayList<>();
    private double totalAmount;

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public ArrayList<Orders> getItems() {
        return items;
    }

    public void setItems(ArrayList<Orders> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber=" + invoiceNumber +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
