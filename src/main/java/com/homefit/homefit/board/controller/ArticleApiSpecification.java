package com.homefit.homefit.board.controller;

import org.springframework.http.ResponseEntity;

import com.homefit.homefit.board.controller.request.CommentRequest;
import com.homefit.homefit.board.controller.request.ModifyArticleRequest;
import com.homefit.homefit.board.controller.request.PostArticleRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "게시글", description = "커뮤니티 내 게시글 관련 API")
public interface ArticleApiSpecification {
    @Operation(summary = "게시글 작성", description = "사용자가 게시글을 작성합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "403", description = "관심지역이 아님", content = @Content()),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시판", content = @Content())
    })
    ResponseEntity<Void> postArticle(Long boardId, PostArticleRequest request);

    @Operation(summary = "게시글 수정", description = "사용자가 게시글을 수정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "403", description = "게시글 수정 권한 없음", content = @Content()),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글", content = @Content())
    })
    ResponseEntity<Void> modifyArticle(Long boardId, ModifyArticleRequest request);

    @Operation(summary = "게시글 삭제", description = "사용자가 게시글을 삭제합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "게시글 삭제 권한 없음", content = @Content()),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글", content = @Content())
    })
    ResponseEntity<Void> deleteArticle(Long articleId);

    @Operation(summary = "댓글 등록", description = "사용자가 댓글을 작성합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "댓글 작성 성공", content = @Content()),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글", content = @Content())
    })
    ResponseEntity<Void> comment(CommentRequest request);

    @Operation(summary = "댓글 삭제", description = "사용자가 댓글을 작성합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "댓글 작성 성공", content = @Content()),
            @ApiResponse(responseCode = "403", description = "댓글 삭제 권한 없음", content = @Content()),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글", content = @Content())
    })
    ResponseEntity<Void> deleteComment(Long commentId);

    @Operation(summary = "좋아요", description = "주어진 게시글에 요청한 사용자가 좋아요를 추가합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "좋아요 성공", content = @Content()),
            @ApiResponse(responseCode = "404", description = "이미 좋아요 한 게시글", content = @Content()),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글", content = @Content())
    })
    ResponseEntity<Void> likeArticle(Long articleId);

    @Operation(summary = "좋아요", description = "주어진 게시글에 요청한 사용자가 좋아요를 취소합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "좋아요 성공", content = @Content()),
            @ApiResponse(responseCode = "404", description = "좋아요 하지 않은 게시글", content = @Content())
    })
    ResponseEntity<Void> unlikeArticle(Long articleId);
}
