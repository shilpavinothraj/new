package com.example.vajro.database;

import java.util.List;

public class SqliteHelper {

    public static final String TABLE_NAME = "auth";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN__PRODUCT_ID = "productid";


    private int id;
    private List<String> productid;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN__PRODUCT_ID + " TEXT"
                    + ")";

    public SqliteHelper() {
    }

    public SqliteHelper(int id, List<String> productid) {
        this.id = id;
        this.productid = productid;
    }






    public List<String> getProductid() {
        return productid;
    }

    public void setProductid(List<String> productid) {
        this.productid = productid;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
