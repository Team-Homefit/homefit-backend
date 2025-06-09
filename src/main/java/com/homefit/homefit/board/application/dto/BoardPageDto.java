package com.homefit.homefit.board.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.board.persistence.po.BoardWithCountPo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardPageDto {
    private final List<Board> boards;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static BoardPageDto of(List<BoardWithCountPo> pos, int page, int size) {
        long totalElements = pos.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        List<Board> boards = pos.stream()
                .map(Board::from)
                .collect(Collectors.toList());
                
        return new BoardPageDto(boards, page, size, totalElements, totalPages);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Board {
        private final Long id;
        private final String sggCd;
        private final Long articleCount;
        private final Long interestCount;

        public static Board from(BoardWithCountPo po) {
            return new Board(
                po.getCommunityBoardId(),
                po.getSggCd(),
                po.getArticleCount(),
                po.getInterestCount()
            );
        }
    }
}