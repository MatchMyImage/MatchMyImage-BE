package com.LetMeDoWith.LetMeDoWith.application.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberTermAgree is a Querydsl query type for MemberTermAgree
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberTermAgree extends EntityPathBase<MemberTermAgree> {

    private static final long serialVersionUID = -722029295L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberTermAgree memberTermAgree = new QMemberTermAgree("memberTermAgree");

    public final com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity _super = new com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity(this);

    public final BooleanPath advertisement = createBoolean("advertisement");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final BooleanPath privacy = createBoolean("privacy");

    public final BooleanPath termsOfAgree = createBoolean("termsOfAgree");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QMemberTermAgree(String variable) {
        this(MemberTermAgree.class, forVariable(variable), INITS);
    }

    public QMemberTermAgree(Path<? extends MemberTermAgree> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberTermAgree(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberTermAgree(PathMetadata metadata, PathInits inits) {
        this(MemberTermAgree.class, metadata, inits);
    }

    public QMemberTermAgree(Class<? extends MemberTermAgree> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

