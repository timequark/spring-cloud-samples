package com.example.demoserviceprovider.mapper;

import com.example.demoserviceprovider.entity.RMSUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RMSUserMapperV2 {
    @Select("SELECT * FROM RMSUser WHERE usercode = #{userCode}")
    RMSUser getRMSUserByUserCode(String userCode);
}
