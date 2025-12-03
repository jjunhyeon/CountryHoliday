package io.github.jjunhyeon.java21_assignment.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.github.jjunhyeon.java21_assignment.domain.entity.id.HolidayCountyId;
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
* @fileName       : HolidayCounty.java
* @author         : JunHyeon
* @date           : 2025.12.01
* @description    : 휴일 COUNTY 정보 처리를 위한 ID 클래스
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.01        KimJunHyeon      최초 생성
*/
@Entity
@Table(name = "HOLIDAY_COUNTY")
@EntityListeners(AuditingEntityListener.class)
@IdClass(HolidayCountyId.class)
@Setter
@Getter
public class HolidayCounty {
	
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOLIDAY_SN")
    private Holiday holiday;

    @Id
    @Column(name = "county")
    private String county;
    
	@Column(name = "REG_DATE")
	@CreatedDate
	private LocalDateTime regDate;

	@Column(name = "UPDATE_DATE")
	@LastModifiedDate
	private LocalDateTime updateDate;
}
