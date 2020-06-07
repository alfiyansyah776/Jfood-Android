package com.dicoding.picodiploma.jfood_android;

/**
 * <h1>Promo<h1>
 * Kelas ini berfungsi untuk mendapatkan dan memberikan nilai pada obyek Promo
 * dengan memanfaatkan method constructor, mutator, dan accessor
 *
 * @author Muhammad Alfiyansyah
 * @version 27-February-2020
 *
 */
public class Promo
{
    //Atribut yang digunakan pada kelas ini dengan access modifier private
    private int id;
    private String code;
    private int discount;
    private int minPrice;
    private boolean active;

    public Promo(int id, String code, int discount, int minPrice, boolean active) {
        //Kata kunci this digunakan untuk mereferensikan obyek saat ini yaitu customer
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.minPrice = minPrice;
        this.active = active;
    }

    /**
     * Method getID merupakan method getter untuk variabel id
     * @return <code>int<code> akan mengembalikan nilai id ketika method ini
     * dipanggil
     */
    public int getId() {
        return id;
    }


    public String getCode() {
        return code;
    }


    public int getDiscount() {
        return discount;
    }


    public int getMinPrice() {
        return minPrice;
    }


    public boolean getActive() {
        return active;
    }


    public void setId(int id){
        this.id = id;
    }


    public void setCode (String code) {
        this.code = code;
    }


    public void setDiscount (int discount) {
        this.discount = discount;
    }


    public void setMinPrice (int minPrice) {
        this.minPrice = minPrice;
    }


    public void setActive (boolean active) {
        this.active = active;
    }

    public String toString(){
        return "Id = " +id+ "\nCode= " +code+ "\nDiscount= " +discount+ "\nMinimal Price= " +minPrice+ "\nActive Status =  " +active;
    }

}