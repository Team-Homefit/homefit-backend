package com.homefit.homefit.board.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.homefit.homefit.board.domain.Comment;
import com.homefit.homefit.board.persistence.po.ArticlePo;
import com.homefit.homefit.board.persistence.po.CommentPo;

@Mapper
public interface CommentRepository {
    int insert(Comment comment);
	
    List<ArticlePo.Comment> findByArticleId(Long articleId);
    
    CommentPo selectById(Long id);
    
    int updateIsDeleted(Comment comment);
}
