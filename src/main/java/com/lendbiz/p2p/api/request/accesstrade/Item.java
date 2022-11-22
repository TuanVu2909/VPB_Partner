package com.lendbiz.p2p.api.request.accesstrade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private String id;
    private String sku;
    private String name;
    private long price;
    private long quantity;
    private String category;
    private String categoryID;

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("sku")
    public String getSku() { return sku; }
    @JsonProperty("sku")
    public void setSku(String value) { this.sku = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("price")
    public long getPrice() { return price; }
    @JsonProperty("price")
    public void setPrice(long value) { this.price = value; }

    @JsonProperty("quantity")
    public long getQuantity() { return quantity; }
    @JsonProperty("quantity")
    public void setQuantity(long value) { this.quantity = value; }

    @JsonProperty("category")
    public String getCategory() { return category; }
    @JsonProperty("category")
    public void setCategory(String value) { this.category = value; }

    @JsonProperty("category_id")
    public String getCategoryID() { return categoryID; }
    @JsonProperty("category_id")
    public void setCategoryID(String value) { this.categoryID = value; }
}
