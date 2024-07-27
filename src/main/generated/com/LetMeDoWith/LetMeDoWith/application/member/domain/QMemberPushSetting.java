package com.LetMeDoWith.LetMeDoWith.application.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberPushSetting is a Querydsl query type for MemberPushSetting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberPushSetting extends EntityPathBase<MemberPushSetting> {

    private static final long serialVersionUID = -1528159993L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberPushSetting memberPushSetting = new QMemberPushSetting("memberPushSetting");

    public final com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity _super = new com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity(this);

    public final BooleanPath basePushYn = createBoolean("basePushYn");

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

    public QMemberPushSetting(String variable) {
        this(MemberPushSetting.class, forVariable(variable), INITS);
    }

    public QMemberPushSetting(Path<? extends MemberPushSetting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberPushSetting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberPushSetting(PathMetadata metadata, PathInits inits) {
        this(MemberPushSetting.class, metadata, inits);
    }

    public QMemberPushSetting(Class<? extends MemberPushSetting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

