package com.homefit.homefit.house.persistence;

import java.time.LocalDate;
import java.util.List;

import com.homefit.homefit.house.persistence.command.SearchHouseCondition;
import org.apache.ibatis.annotations.Mapper;

import com.homefit.homefit.house.persistence.po.DealPagePo;
import com.homefit.homefit.house.persistence.po.HouseDealPo;
import com.homefit.homefit.house.persistence.po.HousePo;

@Mapper
public interface HouseRepository {
    HousePo searchHouseBySequence(String sequence);
    
    List<Double> searchAreasBySequence(String sequence);
    
    List<HouseDealPo> searchHouseDealsBy(String sequence, Double area, LocalDate since, LocalDate until);
    
    List<DealPagePo> searchDealsByPage(Integer pageSize, Integer offset, SearchHouseCondition condition);
}
