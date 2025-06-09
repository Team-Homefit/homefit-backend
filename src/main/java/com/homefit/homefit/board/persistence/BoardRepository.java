package com.homefit.homefit.board.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.homefit.homefit.board.persistence.po.BoardWithCountPo;

@Mapper
public interface BoardRepository {
    List<BoardWithCountPo> selectPageWithCount(int page, int size, int offset);
}
