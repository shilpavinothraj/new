package com.example.vajro.database;

import java.util.List;

public class SqliteHelper {

    public static final String TABLE_NAME = "auth";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN__PRODUCT_ID = "productid";
    public static final String COLUMN__QUANTITY = "quantity";
    public static final String COLUMN__PRICE= "price";
    public static final String COLUMN__IMAGE = "image";



    private List<Integer> id;
    private List<String> productid;
    private List<String> quantity;
    private List<String> price;
    private List<String> image;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN__PRODUCT_ID + " TEXT,"
                    + COLUMN__QUANTITY + " TEXT,"
                    + COLUMN__PRICE + " TEXT"
                    + COLUMN__IMAGE + " TEXT"
                    + ")";

    public SqliteHelper() {
    }

    public SqliteHelper(List<Integer> id, List<String> productid,List<String> quantity,List<String> price,List<String> image) {
        this.id = id;
        this.productid = productid;
        this.quantity=quantity;
        this.price=price;
        this.image=image;
    }






    public List<String> getProductid() {
        return productid;
    }

    public void setQuantity(List<String> quantity) {
        this.quantity = quantity;
    }
    public List<String> getQuantity() {
        return quantity;
    }

    public void setProductid(List<String> productid) {
        this.productid = productid;
    }
    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }
    public List<String> getPrice() {
        return price;
    } public void setImage(List<String> image) {
        this.image = image;
    }
    public List<String> getImage() {
        return image;
    }
}
