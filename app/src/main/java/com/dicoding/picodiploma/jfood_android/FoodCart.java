package com.dicoding.picodiploma.jfood_android;

import java.io.Serializable;
import java.util.Collection;

public class FoodCart implements Serializable {

   // private Customer customer;
    private int customerId;
    private int FoodId;
    private String FoodName;
    private int FoodPriceTimesQuantity;
    private int Quantity;
    private int foodImage;

    public FoodCart (int customerId, int id, String name, int price, int quantity, int foodImage){
        this.customerId = customerId;
        this.FoodId = id;
        this.FoodName = name;
        this.FoodPriceTimesQuantity = price;
        this.Quantity = quantity;
        this.foodImage = foodImage;
    }

    public int getCustomerId(){
        return customerId;
    }

    public void setCustomerId(int customerId){
        this.customerId=customerId;
    }

    public void setFoodId(int FoodId){
        this.FoodId=FoodId;
    }
    public int getFoodId(){
        return FoodId;
    }
    public void setFoodName(String FoodName){
        this.FoodName=FoodName;
    }

    public String getFoodName(){
        return FoodName;
    }

    public void setFoodPriceTimesQuantity(int FoodPriceTimesQuantity){
        this.FoodPriceTimesQuantity=FoodPriceTimesQuantity;
    }

    public int getFoodPriceTimesQuantity(){
        return FoodPriceTimesQuantity;
    }

    public void setQuantity(int Quantity){
        this.Quantity=Quantity;
    }

    public int getQuantity(){
        return Quantity;
    }

    public int getFoodImage(){
        return foodImage;
    }

    public void setFoodImage(int foodImage){
        this.foodImage = foodImage;
    }

    public String getJsonObject(){
        return "{FoodId:"+FoodId+",FoodName:"+FoodName+",FoodPriceTimesQuantity:"+FoodPriceTimesQuantity+",Quantity:"+Quantity+"}";
    }
}


