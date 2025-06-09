package com.homefit.homefit.board.persistence.po;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentPo {
    private Long commentId;
    private Long articleId;
    private Long commentWriterId;
    private Long parentCommentId;
    private String commentContent;
    private LocalDateTime commentCreatedAt;
    private Boolean commentIsDeleted;
}
