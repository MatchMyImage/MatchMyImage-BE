package com.LetMeDoWith.LetMeDoWith.domain.task;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTaskConfirm;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDowithTask is a Querydsl query type for DowithTask
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDowithTask extends EntityPathBase<DowithTask> {

    private static final long serialVersionUID = 1055551958L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDowithTask dowithTask = new QDowithTask("dowithTask");

    public final com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity _super = new com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity(this);

    public final DateTimePath<java.time.LocalDateTime> completeDateTime = createDateTime("completeDateTime", java.time.LocalDateTime.class);

    public final ListPath<DowithTaskConfirm, QDowithTaskConfirm> confirms = this.<DowithTaskConfirm, QDowithTaskConfirm>createList("confirms", DowithTaskConfirm.class, QDowithTaskConfirm.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.LetMeDoWith.LetMeDoWith.common.enums.common.Yn> isRoutine = createEnum("isRoutine", com.LetMeDoWith.LetMeDoWith.common.enums.common.Yn.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final QDowithTaskRoutine routine;

    public final DateTimePath<java.time.LocalDateTime> startDateTime = createDateTime("startDateTime", java.time.LocalDateTime.class);

    public final EnumPath<com.LetMeDoWith.LetMeDoWith.common.enums.task.DowithTaskStatus> status = createEnum("status", com.LetMeDoWith.LetMeDoWith.common.enums.task.DowithTaskStatus.class);

    public final DateTimePath<java.time.LocalDateTime> successDateTime = createDateTime("successDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> taskCategoryId = createNumber("taskCategoryId", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QDowithTask(String variable) {
        this(DowithTask.class, forVariable(variable), INITS);
    }

    public QDowithTask(Path<? extends DowithTask> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDowithTask(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDowithTask(PathMetadata metadata, PathInits inits) {
        this(DowithTask.class, metadata, inits);
    }

    public QDowithTask(Class<? extends DowithTask> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.routine = inits.isInitialized("routine") ? new QDowithTaskRoutine(forProperty("routine")) : null;
    }

}

