package com.manong.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
 
import com.manong.board.domain.Board;
 
public interface BoardService {
    Page<Board> findBoardList(Pageable pageable);
    Board findBoardByIdx(Long idx);
}
