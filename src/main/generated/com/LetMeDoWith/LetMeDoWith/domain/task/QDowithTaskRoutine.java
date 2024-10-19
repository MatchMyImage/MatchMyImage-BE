package com.LetMeDoWith.LetMeDoWith.domain.task;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDowithTaskRoutine is a Querydsl query type for DowithTaskRoutine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDowithTaskRoutine extends EntityPathBase<DowithTaskRoutine> {

    private static final long serialVersionUID = -156243442L;

    public static final QDowithTaskRoutine dowithTaskRoutine = new QDowithTaskRoutine("dowithTaskRoutine");

    public final com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity _super = new com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QDowithTaskRoutine(String variable) {
        super(DowithTaskRoutine.class, forVariable(variable));
    }

    public QDowithTaskRoutine(Path<? extends DowithTaskRoutine> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDowithTaskRoutine(PathMetadata metadata) {
        super(DowithTaskRoutine.class, metadata);
    }

}

