package com.homefit.homefit.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homefit.homefit.board.application.ArticleService;
import com.homefit.homefit.board.application.dto.ArticlePageDto;
import com.homefit.homefit.board.application.dto.ArticleSimpleDto;
import com.homefit.homefit.board.controller.request.CommentRequest;
import com.homefit.homefit.board.controller.request.ModifyArticleRequest;
import com.homefit.homefit.board.controller.request.PostArticleRequest;
import com.homefit.homefit.board.controller.response.ArticlePageResponse;
import com.homefit.homefit.board.controller.response.ArticleResponse;
import com.homefit.homefit.board.controller.response.ArticleSimpleResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class ArticleController implements ArticleApiSpecification {
    private final ArticleService articleService;

    // 게시글 목록 조회: /board/article?boardId=xxx&isLiked=T&page=1&size=10
    @GetMapping("/article")
    public ResponseEntity<ArticlePageResponse> getArticles(
            @RequestParam(required = false) Long boardId,
            @RequestParam(required = false) Boolean isLiked,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        ArticlePageDto articlePageDto = articleService.getArticles(boardId, isLiked, page, size);
        return ResponseEntity.ok(ArticlePageResponse.from(articlePageDto));
    }

    // 게시글 상세 조회: /board/article/{article-id}
    @GetMapping("/article/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(ArticleResponse.from(articleService.getArticle(articleId)));
    }
    
    @GetMapping("/article/detail")
    public ResponseEntity<ArticleSimpleResponse> getArticleForComment(@RequestParam Long commentId) {
        ArticleSimpleDto dto = articleService.getArticleByComment(commentId);
    	
    	return ResponseEntity.ok(ArticleSimpleResponse.of(dto.getId()));
    }

    @PostMapping("/article/comment")
    public ResponseEntity<Void> comment(@RequestBody @Valid CommentRequest request) {
        articleService.comment(request);

        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/article/comment/{comment-id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("comment-id") Long commentId) {
        articleService.deleteComment(commentId);

        return ResponseEntity.created(null).build();
    }

    @PostMapping("/article/{article-id}/like")
    public ResponseEntity<Void> likeArticle(@PathVariable("article-id") Long articleId) {
        articleService.likeArticle(articleId);

        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/article/{article-id}/like")
    public ResponseEntity<Void> unlikeArticle(@PathVariable("article-id") Long articleId) {
        articleService.unlikeArticle(articleId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{board-id}/article")
    public ResponseEntity<Void> postArticle(
            @PathVariable("board-id") Long boardId,
            @RequestBody @Valid PostArticleRequest request) {
        log.info("게시글 작성 요청: boardId={}, request={}", boardId, request);
        articleService.postArticle(boardId, request);
        return ResponseEntity.created(null).build();
    }

    @PatchMapping("/{board-id}/article")
    public ResponseEntity<Void> modifyArticle(
            @PathVariable("board-id") Long boardId,
            @RequestBody @Valid ModifyArticleRequest request) {
        log.info("게시글 수정 요청: boardId={}, request={}", boardId, request);
        articleService.modifyArticle(boardId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/article/{article-id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("article-id") Long articleId) {
        log.info("게시글 삭제 요청: articleId={}", articleId);
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }
}
