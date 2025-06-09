package com.homefit.homefit.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homefit.homefit.board.application.BoardService;
import com.homefit.homefit.board.application.dto.BoardPageDto;
import com.homefit.homefit.board.controller.response.BoardPageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<BoardPageResponse> getBoards(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        BoardPageDto boardPageDto = boardService.getBoards(page, size);
        return ResponseEntity.ok(BoardPageResponse.from(boardPageDto));
    }
}