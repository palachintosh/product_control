package com.bike_control;

import java.io.Serializable;


public class RequestData  implements  Serializable {

//    private String product_code;
    private String from_warehouse;
    private String to_warehouse;
    private String quantity_tt;


    public RequestData(String from_warehouse, String to_warehouse, String quantity_tt) {
//        this.product_code = product_code;
        this.from_warehouse = from_warehouse;
        this.to_warehouse = to_warehouse;
        this.quantity_tt = quantity_tt;
    }

//    public String getProduct_code() { return product_code; }
//    public void setProduct_code(String product_code) {
//        this.product_code = product_code;
//    }

    public String getFrom_warehouse() {return from_warehouse; }
    public void setFrom_warehouse(String from_warehouse) {
        this.from_warehouse = from_warehouse;
    }

    public String getTo_warehouse() { return to_warehouse; }
    public void setTo_warehouse(String to_warehouse) {
        this.to_warehouse = to_warehouse;
    }

    public String getQuantity_tt() { return quantity_tt; }
    public void setQuantity_tt(String quantity_tt) {
        this.quantity_tt = quantity_tt;
    }
}
