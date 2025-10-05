package com.example.order_system_spring_boot.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatesHelper {

    public String getDateNowTS(){

        LocalDateTime now = LocalDateTime.now();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        String formattedNow = now.format(formatter);


       return  formattedNow;
    }
}
