package com.manong.board;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.manong.board.domain.Board;
import com.manong.board.repository.BoardRepository;

@SpringBootApplication
public class SimpleboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleboardApplication.class, args);
	}

	//CommandLineRunner는 애플리케이션 구동 후 특정 코드를 실행시키고 싶을 때 직접 구현하는 인터페이스이다.
	//애플리케이션을 구동하여 목록을 볼 수 있도록 게시글 100개를 생성하는 코드를 작성한다.
	@Bean
	public CommandLineRunner runner(BoardRepository boardRepository) throws Exception{
		//스프링은 빈(Bean)으로 생성된 메서드에 파라미터로 DI(Dependency Injection, 스프링의 주요 특성 중 하나로 주로 의존 관계 주입이라고 한다. 
		//또는 의존 관계를 주입하는게 아니라 단지 객체의 레퍼런스를 전달하여 참조시킨다는 의미로 의존 관계 설정이라고도 한다)시키는 메커니즘이 존재한다. 
		//생성자를 통해 의존성을 주입시키는 방법과 유사하다. 이를 이용하여 CommandLineRunner를 빈으로 등록한 후 UserRepository를 주입받는다.
		return (args) -> {
			//람다식
			
			//페이징 처리 테스트를 위해서 Board 객체를 빌더 패턴(Builder Pattern, 객체의 생성 과정과 표현 방법을 분리하여 객체를 단계별 동일한 생성 절차로 복잡한 객체로 만드는 패턴)
			//을 사용하여 생성한 후 주입받은 BoardRepository를 사용하여 Board 객체를 저장한다. 
			//이때 IntStream의 rangeClosed를 사용하여 index 순서대로 Board 객체 100개를 생성하여 저장한다.
			IntStream.rangeClosed(1, 100).forEach(idx ->
				boardRepository.save(Board.builder()
						.title("게시글" + idx)
						.content("내용"+idx)
						.createDate(LocalDateTime.now())
						.updateDate(LocalDateTime.now())
						.build())
					);
		};
	}
}
