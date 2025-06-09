package com.homefit.homefit.region.persistence;

import com.homefit.homefit.region.persistence.po.SidoPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RegionRepository {
    List<SidoPo> selectAll();
}
