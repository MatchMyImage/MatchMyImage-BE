package com.LetMeDoWith.LetMeDoWith.domain.task;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.LetMeDoWith.LetMeDoWith.domain.task.model.DowithTaskConfirm;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDowithTaskConfirm is a Querydsl query type for DowithTaskConfirm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDowithTaskConfirm extends EntityPathBase<DowithTaskConfirm> {

    private static final long serialVersionUID = -590778358L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDowithTaskConfirm dowithTaskConfirm = new QDowithTaskConfirm("dowithTaskConfirm");

    public final com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity _super = new com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final QDowithTask dowithTask;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QDowithTaskConfirm(String variable) {
        this(DowithTaskConfirm.class, forVariable(variable), INITS);
    }

    public QDowithTaskConfirm(Path<? extends DowithTaskConfirm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDowithTaskConfirm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDowithTaskConfirm(PathMetadata metadata, PathInits inits) {
        this(DowithTaskConfirm.class, metadata, inits);
    }

    public QDowithTaskConfirm(Class<? extends DowithTaskConfirm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dowithTask = inits.isInitialized("dowithTask") ? new QDowithTask(forProperty("dowithTask"), inits.get("dowithTask")) : null;
    }

}

