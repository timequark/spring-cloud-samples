package com.example.demoserviceprovider.dao;

import com.example.demoserviceprovider.entity.*;
import com.example.demoserviceprovider.mapper.CommonMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CommonDao {
    @Resource
    private CommonMapper commonMapper;

    public DimHotels getDimHotels(String hotelCode) {
        return commonMapper.getDimHotels(hotelCode);
    }

    public List<ETLMarketseg> getETLMarketseg(String hotelKey) {
        return commonMapper.getETLMarketseg(hotelKey);
    }

    public List<ETLOrigin> getETLOrigin(String hotelKey) {
        return commonMapper.getETLOrigin(hotelKey);
    }

    public List<ETLRoomType> getETLRoomType(String hotelKey) {
        return commonMapper.getETLRoomType(hotelKey);
    }

    public List<ETLRmRateCode> getETLRmRateCode(String hotelKey) {
        return commonMapper.getETLRmRateCode(hotelKey);
    }
}
