package com.example.demoserviceprovider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ETLMarketseg {
    private String sysid;
    private String hotelkey;
    private String code;
    private String descriptOfChinese;
    private String descriptOfEnglish;
    private String descript;
    private String macroSegKey;
    private int    isValid;
    private int    isStatistic;
    private int    invalidDate;
    private String mappingKey;
}
