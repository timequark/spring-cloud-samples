package com.example.demoserviceprovider.service;

import com.example.demoserviceprovider.base.Constants;
import com.example.demoserviceprovider.base.util.RedisUtil;
import com.example.demoserviceprovider.dao.CommonDao;
import com.example.demoserviceprovider.entity.DimHotels;
import com.example.demoserviceprovider.entity.ETLMarketseg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCommon {
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private RedisUtil redisUtil;

    @Cacheable(value = "hy", key = "#hotelCode+'_dimhotels'", unless = "#result==null")
    public DimHotels getDimHotels(String hotelCode) {
        return commonDao.getDimHotels(hotelCode);
    }

    @Cacheable(value = "hy", key = "#hotelCode+'_etlmarketseg'", unless = "#result==null")
    public List<ETLMarketseg> getETLMarketseg(String hotelCode, String hotelKey) {
        return commonDao.getETLMarketseg(hotelKey);
    }

    public void clearCache(String hotelCode) {
        redisUtil.delByPrefix(Constants.REDIS_DB0, String.format("hy_%s_", hotelCode));
    }
}
