package io.github.jjunhyeon.java21_assignment.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHolidayType is a Querydsl query type for HolidayType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHolidayType extends EntityPathBase<HolidayType> {

    private static final long serialVersionUID = 124814167L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHolidayType holidayType = new QHolidayType("holidayType");

    public final QHoliday holiday;

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath type = createString("type");

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QHolidayType(String variable) {
        this(HolidayType.class, forVariable(variable), INITS);
    }

    public QHolidayType(Path<? extends HolidayType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHolidayType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHolidayType(PathMetadata metadata, PathInits inits) {
        this(HolidayType.class, metadata, inits);
    }

    public QHolidayType(Class<? extends HolidayType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.holiday = inits.isInitialized("holiday") ? new QHoliday(forProperty("holiday")) : null;
    }

}

