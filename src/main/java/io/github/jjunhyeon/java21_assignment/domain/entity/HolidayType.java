package io.github.jjunhyeon.java21_assignment.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.github.jjunhyeon.java21_assignment.domain.entity.id.HolidayTypeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.entity
* @fileName       : HolidayType.java
* @author         : JunHyeon
* @date           : 2025.12.01
* @description    : 휴일 TYPE 정보 처리를 위한 ID 클래스 
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.01        KimJunHyeon      최초 생성
*/
@Entity
@Table(name = "HOLIDAY_TYPE")
@EntityListeners(AuditingEntityListener.class)
@IdClass(HolidayTypeId.class)
@Setter
@Getter
public class HolidayType {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOLIDAY_SN")
    private Holiday holiday;

    /** 공휴일 유형[Public, Bank, School, Observance, Optional] */
    @Column(name = "TYPE")
    @Id
    private String type;
    
	@Column(name = "REG_DATE")
	@CreatedDate
	private LocalDateTime regDate;

	@Column(name = "UPDATE_DATE")
	@LastModifiedDate
	private LocalDateTime updateDate;
    
}
