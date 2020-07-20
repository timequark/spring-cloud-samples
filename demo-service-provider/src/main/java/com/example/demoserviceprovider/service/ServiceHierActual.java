package com.example.demoserviceprovider.service;

import com.example.demoserviceprovider.SpringContextUtil;
import com.example.demoserviceprovider.dao.CommonDao;
import com.example.demoserviceprovider.dao.HierActualDao;
import com.example.demoserviceprovider.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServiceHierActual {
    @Autowired
    private HierActualDao hierActualDao;
    @Autowired
    private CommonDao commonDao;

    @Cacheable(value = "hy", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    public List<HierActual> getHierActualDetail(String hotelcode, String hotelkey, int fromBiz, int toBiz) {
        List<HierActual> actuals = hierActualDao.getHierActualRange(hotelkey, fromBiz, toBiz);
        if (!CollectionUtils.isEmpty(actuals)) {
            List<ETLMarketseg> listMarket = commonDao.getETLMarketseg(hotelkey);
            List<ETLOrigin> listOrigin = commonDao.getETLOrigin(hotelkey);
            List<ETLRoomType> listRoomType = commonDao.getETLRoomType(hotelkey);
            List<ETLRmRateCode> listRmRateCode = commonDao.getETLRmRateCode(hotelkey);

            Map<String, String> mapMarket = new HashMap<>();
            Map<String, String> mapOrigin = new HashMap<>();
            Map<String, String> mapRoomType = new HashMap<>();
            Map<String, String> mapRateCode = new HashMap<>();

            listMarket.forEach(e -> mapMarket.put(e.getSysid(), e.getDescriptOfChinese()));
            listOrigin.forEach(e -> mapOrigin.put(e.getSysid(), e.getDescriptOfChinese()));
            listRoomType.forEach(e -> mapRoomType.put(e.getSysid(), e.getDescriptOfChinese()));
            listRmRateCode.forEach(e -> mapRateCode.put(e.getSysid(), e.getDescriptOfChinese()));

            actuals.forEach(e -> {
                e.marketSegName = mapMarket.get(e.marketSegmentKey);
                e.originName = mapOrigin.get(e.originKey);
                e.roomTypeName = mapRoomType.get(e.roomTypeKey);
                e.rateCodeName = mapRateCode.get(e.rateCodeKey);
            });
        }

        return actuals;
    }

    @Cacheable(value = "hy", keyGenerator = "cacheKeyGenerator", unless = "#result==null")
    public List<HierActual> getHierActualSummaryByDay(String hotelcode, String hotelkey, int fromBiz, int toBiz) {
        // 使用代理后的Spring Bean，否则无法使用缓存数据
        ServiceHierActual selfService = (ServiceHierActual) SpringContextUtil.getBean(this.getClass());

        List<HierActual> actuals = selfService.getHierActualDetail(hotelcode, hotelkey, fromBiz, toBiz);
        List<HierActual> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(actuals)) {
            Map<String, Map<String, Map<Integer, List<HierActual>>>> map = actuals.stream().collect(
                    Collectors.groupingBy(HierActual::getHotelKey,
                            Collectors.groupingBy(HierActual::getHotelCode,
                                    Collectors.groupingBy(HierActual::getBizDate,
                                            Collectors.toList()))));
            map.values().forEach((e)->{
                e.values().forEach((f) -> {
                    f.values().forEach((g) -> {
                        double _rmnights = g.stream().collect(Collectors.summingDouble(HierActual::getRmnights));
                        double _revenue = g.stream().collect(Collectors.summingDouble(HierActual::getRevenue));
                        HierActual actual = new HierActual();
                        actual.hotelKey = g.get(0).hotelKey;
                        actual.hotelCode = g.get(0).hotelCode;
                        actual.bizDate = g.get(0).bizDate;
                        actual.rmnights = _rmnights;
                        actual.revenue = _revenue;
                        result.add(actual);
                    });
                });
            });
        }

        return result;
    }
}
