package com.tombow.board.controller;

import com.tombow.board.dto.BoardDTO;
import com.tombow.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    //model.addAttribute("postList", boardDtoList); 를 통하여 
    // boardDtoList를 board/list.html에 postList로 전달해줌
    @GetMapping("/")
    public String list(Model model){
        List<BoardDTO> boardDTOList = boardService.getBoardList();
        model.addAttribute("postList", boardDTOList);
        return "board/list.html";
    }

    @GetMapping("/post")
    public String post(){
        return "board/post.html";
    }

    @PostMapping("/post")
    public String write(BoardDTO boardDTO){
        boardService.savePost(boardDTO);
        return "redirect:/";
    }
}

