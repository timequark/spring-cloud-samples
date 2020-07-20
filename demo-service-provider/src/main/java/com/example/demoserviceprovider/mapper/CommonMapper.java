package com.example.demoserviceprovider.mapper;

import com.example.demoserviceprovider.entity.*;

import java.util.List;

public interface CommonMapper {
    DimHotels getDimHotels(String hotelCode);

    List<ETLMarketseg> getETLMarketseg(String hotelKey);

    List<ETLOrigin> getETLOrigin(String hotelKey);

    List<ETLRoomType> getETLRoomType(String hotelKey);

    List<ETLRmRateCode> getETLRmRateCode(String hotelKey);
}
