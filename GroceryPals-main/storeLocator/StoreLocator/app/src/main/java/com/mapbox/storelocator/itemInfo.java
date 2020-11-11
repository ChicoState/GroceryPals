package com.mapbox.storelocator;

public class itemInfo {

    private String itemName;
    private double itemPrice;
    private int itemCount;
    // private double itemPounds;

    //Constructor
    itemInfo(String name, double price){

        this.itemName = name;
        this.itemPrice = price;
        this.itemCount = 1;
        // this.itemPounds = itemPounds;
    }

    //Constuctor
    itemInfo(String name){
        this.itemName = name;
        this.itemPrice = 0.0;
        this.itemCount =1;
    }

    String getAllInfo(){

        String info = "";

        info = "Product Name: " + getItemName() +  " Price: $" + getItemPrice() + " Count: " + getItemCount();

        return info;
    }
    //getters below
    public String getItemName(){
        return this.itemName;
    }

    public double getItemPrice(){
        return this.itemPrice;
    }

    public int getItemCount(){
        return this.itemCount;
    }

    //setters below
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void updateItemCount() {
        this.itemCount +=1;
    }
}