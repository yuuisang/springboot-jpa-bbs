package com.manong.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manong.board.domain.Board;
import com.manong.board.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardRepository boardRepository;
	
	
	//pageable로 넘어온 pageNumber 객체가 0 이하일 때 0으로 초기화한다. 
	//기본 페이지 크기인 10으로 새로운 PageRequest 객체를 만들어 페이징 처리된 게시글 리스트를 반환한다.
	@Override
	public Page<Board> findBoardList(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
		return boardRepository.findAll(pageable);
	}
	
	
	//board의 idx 값을 사용하여 board 객체를 반환한다.
	@Override
	public Board findBoardByIdx(Long idx) {
		return boardRepository.findById(idx).orElse(new Board());
	}
}
