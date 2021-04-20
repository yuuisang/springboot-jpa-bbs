package com.tombow.board.service;

import com.tombow.board.domain.repository.BoardRepository;
import com.tombow.board.dto.BoardDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//Repository를 사용하여 Service를 구현합니다.
//글쓰기 Form에서 내용을 입력한 뒤, ‘글쓰기’ 버튼을 누르면 Post 형식으로 요청이 오고,
// BoardService의 savePost()를 실행하게 됩니다.
@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardDTO boardDTO){
        return boardRepository.save(boardDTO.toEntity()).getId();
    }
}
