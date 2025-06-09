package com.homefit.homefit.board.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.board.application.dto.BoardPageDto;

@Getter
@Builder
public class BoardPageResponse {
    private final List<Board> boards;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static BoardPageResponse from(BoardPageDto dto) {
        List<Board> boards = dto.getBoards().stream()
                .map(Board::from)
                .collect(Collectors.toList());
        return new BoardPageResponse(boards, dto.getPage(), dto.getSize(), dto.getTotalElements(), dto.getTotalPages());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Board {
        private final Long id;
        private final String sggCode;
        private final Long articleCount;
        private final Long interestCount;

        public static Board from(BoardPageDto.Board dto) {
            return new Board(dto.getId(), dto.getSggCd(), dto.getArticleCount(), dto.getInterestCount());
        }
    }
}