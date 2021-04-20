package com.tombow.board.dto;

import com.tombow.board.domain.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

//Controller와 Service 사이에서 데이터를 주고받는 DTO(Data Access Object)

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String author;
    private String title;
    private String content;
    private Long fileId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //아래 코드의 toEntity()는 DTO에서 필요한 부분을 빌더 패턴을 통해 Entity로 만드는 일을 합니다.
    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .fileId(fileId)
                .build();
        return build;
    }

    @Builder
    public BoardDTO(Long id, String author, String title, String content, Long fileId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
