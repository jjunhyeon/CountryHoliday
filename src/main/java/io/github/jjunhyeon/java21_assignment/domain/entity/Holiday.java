package io.github.jjunhyeon.java21_assignment.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.entity
* @fileName       : Holiday.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 공휴일 Entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
@Entity
@Table(name ="HOLIDAY")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Holiday {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOLIDAY_SN")
	private Long holidaySn;

    /** 공휴일 일자 */
    @Column(name = "HOLIDAY_DATE")
	private LocalDate holidayYmd;
	
	/** 국가코드 */
    @Column(name = "COUNTRY_CODE")
	private String countryCode;
    
	/** 국가명 */
    @Column(name = "COUNTRY_NAME")
	private String countryName;
	
	/** 현지 공휴일명 */
    @Column(name = "LOCAL_HOLIDAY_NAME")
	private String localHolidayName;
    
    /** 매년 날짜가 동일한 공휴일 여부 */
    @Column(name = "FIXED_YN")
	private String isFixedDateYn;
    
    /** 국가 전체 적용 여부 */
    @Column(name ="GLOBAL_YN")
    private String isNationalHolidayYn;
    
    /** 공휴일이 법적으로 지정된 첫 해 */
    @Column(name ="LAUNCH_YEAR")
    private String launchYear;
    
    /** 공휴일 유형[Public, Bank, School, Observance, Optional] */
    @ElementCollection
    @CollectionTable( name = "HOLIDAY_TYPE", joinColumns = @JoinColumn(name = "HOLIDAY_SN"))
    @Column(name = "type")
    private List<String> types = new ArrayList<>();
    
    /** 특정 지역 또는 주만 쉬는 경우 지역 리스트 정보 */
    @ElementCollection
    @CollectionTable(name = "HOLIDAY_COUNTIES", joinColumns = @JoinColumn(name = "HOLIDAY_SN"))
    @Column(name = "county")
    private List<String> counties = new ArrayList<>();
    
    @Column(name = "REG_DATE")
    @CreatedDate
    private LocalDateTime regDate;
    
    @Column(name = "UPDATE_DATE")
    @LastModifiedDate
    private LocalDateTime updateDate;
    
    // 내용 비교 (PK 제외)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Holiday)) return false;
        Holiday other = (Holiday) o;
        
        return Objects.equals(countryCode, other.countryCode) &&
               Objects.equals(holidayYmd, other.holidayYmd) &&
               Objects.equals(countryName, other.countryName) &&
               Objects.equals(localHolidayName, other.localHolidayName) &&
               Objects.equals(isFixedDateYn, other.isFixedDateYn) &&
               Objects.equals(launchYear, other.launchYear) &&
               Objects.equals(isNationalHolidayYn, other.isNationalHolidayYn) && 
               listEquals(types, other.types) && listEquals(counties, other.counties);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(countryCode, holidayYmd, countryName, localHolidayName
        		, isFixedDateYn, isNationalHolidayYn, launchYear, types, counties);
    }
    
    private boolean listEquals(List<?> a, List<?> b) {
        return (a == null ? Collections.emptyList() : new ArrayList<>(a))
               .equals(b == null ? Collections.emptyList() : new ArrayList<>(b));
    } 
}
