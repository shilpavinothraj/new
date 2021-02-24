package com.example.vajro.database

import Products

class Sqllite {
    val TABLE_NAME = "auth"

    val COLUMN_ID = "id"
    val COLUMN_USERNAME = "username"
    val COLUMN_PASSWORD = "password"
    val COLUMN_TIMESTAMP = "timestamp"

    private var id = 0
    private var username: String? = null
    private var password: String? = null
    private var key: String? = null
    private var timestamp: String? = null
    private var products:List<Products>?=null

    // Create table SQL query
    val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")")

    fun SqliteHelper() {}

    fun SqliteHelper(
        id: Int,
        username: String?,
        password: String?,
        timestamp: String?
    ) {
        this.id = id
        this.username = username
        this.password = password
        this.timestamp = timestamp
    }


    fun SqliteHelper(username: String?, password: String?) {
        this.username = username
        this.password = password
    }
    fun SqliteHelper(products: List<Products>?) {
        this.products = products
    }


    fun getKey(): String? {
        return key
    }

    fun setKey(key: String?) {
        this.key = key
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getTimestamp(): String? {
        return timestamp
    }

    fun setTimestamp(timestamp: String?) {
        this.timestamp = timestamp
    }
    fun getproducts(products: List<Products>?): List<Products>? {
        return products
    }
    fun setproducts(products: List<Products>?){
        this.products=products
    }


}