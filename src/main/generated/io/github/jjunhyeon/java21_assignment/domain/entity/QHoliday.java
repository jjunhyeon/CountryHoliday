package io.github.jjunhyeon.java21_assignment.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHoliday is a Querydsl query type for Holiday
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHoliday extends EntityPathBase<Holiday> {

    private static final long serialVersionUID = -1462106499L;

    public static final QHoliday holiday = new QHoliday("holiday");

    public final ListPath<HolidayCounty, QHolidayCounty> counties = this.<HolidayCounty, QHolidayCounty>createList("counties", HolidayCounty.class, QHolidayCounty.class, PathInits.DIRECT2);

    public final StringPath countryCode = createString("countryCode");

    public final StringPath holidayName = createString("holidayName");

    public final NumberPath<Long> holidaySn = createNumber("holidaySn", Long.class);

    public final DatePath<java.time.LocalDate> holidayYmd = createDate("holidayYmd", java.time.LocalDate.class);

    public final StringPath isFixedDateYn = createString("isFixedDateYn");

    public final StringPath isNationalHolidayYn = createString("isNationalHolidayYn");

    public final StringPath launchYear = createString("launchYear");

    public final StringPath localHolidayName = createString("localHolidayName");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final ListPath<HolidayType, QHolidayType> types = this.<HolidayType, QHolidayType>createList("types", HolidayType.class, QHolidayType.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QHoliday(String variable) {
        super(Holiday.class, forVariable(variable));
    }

    public QHoliday(Path<? extends Holiday> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHoliday(PathMetadata metadata) {
        super(Holiday.class, metadata);
    }

}

