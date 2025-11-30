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

    public final ListPath<String, StringPath> counties = this.<String, StringPath>createList("counties", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath countryCode = createString("countryCode");

    public final StringPath countryName = createString("countryName");

    public final NumberPath<Long> holidaySn = createNumber("holidaySn", Long.class);

    public final DatePath<java.time.LocalDate> holidayYmd = createDate("holidayYmd", java.time.LocalDate.class);

    public final StringPath isFixedDateYn = createString("isFixedDateYn");

    public final StringPath isNationalHolidayYn = createString("isNationalHolidayYn");

    public final StringPath launchYear = createString("launchYear");

    public final StringPath localHolidayName = createString("localHolidayName");

    public final DatePath<java.util.Date> regDate = createDate("regDate", java.util.Date.class);

    public final ListPath<String, StringPath> types = this.<String, StringPath>createList("types", String.class, StringPath.class, PathInits.DIRECT2);

    public final DatePath<java.util.Date> updateDate = createDate("updateDate", java.util.Date.class);

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

