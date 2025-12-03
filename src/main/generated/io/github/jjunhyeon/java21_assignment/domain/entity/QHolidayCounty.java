package io.github.jjunhyeon.java21_assignment.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHolidayCounty is a Querydsl query type for HolidayCounty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHolidayCounty extends EntityPathBase<HolidayCounty> {

    private static final long serialVersionUID = -808439257L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHolidayCounty holidayCounty = new QHolidayCounty("holidayCounty");

    public final StringPath county = createString("county");

    public final QHoliday holiday;

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QHolidayCounty(String variable) {
        this(HolidayCounty.class, forVariable(variable), INITS);
    }

    public QHolidayCounty(Path<? extends HolidayCounty> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHolidayCounty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHolidayCounty(PathMetadata metadata, PathInits inits) {
        this(HolidayCounty.class, metadata, inits);
    }

    public QHolidayCounty(Class<? extends HolidayCounty> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.holiday = inits.isInitialized("holiday") ? new QHoliday(forProperty("holiday")) : null;
    }

}

