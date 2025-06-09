package com.homefit.homefit.board.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.homefit.homefit.board.domain.Like;

@Mapper
public interface LikeRepository {
	int insert(Like like);
	
	int countById(Long articleId, Long memberId);
	
	int deleteById(Long articleId, Long memberId);
}
