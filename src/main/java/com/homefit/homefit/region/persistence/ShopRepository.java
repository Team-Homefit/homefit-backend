package com.homefit.homefit.region.persistence;

import com.homefit.homefit.region.domain.Shop;
import com.homefit.homefit.region.persistence.po.ShopCountPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShopRepository {
    int insertAll(List<Shop> shops);

    List<ShopCountPo> selectNearKm(
            Double latitude, Double longitude, Double dist,
            List<String> firstCodes, List<String> secondCodes, List<String> thirdCodes);
}
