package com.manong.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manong.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
}
