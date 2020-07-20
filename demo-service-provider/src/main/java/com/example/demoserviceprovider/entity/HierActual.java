package com.example.demoserviceprovider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierActual {
    public String hotelKey;
    public String hotelCode;

    public String marketSegmentKey;
    public String marketSegName;

    public String originKey;
    public String originName;

    public String roomTypeKey;
    public String roomTypeName;

    public String rateCodeKey;
    public String rateCodeName;

    public int bizDate;
    public double rmnights;
    public double revenue;
}
