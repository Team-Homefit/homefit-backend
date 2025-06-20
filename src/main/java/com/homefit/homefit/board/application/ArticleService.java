package com.homefit.homefit.board.application;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homefit.homefit.board.application.command.CommentCommand;
import com.homefit.homefit.board.application.command.PostArticleCommand;
import com.homefit.homefit.board.application.command.ModifyArticleCommand;
import com.homefit.homefit.board.application.dto.ArticlePageDto;
import com.homefit.homefit.board.application.dto.ArticleSimpleDto;
import com.homefit.homefit.board.domain.Like;
import com.homefit.homefit.board.domain.Comment;
import com.homefit.homefit.board.application.dto.ArticleDto;
import com.homefit.homefit.board.domain.Viewer;
import com.homefit.homefit.board.persistence.ArticleRepository;
import com.homefit.homefit.board.persistence.CommentRepository;
import com.homefit.homefit.board.persistence.ViewerRepository;
import com.homefit.homefit.board.persistence.LikeRepository;

import com.homefit.homefit.board.persistence.po.ArticlePagePo;
import com.homefit.homefit.board.persistence.po.ArticlePo;
import com.homefit.homefit.board.persistence.po.ArticleSimplePo;
import com.homefit.homefit.board.persistence.po.CommentPo;
import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.security.util.UserPrincipalUtil;
import com.homefit.homefit.board.domain.Article;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ViewerRepository viewerRepository;
    private final LikeRepository likeRepository;

    @PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
    public ArticlePageDto getArticles(Long boardId, Boolean isLiked, Integer page, Integer size) {
        Long writerId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        int offset = (page - 1) * size;

        List<ArticlePagePo> articlePagePos = articleRepository.getArticles(boardId, isLiked, page, size, offset,
                writerId);
        return ArticlePageDto.of(articlePagePos, page, size);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
    public ArticleDto getArticle(Long articleId) {
        Long userId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        // 조회수 증가
        boolean hasViewedToday = viewerRepository.hasViewedToday(articleId, userId);
        if (!hasViewedToday) {
            Viewer viewer = Viewer.of(articleId, userId);
            viewerRepository.createViewer(viewer);
        }

        // 게시글 조회
        ArticlePo article = articleRepository.getArticleById(articleId, userId);

        if (article == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");
        }

        // 댓글 목록 조회
        List<ArticlePo.Comment> comments = commentRepository.findByArticleId(articleId);
        article.setComments(comments);

        return ArticleDto.from(article);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
    public ArticleSimpleDto getArticleByComment(Long commentId) {
        ArticleSimplePo po = articleRepository.getArticleByComment(commentId);
        return ArticleSimpleDto.from(po);
    }

    @Transactional
    @PreAuthorize("hasRole('BASIC')")
    public void comment(CommentCommand command) {
        Long memberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요"));

        int count = articleRepository.countArticleInInterestedRegionById(command.getArticleId(), memberId);
        if (count == 0) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "권한이 없거나 존재하지 않는 게시글입니다");
        }

        Comment comment = Comment.of(
                command.getArticleId(),
                memberId,
                command.getParentCommentId(),
                command.getContent());

        int result = commentRepository.insert(comment);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 저장에 실패했습니다");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('BASIC')")
    public void deleteComment(Long commentId) {
        Long memberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요"));

        CommentPo commentPo = commentRepository.selectById(commentId);
        if (commentPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다");
        }

        Comment comment = Comment.of(
                commentPo.getCommentId(),
                commentPo.getArticleId(),
                commentPo.getCommentWriterId(),
                commentPo.getParentCommentId(),
                commentPo.getCommentContent(),
                commentPo.getCommentIsDeleted());

        comment.delete(memberId);

        int result = commentRepository.updateIsDeleted(comment);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 삭제 저장에 실패했습니다");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('BASIC')")
    public void likeArticle(Long articleId) {
        Long memberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요"));

        int count = articleRepository.countById(articleId);
        if (count == 0) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다");
        }

        Like like = Like.of(articleId, memberId);

        int result = 0;
        try {
            result = likeRepository.insert(like);
        } catch (DuplicateKeyException e) {
            throw new HomefitException(HttpStatus.BAD_REQUEST, "이미 좋아요 한 게시글입니다");
        }

        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "좋아요 저장에 실패했습니다");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('BASIC')")
    public void unlikeArticle(Long articleId) {
        Long memberId = UserPrincipalUtil.getId()
                .orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요"));

        int count = likeRepository.countById(articleId, memberId);
        if (count == 0) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "좋아요 하지 않은 게시글입니다");
        }

        int result = likeRepository.deleteById(articleId, memberId);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "좋아요 취소에 실패했습니다");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('BASIC')")
    public void postArticle(PostArticleCommand command) {
        Long writerId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        // 관심지역 검사
        if (!articleRepository.isInterestedRegion(command.getBoardId(), writerId)) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "관심지역이 아닙니다.");
        }

        // 게시글 생성 및 ID 반환
        Article article = Article.of(writerId, command.getBoardId(), command.getTitle(), command.getContent());
        int result = articleRepository.createArticle(article);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 생성에 실패했습니다.");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('BASIC')")
    public void modifyArticle(ModifyArticleCommand command) {
        Long updaterId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        ArticlePo articlePo = articleRepository.getArticleById(command.getId(), updaterId);
        if (articlePo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");
        }

        Article article = Article.of(articlePo.getArticleId(), articlePo.getArticleWriterId(),
                articlePo.getCommunityBoardId(),
                articlePo.getArticleTitle(),
                articlePo.getArticleContent(),
                articlePo.getArticleCreatedAt(),
                articlePo.getArticleLastUpdatedAt(),
                articlePo.getArticleIsDeleted());

        article.update(command.getTitle(), command.getContent(), updaterId);

        int result = articleRepository.updateArticle(article);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 수정에 실패했습니다.");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('BASIC')")
    public void deleteArticle(Long articleId) {
        Long deleterId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        // 작성자 검사
        ArticlePo articlePo = articleRepository.getArticleById(articleId, deleterId);
        if (articlePo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");
        }

        Article article = Article.of(articlePo.getArticleId(), articlePo.getArticleWriterId(),
                articlePo.getCommunityBoardId(),
                articlePo.getArticleTitle(),
                articlePo.getArticleContent(),
                articlePo.getArticleCreatedAt(),
                articlePo.getArticleLastUpdatedAt(),
                articlePo.getArticleIsDeleted());

        article.delete(deleterId);

        int result = articleRepository.deleteArticle(articleId);
        if (result == 0) {
            throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 삭제에 실패했습니다.");
        }
    }
}
