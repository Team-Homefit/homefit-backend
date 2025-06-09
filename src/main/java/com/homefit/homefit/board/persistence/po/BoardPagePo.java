package com.homefit.homefit.board.persistence.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardPagePo {
    private final List<BoardPo> boards;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static BoardPagePo of(List<BoardPo> boards, int page, int size, long totalElements, int totalPages) {
        return new BoardPagePo(boards, page, size, totalElements, totalPages);
    }
}
