package io.github.jjunhyeon.java21_assignment.domain.entity.id;

import java.io.Serializable;
import java.util.Objects;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.entity.id
* @fileName       : HolidayTypeId.java
* @author         : JunHyeon
* @date           : 2025.12.01
* @description    : HolidayType PK ID
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.01        KimJunHyeon      최초 생성
*/
public class HolidayTypeId implements Serializable {
	
	private static final long serialVersionUID = 2546063265109598890L;
	private Long holiday;
    private String type;

    public HolidayTypeId() {}

    public HolidayTypeId(Long holiday, String type) {
        this.holiday = holiday;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof HolidayTypeId)) return false;
        HolidayTypeId that = (HolidayTypeId) o;
        return Objects.equals(holiday, that.holiday) &&
               Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(holiday, type);
    }
}
