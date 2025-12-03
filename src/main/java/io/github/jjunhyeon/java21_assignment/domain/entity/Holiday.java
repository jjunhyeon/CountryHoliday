package io.github.jjunhyeon.java21_assignment.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
@Table(name = "HOLIDAY")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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
	@Column(name = "HOLIDAY_NAME")
	private String holidayName;

	/** 현지 공휴일명 */
	@Column(name = "LOCAL_HOLIDAY_NAME")
	private String localHolidayName;

	/** 매년 날짜가 동일한 공휴일 여부 */
	@Column(name = "FIXED_YN")
	private String isFixedDateYn;

	/** 국가 전체 적용 여부 */
	@Column(name = "GLOBAL_YN")
	private String isNationalHolidayYn;

	/** 공휴일이 법적으로 지정된 첫 해 */
	@Column(name = "LAUNCH_YEAR")
	private String launchYear;

	/** 공휴일 유형[Public, Bank, School, Observance, Optional] */
	@OneToMany(mappedBy = "holiday", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HolidayType> types = new ArrayList<>();

	/** 특정 지역 또는 주만 쉬는 경우 지역 리스트 정보 */
	@OneToMany(mappedBy = "holiday", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HolidayCounty> counties = new ArrayList<>();

	@Column(name = "REG_DATE")
	@CreatedDate
	private LocalDateTime regDate;

	@Column(name = "UPDATE_DATE")
	@LastModifiedDate
	private LocalDateTime updateDate;

	// 내용 비교 (PK 제외)
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Holiday))
			return false;
		Holiday other = (Holiday) o;

		return Objects.equals(countryCode, other.countryCode) && Objects.equals(holidayYmd, other.holidayYmd)
				&& Objects.equals(holidayName, other.holidayName)
				&& Objects.equals(localHolidayName, other.localHolidayName)
				&& Objects.equals(isFixedDateYn, other.isFixedDateYn) && Objects.equals(launchYear, other.launchYear);
	}

	@Override
	public int hashCode() {
		return Objects.hash(countryCode, holidayYmd, holidayName, localHolidayName, isFixedDateYn, isNationalHolidayYn,
				launchYear);
	}

	public boolean typesChanged(List<HolidayType> newTypes) {
		Set<String> current = types.stream().map(HolidayType::getType).collect(Collectors.toSet());
		Set<String> updated = newTypes.stream().map(HolidayType::getType).collect(Collectors.toSet());
		return !current.equals(updated);
	}

	public boolean countiesChanged(List<HolidayCounty> newCounties) {
		Set<String> current = counties.stream().map(HolidayCounty::getCounty).collect(Collectors.toSet());
		Set<String> updated = newCounties.stream().map(HolidayCounty::getCounty).collect(Collectors.toSet());
		return !current.equals(updated);
	}
}
