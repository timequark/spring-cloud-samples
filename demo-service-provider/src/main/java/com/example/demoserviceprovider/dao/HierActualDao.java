package com.example.demoserviceprovider.dao;

import com.example.demoserviceprovider.entity.HierActual;
import com.example.demoserviceprovider.mapper.HierActualMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class HierActualDao {
    @Resource
    private HierActualMapper hierActualMapper;

    public List<HierActual> getHierActualRange(String hotelKey, int fromBiz, int toBiz) {
        return hierActualMapper.getHierActualRange(hotelKey, fromBiz, toBiz);
    }
}
