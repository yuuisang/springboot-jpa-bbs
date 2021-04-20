package com.tombow.board.service;

import com.tombow.board.domain.entity.Board;
import com.tombow.board.domain.repository.BoardRepository;
import com.tombow.board.dto.BoardDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//Repository를 사용하여 Service를 구현합니다.
//글쓰기 Form에서 내용을 입력한 뒤, ‘글쓰기’ 버튼을 누르면 Post 형식으로 요청이 오고,
// BoardService의 savePost()를 실행하게 됩니다.
@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    // POSTING 작업
    @Transactional
    public Long savePost(BoardDTO boardDTO){
        return boardRepository.save(boardDTO.toEntity()).getId();
    }

    //Repository에서 모든 데이터를 조회하여, BoardDto List에 데이터를 넣어 반환
    @Transactional
    public List<BoardDTO> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for(Board board : boardList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    //게시글의 id를 받아 해당 게시글의 데이터만 가져와 화면에 뿌려줘야함.
    @Transactional
    public BoardDTO getPost(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardDTO boardDTO = BoardDTO.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .fileId(board.getFileId())
                .createdDate(board.getCreatedDate())
                .build();
        return boardDTO;
    }

    //글을 조회하는 페이지에서 ‘삭제’ 버튼을 누르면, /post/{id}으로 Delete 요청을 한다.
    // (만약 1번 글에서 ‘삭제’ 버튼을 클릭하면 /post/1로 접속.)
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

}
