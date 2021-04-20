package com.tombow.board.controller;

import com.tombow.board.dto.BoardDTO;
import com.tombow.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

    //각 게시글을 클릭하면, /post/{id}으로 Get 요청을 합니다.
    //만약 1번 글을 클릭하면 /post/1로 접속됩니다.
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.getPost(id);
        model.addAttribute("post", boardDTO);
        return "board/detail.html";
    }

    // ‘수정’ 버튼을 누르면, /post/edit/{id}으로 Get 요청 화면띄우기
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.getPost(id);
        model.addAttribute("post", boardDTO);
        return "board/edit.html";
    }

    // DB에 접근해서 데이터 수정
    @PutMapping("/post/edit/{id}")
    public String update(BoardDTO boardDTO) {
        boardService.savePost(boardDTO);
        return "redirect:/";
    }
}

