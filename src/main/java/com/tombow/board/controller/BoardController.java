package com.tombow.board.controller;

import com.tombow.board.dto.BoardDTO;
import com.tombow.board.dto.FileDTO;
import com.tombow.board.service.BoardService;
import com.tombow.board.service.FileService;
import com.tombow.board.util.MD5Generator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;
    private FileService fileService;

    public BoardController(BoardService boardService, FileService fileService){
        this.boardService = boardService;
        this.fileService = fileService;
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
    public String write(@RequestParam("file") MultipartFile files, BoardDTO boardDTO){
        try {
            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    // JAVA.IO.FILE
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FileDTO fileDTO = new FileDTO();
            fileDTO.setOrigFilename(origFilename);
            fileDTO.setFilename(filename);
            fileDTO.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDTO);
            boardDTO.setFileId(fileId);
            boardService.savePost(boardDTO);
        } catch(Exception e) {
            e.printStackTrace();
        }
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

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable("fileId") Long fileId) throws Exception {
        FileDTO fileDTO = fileService.getFile(fileId);
        Path path = Paths.get(fileDTO.getFilePath());
        InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getOrigFilename() + "\"")
                .body(resource);
    }
}

