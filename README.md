## 환경 세팅

### Springboot
**https://start.spring.io/** 에서 세팅 후 시작
- v2.4.5
- JAR
- JAVA 8(1.8.0_241)
- Gradle
- Dependencies
  > Spring Web<br>Thymeleaf<br>Spring Data JPA<br>MySQL Driver<br>Lombok
  
### IntelliJ
- ideaIC-2021.1

### MySQL
- v8.0.23

---

## 실행화면 캡처
> 우선 게시판(파일업로드 까지, 추후에 로그인/회원가입 진행 예정)

<br>

#### 게시판 테이블
![boardTable](https://user-images.githubusercontent.com/58925978/115341346-64757700-a1e3-11eb-9302-8dc9864ee85e.PNG)<br>

<details>
<summary><b>코드 보기(Board.java)</b></summary>
<div markdown="1">

```java
// Entity는 데이터베이스 테이블과 매핑되는 객체

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)// JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다
public class Board {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 10, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private Long fileId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public Board(Long id, String author, String title, String content, Long fileId) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }
}
```

</div>
</details>

<details>
<summary><b>코드 보기(BoardRepository.java)</b></summary>
<div markdown="1">

```java
//Repository는 데이터 조작을 담당하며, JpaRepository를 상속받습니다.
//JpaRepository의 값은 매핑할 Entity와 Id의 타입입니다.
public interface BoardRepository extends JpaRepository<Board, Long> {
}
```

</div>
</details>

<details>
<summary><b>코드 보기(BoardDTO.java)</b></summary>
<div markdown="1">

```java
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
```

</div>
</details>
<br>

---
#### 파일 테이블
![fileTable](https://user-images.githubusercontent.com/58925978/115341348-650e0d80-a1e3-11eb-9ffb-83d85e21ee92.PNG)

<details>
<summary><b>코드 보기(File.java)</b></summary>
<div markdown="1">

```java
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    //파일이 업로드되면 ‘업로드된 실제 파일명’, ‘서버에 저장된 파일명’, ‘파일이 서버에 저장된 위치’가 데이터 베이스에 기록되게 프로그램을 작성

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String origFilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    @Builder
    public File(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
```

</div>
</details>

<details>
<summary><b>코드 보기(FileRepository.java)</b></summary>
<div markdown="1">

```java
//Repository는 데이터 조작을 담당하며, JpaRepository를 상속받습니다.
//JpaRepository의 값은 매핑할 Entity와 Id의 타입입니다.
public interface FileRepository extends JpaRepository<File, Long> {
}
```

</div>
</details>

<details>
<summary><b>코드 보기(FileRepository.java)</b></summary>
<div markdown="1">

```java
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDTO {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;

    public File toEntity() {
        File build = File.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public FileDTO(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
```

</div>
</details>


<br>

---
#### 글 목록
![list](https://user-images.githubusercontent.com/58925978/115341107-eadd8900-a1e2-11eb-9d2c-218384fba098.PNG)
---
#### 글 작성
![write_addFileupload](https://user-images.githubusercontent.com/58925978/115341109-eadd8900-a1e2-11eb-8b72-dc2d0fa6c40d.PNG)
---
#### 글 세부내용
![detail](https://user-images.githubusercontent.com/58925978/115341104-e9ac5c00-a1e2-11eb-88b6-0c672e38c1fc.PNG)
---
#### 글 수정
![edit](https://user-images.githubusercontent.com/58925978/115341105-ea44f280-a1e2-11eb-8855-e7ce91be42bc.PNG)
---

