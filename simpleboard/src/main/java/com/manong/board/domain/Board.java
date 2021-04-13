package com.manong.board.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Board {
	/*
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 기본 키가 자동으로 할당 되도록 설정하는 어노테이션
	 * 기본키 할당 전략을 선택할 수 있는데, 키 생성을 데이터베이스에 위임하는 IDENTITY 전략을 사용한다.
	 */
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	private LocalDateTime createDate;
	
	@Column
	private LocalDateTime updateDate;

	@Builder
	public Board(Long idx, String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
		super();
		this.idx = idx;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	
}
