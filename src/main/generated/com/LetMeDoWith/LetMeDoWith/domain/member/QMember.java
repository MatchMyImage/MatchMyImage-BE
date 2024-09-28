package com.LetMeDoWith.LetMeDoWith.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.LetMeDoWith.LetMeDoWith.common.enums.member.TaskCompleteLevel;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1869145339L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity _super = new com.LetMeDoWith.LetMeDoWith.common.entity.QBaseAuditEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final DatePath<java.time.LocalDate> dateOfBirth = createDate("dateOfBirth", java.time.LocalDate.class);

    public final ListPath<MemberFollow, QMemberFollow> followerMembers = this.<MemberFollow, QMemberFollow>createList("followerMembers", MemberFollow.class, QMemberFollow.class, PathInits.DIRECT2);

    public final ListPath<MemberFollow, QMemberFollow> followingMembers = this.<MemberFollow, QMemberFollow>createList("followingMembers", MemberFollow.class, QMemberFollow.class, PathInits.DIRECT2);

    public final EnumPath<com.LetMeDoWith.LetMeDoWith.common.enums.member.Gender> gender = createEnum("gender", com.LetMeDoWith.LetMeDoWith.common.enums.member.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final StringPath selfDescription = createString("selfDescription");

    public final ListPath<MemberSocialAccount, QMemberSocialAccount> socialAccountList = this.<MemberSocialAccount, QMemberSocialAccount>createList("socialAccountList", MemberSocialAccount.class, QMemberSocialAccount.class, PathInits.DIRECT2);

    public final EnumPath<com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberStatus> status = createEnum("status", com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberStatus.class);

    public final ListPath<MemberStatusHistory, QMemberStatusHistory> statusHistoryList = this.<MemberStatusHistory, QMemberStatusHistory>createList("statusHistoryList", MemberStatusHistory.class, QMemberStatusHistory.class, PathInits.DIRECT2);

    public final StringPath subject = createString("subject");

    public final EnumPath<TaskCompleteLevel> taskLevel = createEnum("taskLevel", TaskCompleteLevel.class);

    public final QMemberTermAgree termAgree;

    public final EnumPath<com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberType> type = createEnum("type", com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.termAgree = inits.isInitialized("termAgree") ? new QMemberTermAgree(forProperty("termAgree"), inits.get("termAgree")) : null;
    }

}

