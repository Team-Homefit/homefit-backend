package com.homefit.homefit.board.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.homefit.homefit.board.persistence.po.ArticlePagePo;
import com.homefit.homefit.board.persistence.po.ArticlePo;
import com.homefit.homefit.board.persistence.po.ArticleSimplePo;
import com.homefit.homefit.board.domain.Article;

@Mapper
public interface ArticleRepository {
    List<ArticlePagePo> getArticles(Long boardId, Boolean isLiked, int page, int size, int offset, Long memberId);

    ArticlePo getArticleById(Long articleId, Long memberId);

    ArticleSimplePo getArticleByComment(Long commentId);

    int countById(Long id);

    int countArticleInInterestedRegionById(Long articleId, Long memberId);

    boolean isInterestedRegion(Long boardId, Long memberId);

    int createArticle(Article article);

    int updateArticle(Article article);

    int deleteArticle(Long articleId);
}
