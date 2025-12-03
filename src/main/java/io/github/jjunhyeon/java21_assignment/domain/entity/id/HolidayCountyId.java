package io.github.jjunhyeon.java21_assignment.domain.entity.id;

import java.io.Serializable;
import java.util.Objects;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.entity.id
* @fileName       : HolidayCountyId.java
* @author         : JunHyeon
* @date           : 2025.12.01
* @description    : HolidayCounty PK ID
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.01        KimJunHyeon      최초 생성
*/
public class HolidayCountyId implements Serializable {
	private static final long serialVersionUID = 2546063265109598890L;
	private Long holiday;
    private String county;

    public HolidayCountyId() {}

    public HolidayCountyId(Long holiday, String county) {
        this.holiday = holiday;
        this.county = county;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof HolidayCountyId)) return false;
        HolidayCountyId that = (HolidayCountyId) o;
        return Objects.equals(holiday, that.holiday) &&
               Objects.equals(county, that.county);
    }

    @Override
    public int hashCode() {
        return Objects.hash(holiday, county);
    }
}
