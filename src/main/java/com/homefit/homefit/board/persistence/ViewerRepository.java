package com.homefit.homefit.board.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.homefit.homefit.board.domain.Viewer;

@Mapper
public interface ViewerRepository {
    boolean hasViewedToday(Long articleId, Long memberId);

    void createViewer(Viewer viewer);
}