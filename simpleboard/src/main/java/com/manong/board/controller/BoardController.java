package com.manong.board.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.manong.board.domain.Board;
import com.manong.board.repository.BoardRepository;
import com.manong.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepository;
	
	
	//게시글 리스트
	@GetMapping("/list")
	public String list(@PageableDefault Pageable pageable, Model model) {
		//@PageableDefault 어노테이션의 파라미터인 size, sort, direction 등을 사용하여 페이징 처리에 대한 규약을 정의할 수 있다.

		model.addAttribute("boardList", boardService.findBoardList(pageable));
		return "/board/list";
	}
	
	//게시글 상세 및 등록 폼 호출
    @GetMapping({"", "/"})
    //매핑 경로를 중괄호를 사용하여 여러 개를 받을 수 있다.
    
    public String board(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
    	//@RequestParam 어노테이션을 사용하여 idx 파라미터를 필수로 받는다. 만약 바인딩할 값이 없으면 기본 값 "0"으로 설정된다. 
    	//findBoardByIdx(idx)로 조회 시 idx 값을 "0"으로 조회하면 board 값은 null 값으로 반환된다.
    	
        model.addAttribute("board", boardService.findBoardByIdx(idx));
        return "/board/form";
    }
	
	//게시글 생성
	@PostMapping
	public ResponseEntity<?> postBoard(@RequestBody Board board){
		board.setCreateDate(LocalDateTime.now());
		board.setUpdateDate(LocalDateTime.now());
		boardRepository.save(board);
		
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}
	
	//게시글 수정
	@PutMapping("/{idx}")
	public ResponseEntity<?> putBoard(@PathVariable("idx") Long idx, @RequestBody Board board){
		Board updateBoard = boardRepository.getOne(idx);
		updateBoard.setTitle(board.getTitle());
		updateBoard.setContent(board.getContent());
		updateBoard.setUpdateDate(LocalDateTime.now());
		boardRepository.save(updateBoard);
		
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
	
	//게시글 삭제
	@DeleteMapping("/{idx}")
	public ResponseEntity<?> deleteBoard(@PathVariable("idx") Long idx){
		boardRepository.deleteById(idx);
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
	
			
	
	
}
