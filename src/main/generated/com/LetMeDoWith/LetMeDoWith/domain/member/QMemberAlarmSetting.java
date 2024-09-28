package com.LetMeDoWith.LetMeDoWith.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAlarmSetting is a Querydsl query type for MemberAlarmSetting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAlarmSetting extends EntityPathBase<MemberAlarmSetting> {

    private static final long serialVersionUID = 1337011812L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAlarmSetting memberAlarmSetting = new QMemberAlarmSetting("memberAlarmSetting");

    public final com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity _super = new com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity(this);

    public final BooleanPath baseAlarmYn = createBoolean("baseAlarmYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final BooleanPath feedbackYn = createBoolean("feedbackYn");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath marketingYn = createBoolean("marketingYn");

    public final QMember member;

    public final BooleanPath todoBotYn = createBoolean("todoBotYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QMemberAlarmSetting(String variable) {
        this(MemberAlarmSetting.class, forVariable(variable), INITS);
    }

    public QMemberAlarmSetting(Path<? extends MemberAlarmSetting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAlarmSetting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAlarmSetting(PathMetadata metadata, PathInits inits) {
        this(MemberAlarmSetting.class, metadata, inits);
    }

    public QMemberAlarmSetting(Class<? extends MemberAlarmSetting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

