package com.example.demoserviceprovider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ETLRmRateCode {
    private String sysid;
    private String hotelkey;
    private String code;
    private String descriptOfChinese;
    private String descriptOfEnglish;
    private String descript;
    private Date   sdate;
    private Date   edate;
    private String originKey;
    private String marketSegKey;
    private String rateTypeKey;
    private BigDecimal commission;
    private int    isValid;
    private int    isStatistic;
    private int    invalidDate;
    private String mappingKey;
}
