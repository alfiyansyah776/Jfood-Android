package com.dicoding.picodiploma.jfood_android;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    //Atribut yang digunakan pada kelas ini dengan access modifier private
    private int id;
    private String name;
    private String email;
    private String password;
    private Calendar joinDate;
    public Customer(int id, String name, String email, String password, Calendar joinDate) {
        //Kata kunci this digunakan untuk mereferensikan obyek saat ini yaitu customer
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinDate = joinDate;
        setEmail(email);
        setPassword(password);

    }

    public Customer(int id, String name, String email, String password, int year, int month, int dayOfMonth) {
        //Kata kunci this digunakan untuk mereferensikan obyek saat ini yaitu customer
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinDate = new GregorianCalendar(year,month - 1,dayOfMonth);
        setEmail(email);
        setPassword(password);
    }

    public Customer(int id, String name, String email, String password) {
        //Kata kunci this digunakan untuk mereferensikan obyek saat ini yaitu customer
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        setEmail(email);
        setPassword(password);
        this.joinDate = Calendar.getInstance();
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public Calendar getJoinDate() {
        return joinDate;
    }


    public void setId(int id){
        this.id = id;
    }


    public void setName (String name) {
        this.name = name;
    }


    public void setEmail (String email) {
        String pattern =  "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(email);
        if (m.find()) {
            this.email = email;
        } else {

        }
    }


    public void setPassword (String password) {

        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        if (m.find()) {
            this.password = password;
        } else {


        }

    }


    public void setJoinDate (Calendar joinDate) {
        this.joinDate = joinDate;
    }

    public void setJoinDate(int year, int month, int dayOfMonth) {
        this.joinDate = new GregorianCalendar(year,month-1,dayOfMonth);
    }
}
