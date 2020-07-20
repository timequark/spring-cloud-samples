package com.example.demoserviceprovider.mapper;

import com.example.demoserviceprovider.entity.HierActual;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HierActualMapper {
    List<HierActual> getHierActualRange(String hotelKey, @Param("fromBiz") int fromBiz, @Param("toBiz") int toBiz);
}
