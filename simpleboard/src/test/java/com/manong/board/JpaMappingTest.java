package com.manong.board;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.manong.board.domain.Board;
import com.manong.board.repository.BoardRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

//@DataJpaTest는 JPA에 대한 테스트를 지원하는 어노테이션으로 테스트 시 실행된 변경사항이 실제 DB에 반영되지 않는다. 이는 테스트를 수행하고 다시 테스트 이전의 데이터로 롤백하기 때문이다.

//@RunWith 어노테이션을 사용하면 JUnit에 내장된 러너를 사용하는 대신 어노테이션에 정의된 클래스를 호출한다. 
//또한 JUnit의 확장 기능을 지정하여 각 테스트 시 독립적인 애플리케이션 컨텍스트
//(빈의 생성과 관계 설정 같은 제어를 담당하는 IOC 객체를 빈 팩토리라 부르며 이러한 빈 팩토리를 더 확장한 개념이 애플리케이션 컨텍스트이다)를 보장한다.
@RunWith(SpringRunner.class) 

//스프링 부트에서 JPA 테스트를 위한 전용 어노테이션이다. 
//첫 설계 시 엔티티 간의 관계 설정 및 기능 테스트를 가능하게 도와준다. 
//테스트가 끝날 때마다 자동 롤백을 해주어 편리한 JPA 테스트가 가능하다.
@DataJpaTest
class JpaMappingTest {

	private final String title = "테스트";
	private final String content = "내용";

	@Autowired
	private BoardRepository boardRepository;

	//각 테스트가 실행되기 전에 실행될 메서드를 선언한다.
	@Before
	public void init() {
		boardRepository.save(Board.builder()
				.title(title)
				.content(content)
				.createDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now()).build());
	}

	//실제 테스트가 진행될 메서드를 선언한다.
	@Test
    public void test() {
        Board board = boardRepository.getOne((long) 1);
        assertThat(board.getTitle(), is(title));
        assertThat(board.getContent(), is(content));
    }

}
