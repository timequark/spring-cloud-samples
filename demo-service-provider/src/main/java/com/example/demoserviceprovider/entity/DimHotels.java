package com.example.demoserviceprovider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimHotels {
    private String hotelKey;
    private String hotelCode;
    private String hotelName;
    private String hotelEnglishName;
    private String hotelChineseName;
    private String geographyKey;
    private String telephone;
    private String fax;
    private String address;
    private String company;
    private String pms;
    private int isValid;
    private int invalidDate;
    private int bizDate;
    private int bizMode;
    private int openDate;
}
