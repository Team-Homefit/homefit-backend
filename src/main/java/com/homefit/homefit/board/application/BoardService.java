package com.homefit.homefit.board.application;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.homefit.homefit.board.application.dto.BoardPageDto;
import com.homefit.homefit.board.persistence.BoardRepository;
import com.homefit.homefit.board.persistence.po.BoardWithCountPo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @PreAuthorize("hasAnyRole('BASIC', 'ADMIN')")
    public BoardPageDto getBoards(int page, int size) {
        int offset = (page - 1) * size;

        List<BoardWithCountPo> poList = boardRepository.selectPageWithCount(page, size, offset);
        return BoardPageDto.of(poList, page, size);
    }
}